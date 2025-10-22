package g01.it342.oauth2.service;

import g01.it342.oauth2.entity.AuthProviderEntity;
import g01.it342.oauth2.entity.UserEntity;
import g01.it342.oauth2.repository.AuthProviderRepository;
import g01.it342.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthProviderRepository authProviderRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException
    {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        AuthProviderEntity.Provider provider = AuthProviderEntity.Provider.valueOf(registrationId.toUpperCase());

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email;
        String name;
        String avatar;

        if(provider == AuthProviderEntity.Provider.GOOGLE)
        {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            avatar = (String) attributes.get("picture");
        }
        else // GITHUB
        {
            email = (String) attributes.get("email");

            if(email == null && attributes.containsKey("login"))
            {
                email = attributes.get("login") + "github.com";
            }

            name = (String) attributes.get("name");
            avatar = (String) attributes.get("avatar_url");
        }



        // Check if user already exists
        String finalEmail = email;
        UserEntity user = userRepository.findByEmail(email).
                orElseGet(() ->
                {
                    UserEntity newUser = new UserEntity();
                    newUser.setEmail(finalEmail);
                    newUser.setDisplayName(name);
                    newUser.setAvatarUrl(avatar);
                    return userRepository.save(newUser);
                });

        // Create or update AuthProvider
        authProviderRepository.findByProviderAndProviderUserId(provider, oAuth2User.getName()).
                orElseGet(() ->
                {
                   AuthProviderEntity ap = new AuthProviderEntity();
                   ap.setUser(user);
                   ap.setProvider(provider);
                   ap.setProviderUserId(oAuth2User.getName());
                   ap.setProviderEmail(finalEmail);
                   return authProviderRepository.save(ap);
                });

        return oAuth2User;
    }
}

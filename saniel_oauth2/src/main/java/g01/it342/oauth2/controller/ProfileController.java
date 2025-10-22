package g01.it342.oauth2.controller;

import g01.it342.oauth2.entity.AuthProviderEntity;
import g01.it342.oauth2.entity.UserEntity;
import g01.it342.oauth2.repository.AuthProviderRepository;
import g01.it342.oauth2.repository.UserRepository;
import g01.it342.oauth2.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Map;

@Controller
@RequestMapping("/profile")
public class ProfileController
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthProviderRepository apRepository;

    @GetMapping
    public String profilePage(Model model, OAuth2AuthenticationToken auth)
    {
        String registrationId = auth.getAuthorizedClientRegistrationId();
        AuthProviderEntity.Provider provider = AuthProviderEntity.Provider.valueOf(registrationId.toUpperCase());
        Map<String, Object> attributes = auth.getPrincipal().getAttributes();
        String email, name, avatar, bio;

        if(provider == AuthProviderEntity.Provider.GOOGLE)
        {
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
            avatar = (String) attributes.get("picture");
            bio = (String) attributes.get("bio");
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
            bio = (String) attributes.get("bio");
        }

        String finalEmail = email;
        UserEntity user = userRepository.findByEmail(email).
                orElseGet(() ->
                {
                    UserEntity newUser = new UserEntity();
                    newUser.setEmail(finalEmail);
                    newUser.setDisplayName(name);
                    newUser.setAvatarUrl(avatar);
                    newUser.setBio(bio);
                    return userRepository.save(newUser);
                });

        apRepository.findByProviderAndProviderUserId(provider, name).
                orElseGet(() ->
                {
                    AuthProviderEntity ap = new AuthProviderEntity();
                    ap.setUser(user);
                    ap.setProvider(provider);
                    ap.setProviderUserId(name);
                    ap.setProviderEmail(finalEmail);
                    return apRepository.save(ap);
                });

        model.addAttribute("user", user);
        return "profile"; // Render profile.html
    }

    @PostMapping
    public String updateProfile(@ModelAttribute UserEntity formUser, OAuth2AuthenticationToken auth)
    {
        String email = auth.getPrincipal().getAttribute("email");
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        user.setDisplayName(formUser.getDisplayName());
        user.setBio(formUser.getBio());
        userRepository.save(user);
        return "redirect:/profile";
    }
}

package g01.it342.oauth2.config;

import g01.it342.oauth2.service.CustomOAuth2Service2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
         http.
                authorizeHttpRequests(auth -> auth.
                        requestMatchers("/").
                        permitAll().anyRequest().authenticated()).
                oauth2Login(oauth -> oauth.
                        loginPage("/").
                        userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2Service2())).
                        defaultSuccessUrl("/profile", true)).
                logout(logout -> logout.
                        logoutSuccessUrl("/").
                        invalidateHttpSession(true).
                        deleteCookies("JSESSIONID").
                        permitAll());

         return http.build();
    }

    @Bean
    public CustomOAuth2Service2 customOAuth2Service2()
    {
        return new CustomOAuth2Service2();
    }
}

package ch.igl.compta.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import ch.igl.compta.api.security.AuthEntryPointJwt;
import ch.igl.compta.api.security.AuthTokenFilter;
import ch.igl.compta.api.security.JwtUtil;
import ch.igl.compta.service.api.ApiUserDetailsService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Value("${spring.websecurity.debug:false}")
    boolean webSecurityDebug;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ApiUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/v1/**")
                .authorizeHttpRequests((requests) -> requests
                            .requestMatchers("/api/v1/test/auth/**").permitAll()
                            .requestMatchers("/api/v1/test/users").hasRole("ADMIN")
                            .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandling -> exceptionHandling
                            .authenticationEntryPoint(unauthorizedHandler)
                            .accessDeniedHandler((request, response, accessDeniedException) -> {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
            }))
                .csrf(csrf -> csrf.disable()) // Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/**")
                .authorizeHttpRequests((requests) -> requests
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                            .requestMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/img/**", "/images/**", 
                                            "/manifest.webmanifest", "/favicon.ico", "/icon.svg", "/apple-touch-icon.png", 
                                            "/.well-known/appspecific/com.chrome.devtools.json").permitAll()
                            .requestMatchers("/api/v1/**", "/error").permitAll()
                            .anyRequest().authenticated())
                .formLogin((form) -> form
                            .loginPage("/login")
                            .successHandler(authenticationSuccessHandler())
                            .permitAll())
                .logout((logout) -> logout.permitAll()
                            .deleteCookies("JSESSIONID"))
                .csrf(Customizer.withDefaults());

        http.sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            .maximumSessions(1)
            .expiredUrl("/login?expired=true")
            // TODO test expired session handling
        );

        return http.build();
    }



    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) -> {
            if (request.getHeader("Accept") != null && request.getHeader("Accept").contains("application/json")) {
                // Requête API : retourne un JWT
                String jwt = jwtUtil.generateToken(authentication.getName());
                response.setContentType("application/json");
                response.getWriter().write("{\"token\": \"" + jwt + "\"}");
            } else {
                String jwt = jwtUtil.generateToken(authentication.getName());
                request.getSession().setAttribute("jwt", jwt);

                // Ajoute le JWT dans un cookie HTTP-only
                Cookie jwtCookie = new Cookie("X-Auth-Token", jwt);
                jwtCookie.setHttpOnly(true);
                jwtCookie.setSecure(false); // Si HTTPS
                jwtCookie.setPath("/");
                jwtCookie.setMaxAge(60 * 60 * 4); // 4h (même durée que ton token)
                response.addCookie(jwtCookie);

                RequestCache requestCache = new HttpSessionRequestCache();
                SavedRequest savedRequest = requestCache.getRequest(request, response);
                String redirectUrl = savedRequest != null ? savedRequest.getRedirectUrl() : "/";
                response.sendRedirect(redirectUrl);
            }
        }; 
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.debug(webSecurityDebug);
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    /*
	@Bean
	public UserDetailsService webUserDetailsService(UserRepository userRepository) {
        // auth with api?
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		UserDetails user =
			 User.builder()
				.username("user")
				.password(encoder.encode("password"))
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}*/
}

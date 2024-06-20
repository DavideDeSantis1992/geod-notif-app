package it.inail.geodnotifapp.security.configuration;

import it.inail.geodnotifapp.security.filters.JwtAuthenticationFilter;
import it.inail.geodnotifapp.security.filters.JwtTokenHelper;
import it.inail.geodnotifapp.security.filters.UnauthorizedAuthenticationEntryPoint;
import it.inail.geodnotifapp.security.matchers.BasicMatcher;
import it.inail.geodnotifapp.security.matchers.EmptyRequestMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe che implementa la configurazione di sicurezza del servizio.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled= true)
@EnableScheduling
public class SecurityConfiguration extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfiguration.class);

    private static final String[] SWAGGER_URLS = {
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/favicon.ico"
    };

    private static final String ACTUATOR_URL = "/actuator/**";

    /* Classe che gestisce gli errori di autorizzazione */
    @Autowired
    private UnauthorizedAuthenticationEntryPoint unauthorizedAuthenticationEntryPoint;

    @Autowired(required = false)
    private BuildProperties buildProperties;

//    @Autowired
//    private AuthorizationProperties authorizationProperties;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private ApplicationContext applicationContext;

//    @Autowired
//    private Environment environment;

    @Value("${server.servlet.context-path:@null}")
    private String contextRoot;

    
    
    public SecurityConfiguration() {
		super();
	}

	public SecurityConfiguration(boolean disableDefaults) {
		super(disableDefaults);
	}

    private JwtAuthenticationFilter buildAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter autFilter = new JwtAuthenticationFilter(
                requestMatcher(),
                buildProperties,
                jwtTokenHelper
        );
        autFilter.setAuthenticationManager(authenticationManager());
        return autFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        log.info("Configuring the Security Provider...");

        http
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .httpBasic().disable()
                .cors()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedAuthenticationEntryPoint)
                .and()
                //we don't need a session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, SWAGGER_URLS).permitAll()
                .antMatchers(ACTUATOR_URL).permitAll()
                .anyRequest().permitAll();

        http.addFilterBefore(buildAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();

        log.info("All private resources have been protected.");
    }

    /* CORS configuration: vengono permesse chiamate da tutte le sorgenti e su tutti i metodi. */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD"));
        configuration.addAllowedHeader("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    private RequestMatcher requestMatcher() throws ClassNotFoundException {
        Map<String, RequestMatcher> requestMatchers = new HashMap<>();

        Map<String, BasicMatcher> beansOfType = applicationContext.getBeansOfType(BasicMatcher.class);
        log.debug("Founded Matchers: " +beansOfType);

        for (BasicMatcher matcher : beansOfType.values()) {
            Map<String, RequestMatcher> matchers = matcher.getMatchers();
            matchers.forEach((key, requestMatcher) -> {
                requestMatchers.put(key, requestMatcher);
            });
        }

        if (requestMatchers.values().isEmpty()) {
            log.warn("No rest controller has been found.");
            return new EmptyRequestMatcher();
        }

        if (log.isDebugEnabled()) {
            for (String path : requestMatchers.keySet()) {
                log.debug("path sottoposto a sicurezza {} ", path);
            }
        }

        return new OrRequestMatcher(requestMatchers.values().toArray(new RequestMatcher[requestMatchers.size()]));
    }
}

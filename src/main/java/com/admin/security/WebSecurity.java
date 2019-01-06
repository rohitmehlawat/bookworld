package com.admin.security;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.admin.service.UserDetailsServiceImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;

import static com.admin.security.SecurityConstants.SIGN_UP_URL;

import static com.admin.security.SecurityConstants.SIGN_UP_URL_EMPLOYEE;;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL,SIGN_UP_URL_EMPLOYEE).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(getJWTAuthenticationFilterAdmin())
                .addFilter(getJWTAuthenticationFilterEmployee())                
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

        
    }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
	  /*CorsConfiguration configuration = new CorsConfiguration();
      // This Origin header you can see that in Network tab
      configuration.setAllowedOrigins(Arrays.asList("http:/url_1", "http:/url_2")); 
      configuration.setAllowedMethods(Arrays.asList("GET","POST"));
      configuration.setAllowedHeaders(Arrays.asList("content-type"));
      configuration.setAllowCredentials(true);*/
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
  
  
  public JWTAuthenticationFilterAdmin getJWTAuthenticationFilterAdmin() throws Exception {
      final JWTAuthenticationFilterAdmin filter = new JWTAuthenticationFilterAdmin(authenticationManager());
      filter.setFilterProcessesUrl("/admin/login");
      return filter;
  }
  

  public JWTAuthenticationFilterEmployee getJWTAuthenticationFilterEmployee() throws Exception {
      final JWTAuthenticationFilterEmployee filter = new JWTAuthenticationFilterEmployee(authenticationManager());
      filter.setFilterProcessesUrl("/employee/login");
      return filter;
  }
  
}

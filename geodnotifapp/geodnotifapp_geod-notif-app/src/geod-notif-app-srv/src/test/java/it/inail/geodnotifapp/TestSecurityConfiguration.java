package it.inail.geodnotifapp;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@TestConfiguration
@Order(99)
public class TestSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure( HttpSecurity http ) throws Exception {
    http.authorizeRequests()
            .mvcMatchers("*").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable();
  }

}

package com.imanafrica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableWebSecurity
@Order(value = Ordered.HIGHEST_PRECEDENCE + 10)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	BoneCPDataSource datasource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/resources/**").permitAll()
				.antMatchers("/api/v1/user/create").permitAll()
				.antMatchers("api/v1/admin/**").hasRole("ADMIN").anyRequest().anonymous();
		http.csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		// auth.inMemoryAuthentication().withUser("pastoroket")
		// .password("wordpass15").roles("USER");
		auth.jdbcAuthentication()
				.dataSource(datasource)
				.passwordEncoder(passwordEncoder())
				.usersByUsernameQuery(
						"select username, password, enabled from users where username=?")
				.authoritiesByUsernameQuery(
						"select u.username, ur.authority from users u, user_roles ur where u.id = ur.userId and u.username =?");
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder;

	}

}

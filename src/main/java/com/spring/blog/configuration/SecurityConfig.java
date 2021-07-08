package com.spring.blog.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private static final String[] AUTH_LIST = { //URI's que não precisarão de autenticação
												//Spring Security não pedirá autenticação para acessar essas URI's
        "/",
        "/posts",
        "/posts/{id}"
//      "/newpost" não deve constar pois apenas usuários autenticadas devem conseguir postar algo no blog
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable().authorizeRequests()
                .antMatchers(AUTH_LIST).permitAll() //URI's que são acessadas sem autenticação (AUTH_LIST)
                .anyRequest().authenticated()		//any other precisam de autenticação
                .and().formLogin().permitAll()		//abre pagina padrão de login (pode-se criar uma pagina html para esse recurso)
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{	//metodo para configurar o usuário para acesso
        auth.inMemoryAuthentication()
                .withUser("henrique").password("{noop}123").roles("ADMIN");
    }

    @Override
    public void configure(WebSecurity web) throws Exception{	//metodo para ser usado quando importa-se pastas estáticas para a aplicação
        web.ignoring().antMatchers("/bootstrap/**");
//	        web.ignoring().antMatchers("/bootstrap/**", "/style/**");
    }
}

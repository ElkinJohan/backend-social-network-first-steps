package com.elkin.socialnetwork.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    //componenete proveedro que validara el token y se comunica con el server de autenticacion
    //para validar el usuario y password (login)
    private final UserAuthenticationProvider userAuthenticationProvider;

    public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint,
                          UserAuthenticationProvider userAuthenticationProvider) {
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //punto de entrada para manejar las excepciones de autenticacion para devolver mensaje personalizado
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                //un http filter dedicado a manejar la autenticacion regular, la auten del user y pass
                .addFilterBefore(new UsernamePasswordAuthFilter(userAuthenticationProvider), BasicAuthenticationFilter.class)
                //un http filtro dedicado a manejar autenticacoin con JWT
                .addFilterBefore(new JwtAuthFilter(userAuthenticationProvider), UsernamePasswordAuthFilter.class)
                .csrf().disable()
                //indicar que la sesion sera creeada, ya que estoy en una app STATELESS (sin estado)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //que puntos finales NO requieren la autenticacion
                .antMatchers(HttpMethod.POST, "/v1/signIn", "/v1/signUp").permitAll()
                //y el resto requerira autenticacion
                .anyRequest().authenticated();

    }
}

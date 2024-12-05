package com.elkin.socialnetwork.config;

import com.elkin.socialnetwork.dto.CredentialsDto;
import com.elkin.socialnetwork.dto.UserDto;
import com.elkin.socialnetwork.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@Component
public class UserAuthenticationProvider {

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String sercretKey;//se usa para construir el jwt

    private final AuthenticationService authenticationService;

    public UserAuthenticationProvider(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostConstruct
    protected void init() {
        //esto es para evitar tener la clave secreta sin procesar disponible en la JVM
        sercretKey = Base64.getEncoder().encodeToString(sercretKey.getBytes());
    }

    public String createToken(String login) {
        /*
        * el jwt esta dividdo en 3:
        * 1) Header -> tiene informacion sobre el algoritmo para cifrar el token y que tipo de token es.
        * 2) Payload -> tiene claims que son info estandar como nombre, fecha vencimiento, fecha creacion etc.
        * 3) Signature (Firma) -> es el header en base64, payload en base64 y la concatenacion cifrada de ambos
        * */
        Claims claims = Jwts.claims().setSubject(login);

        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000);//1 hora

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, sercretKey)
                .compact();
    }

    //para validar el token esta el metodo de analisis, si alguna informacion es incorrecta o la fecVencimi es en el pasado
    //se lanzara una excepcion
    public Authentication validateToken(String token) {
        String login = Jwts.parser()
                .setSigningKey(sercretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        //se devuelve este objeto que se utilizara en el security contxt para permitir que Spring inyecte la info del
        //usuario en el controller con la anotacion @AuthenticationPrincipal
        UserDto user = authenticationService.findByLogin(login);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    //autenticacion para usuario y password
    public Authentication validateCredentials(CredentialsDto credentialsDto) {
        UserDto user = authenticationService.authenticate(credentialsDto);
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

}

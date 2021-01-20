package by.unvisiblee.questionnaireApp.security;

import by.unvisiblee.questionnaireApp.exception.QuestionnaireServiceException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.User;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;

import static io.jsonwebtoken.Jwts.parser;

@Service
public class JwtProvider {

    private KeyStore keyStore;

    @PostConstruct
    public void init() {
        try {
            keyStore = KeyStore.getInstance("JKS");
            InputStream keyStoreAsStream = getClass().getResourceAsStream("/questionnaire.jks");
            keyStore.load(keyStoreAsStream, "secret".toCharArray());
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new QuestionnaireServiceException("Error occurred while initiating keystore");
        }
    }

    public String generateToken(Authentication authentication) {
        User principal =  (User) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(principal.getUsername())
                .signWith(getPrivateKey())
                .compact();
    }

    private PrivateKey getPrivateKey() {
        try {
            return (PrivateKey) keyStore.getKey("questionnaire", "secret".toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            throw new QuestionnaireServiceException("Error occured while getting private key from the keystore");
        }
    }


    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate("questionnaire").getPublicKey();
        } catch (KeyStoreException e) {
            throw new QuestionnaireServiceException("Error occurred while " +
                    "retrieving public key from keystore", e);
        }
    }

    public String getUsernameFromJwt(String jwt) {
        Claims claims = parser()
                .setSigningKey(getPublicKey())
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();
    }
}

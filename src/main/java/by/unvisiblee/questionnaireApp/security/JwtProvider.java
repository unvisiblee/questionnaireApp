package by.unvisiblee.questionnaireApp.security;

import by.unvisiblee.questionnaireApp.config.KeyStoreProperties;
import by.unvisiblee.questionnaireApp.exception.JwtException;
import by.unvisiblee.questionnaireApp.exception.QuestionnaireServiceException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
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

    private  KeyStore keyStore;
    private static final String KEY_STORE_ALIAS = "questionnaire";
    private KeyStoreProperties keyStoreProperties;


    @PostConstruct
    public void init() {
        try {
            InputStream keyStoreAsStream = getClass().getResourceAsStream("/" + KEY_STORE_ALIAS + ".jks");
            keyStore.load(keyStoreAsStream, keyStoreProperties.getPassword().toCharArray());
        } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
            throw new JwtException("Error occurred while initiating keystore");
        }
    }

    @Autowired
    public void setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
    }

    @Autowired
    public void setKeyStoreProperties(KeyStoreProperties keyStoreProperties) {
        this.keyStoreProperties = keyStoreProperties;
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
            return (PrivateKey) keyStore.getKey(KEY_STORE_ALIAS, keyStoreProperties.getPassword().toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | UnrecoverableKeyException ex) {
            throw new JwtException("Error occured while getting private key from the keystore");
        }
    }


    public boolean validateToken(String jwt) {
        Jwts.parser().setSigningKey(getPublicKey()).parseClaimsJws(jwt);
        return true;
    }

    private PublicKey getPublicKey() {
        try {
            return keyStore.getCertificate(KEY_STORE_ALIAS).getPublicKey();
        } catch (KeyStoreException e) {
            throw new JwtException("Error occurred while " +
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

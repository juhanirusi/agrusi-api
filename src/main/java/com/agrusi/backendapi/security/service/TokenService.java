package com.agrusi.backendapi.security.service;

import com.agrusi.backendapi.constants.AppConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public TokenService(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateJwt(Authentication authentication) {
        Instant now = Instant.now();
        Instant expirationTime = now.plus(5, ChronoUnit.SECONDS);

        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .issuer(AppConstants.AGRUSI_URL) // Who issued this token (WE)
                .issuedAt(now) // When token was issued
                .expiresAt(expirationTime) // When token expires
                .subject(authentication.getName()) // Who this token was issued to
                .claim("roles", scope) // What information this is holding (roles of the authenticated user)
                .build();

        // Lastly, encode the new generated token from the claims we
        // just configured and get the token value as a String

        return jwtEncoder.encode(JwtEncoderParameters.from(claimsSet)).getTokenValue();
    }
}

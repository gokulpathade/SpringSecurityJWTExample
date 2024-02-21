package com.security.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.security.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/*
 * 
 * extractUsername: Extracts the username from the given token.
        isValid: Checks if the provided token is valid for the given user.
      isTokenExpired: Checks if the given token is expired.
      extractExpiration: Extracts the expiration date from the token.
      extractClaim: Extracts a claim from the token using the provided resolver function.
       generateToken: Generates a token for the given user.
      getSigninKey: Retrieves the signing key for authentication.
 * 
 * 
 */

@Service
public class JWTServiceImpl {

//	Human-Readable Encryption Keys created by using 256-bit Hex Key:
	private final String SECRET_KEY = "9e9dd31a6bdd581125bd92449ecaf1caaa561946005183f2d2a1dca9b15eca3e";

	/**
	 * Extracts the username from the given token.
	 *
	 * @param token the input token
	 * @return the extracted username
	 */
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	/**
	 * Checks if the provided token is valid for the given user.
	 *
	 * @param token the token to be validated
	 * @param user  the user details for validation
	 * @return true if the token is valid for the user, false otherwise
	 */
	public boolean isValid(String token, UserDetails user) {
		String username = extractUsername(token);
		return (username.equals(user.getUsername())) && !isTokenExpired(token);
	}

	/**
	 * Checks if the given token is expired.
	 *
	 * @param token the token to be checked for expiration
	 * @return true if the token is expired, false otherwise
	 */

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	/**
	 * Extracts the expiration date from the token.
	 *
	 * @param token the token from which to extract the expiration date
	 * @return the expiration date extracted from the token
	 */
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	/**
	 * Extracts a claim from the token using the provided resolver function.
	 *
	 * @param token    the token from which to extract the claim
	 * @param resolver the function to apply to the extracted claims
	 * @return the result of applying the resolver function to the extracted claims
	 */
	private <T> T extractClaim(String token, Function<Claims, T> resolver) {
		Claims claims = extractAllClaims(token);
		return resolver.apply(claims);
	}

	/**
	 * A description of the entire Java function.
	 *
	 * @param token description of parameter
	 * @return description of return value
	 */
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getSigninKey()).build().parseSignedClaims(token).getPayload();
	}

	/**
	 * Generates a token for the given user.
	 *
	 * @param user the user for whom the token is generated
	 * @return the generated token
	 */
	public String generateToken(User user) {

		String token = Jwts.builder().subject(user.getEmail()).issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)).signWith(getSigninKey())
				.compact();
		return token;

	}

	/**
	 * This function retrieves the signing key for authentication.
	 *
	 * @return the signing key for authentication
	 */

	private SecretKey getSigninKey() {
		byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

//	
//    private Key getSignKey() {
//        byte[] keyBytes = Decoders.BASE64.decode("daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb");
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

//    public String extractUserName(String token) {
//        return extractClaim(token, Claims::getSubject);
//    }

//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }

//    private Claims extractAllClaims(String token) {
//        try {
//            return Jwts.parser().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
//        } catch (SignatureException e) {
//            // Handle SignatureException appropriately, e.g., log it or throw a custom exception
//            e.printStackTrace(); // Just printing the stack trace for demonstration purposes
//            return null; // Or throw a custom exception
//        } catch (Exception e) {
//            // Handle other exceptions if needed
//            e.printStackTrace(); // Just printing the stack trace for demonstration purposes
//            return null; // Or throw a custom exception
//        }
//    }

//    public boolean isTokenValid(String token, UserDetails userDetails) {
//        final String username = extractUserName(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }

//    private boolean isTokenExpired(String token) {
//        return extractClaim(token, Claims::getExpiration).before(new Date());
//    }
//}

}

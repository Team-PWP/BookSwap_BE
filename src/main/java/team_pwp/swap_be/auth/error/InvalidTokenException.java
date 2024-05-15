package team_pwp.swap_be.auth.error;

import io.jsonwebtoken.ExpiredJwtException;

public class InvalidTokenException extends ExpiredJwtException {

    public InvalidTokenException(String message) {
        super(null, null, message);
    }

}

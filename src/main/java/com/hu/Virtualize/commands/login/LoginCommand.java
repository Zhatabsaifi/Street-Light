package com.hu.Virtualize.commands.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCommand {
    private String id;
    private String password;

    // type must be ("USER", "ADMIN" )
    private String type;

    private Double latitude;
    private Double longitude;

    public LoginCommand(String id, String password, String type) {
        this.id = id;
        this.password = password;
        this.type = type;
        latitude = null;
        longitude = null;
    }
}
/**
 * for login enter
 * id, password, type , latitude, and longitude;
 */

/**
 * For validity send only
 * id and type
 */

/**
 * loginWithGoogleValidation
 * just send the id
 */
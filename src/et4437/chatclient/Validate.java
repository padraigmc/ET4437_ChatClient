/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et4437.chatclient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley      - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan  - 18222765@studentmai.ul.ie
 */
public class Validate {
    public static final String LOGIN_FAILED = "Login failed, please check username and password.";
    public static final String INVALID_USERNAME = "Usernames must be 3 - 30 characters and can only contain letters, numbers, \nfull stops, underscores and hyphens.";
    public static final String INVALID_PASSWORD = "Passwords must be 8 - 64 characters long and contain at least one number.";
    public static final String PASSWORD_NO_MATCH = "Passwords do not match.";
    
    // upper and lowercase chars, numbers, full stops (.), underscores (_) and hyphens (-)
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,30}$";
    
    // at least one number and lower case letter. Must be at least 8 chars and max 64 chars
    private static final String PASSWORD_REGEX = "^(?=.*[0-9]).{8,64}$";
    
    public static String validateRegister(String username, String password, String rptPassword) {
        String error = "";
                
        if (!validateUsername(username)) {
            error = INVALID_USERNAME;
        } else if (!validatePassword(password)) {
            error = INVALID_PASSWORD;
        } else if (!doPasswordsMatch(password, rptPassword))
            error = PASSWORD_NO_MATCH;
        return error;
    }
    
    public static boolean validateUsername(String username) {
        return Pattern.matches(USERNAME_REGEX, username);
    }
    
    public static boolean validatePassword(String password) {
        return Pattern.matches(PASSWORD_REGEX, password);
    }
    
    public static boolean doPasswordsMatch(String password, String rptPassword) {
        return password.equals(rptPassword);
    }
}

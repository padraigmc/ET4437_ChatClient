/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et4437.chatclient;

import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author Padraig McCarthy - 18227465@studentmail.ul.ie
 * @author Emma Hurley      - 17212723@studentmail.ul.ie
 * @author Lucy Dolan Egan  - 18222765@studentmai.ul.ie
 */
public class Validate {
    // upper and lowercase chars, numbers, full stops (.), underscores (_) and hyphens (-)
    private static final String USERNAME_REGEX = "^[a-zA-Z0-9._-]{3,30}$";
    
    // at least one number and lower case letter. Must be at least 8 chars and max 64 chars
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z]).{8,64}$";
    
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

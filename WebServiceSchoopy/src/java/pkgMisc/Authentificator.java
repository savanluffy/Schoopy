/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import java.util.HashSet;

/**
 *
 * @author schueler
 */
public class Authentificator {

    private static HashSet<String> collTokens = new HashSet<>();

    public static boolean isUserAuthenticated(String token) {
        return collTokens.contains(token);
    }

    public static void loginToken(String token) {
        if (!collTokens.contains(token)) {
            collTokens.add(token);
        }
    }

    public static void logoutToken(String token) {
        if (collTokens.contains(token)) {
            collTokens.remove(token);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

/**
 *
 * @author schueler
 */
public class StringConverter {

    public static String generatePW() {
        int length = 15;
        String digits = "0123456789";
        String specials = "~=+%^*/()[]{}/!@#$?|";
        String all = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + digits + specials;
        Random rnd = new Random();
        List<String> result = new ArrayList<>();
        Consumer<String> appendChar = s
                -> result.add("" + s.charAt(rnd.nextInt(s.length())));
        appendChar.accept(digits);
        appendChar.accept(specials);
        while (result.size() < length) {
            appendChar.accept(all);
        }
        Collections.shuffle(result, rnd);
        return String.join("", result);
    }

}

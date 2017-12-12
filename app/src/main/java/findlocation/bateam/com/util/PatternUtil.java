package findlocation.bateam.com.util;

import java.util.regex.Pattern;

/**
 * Created by acv on 12/11/17.
 */

public class PatternUtil {

//    public static boolean checkPasswordCharacter(String password) {
//        // Minimum eight characters, at least one uppercase letter, one lowercase letter and one number:
//        Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
//        return p.matcher(password).matches();
//    }

    public static boolean checkPasswordCharacter(String password) {
        // Minimum eight characters, at one lowercase letter and one number:
        Pattern p = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
        return p.matcher(password).matches();
    }

}

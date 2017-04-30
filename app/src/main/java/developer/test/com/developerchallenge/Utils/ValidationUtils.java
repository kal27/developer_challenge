package developer.test.com.developerchallenge.Utils;

import android.widget.EditText;
import android.widget.Toast;

import developer.test.com.developerchallenge.Activities.MainActivity;


public class ValidationUtils {
    private static final String CODE_ERR = "The airport codes need to be between 2 and 4 characters long.";
    private static final String DATE_ERR = "Invalid format. Please enter date in YYYY-MM-DD format.";

    public static boolean validateAirportCode(String input, MainActivity mainActivity) {
        if (input != null) {
            if ((input.length() >= 2) && (input.length() <= 4)) {
                return true;
            }
        }
        Toast.makeText(mainActivity, CODE_ERR, Toast.LENGTH_SHORT).show();

        return false;
    }

    public static boolean validateDate(String dateString, EditText field) {
        //basic validation
        if (dateString.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return true;
        }

        field.setError(DATE_ERR);

        return false;
    }


}

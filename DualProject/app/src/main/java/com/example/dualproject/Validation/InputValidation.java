package com.example.dualproject.Validation;

import android.content.Context;
import android.util.Patterns;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
    Context context;
    public InputValidation(Context context)
    {
        this.context=context;
    }
    public boolean isInputEdiTextFilled(EditText editText,String message)
    {
        String value=editText.getText().toString().trim();
        if(value.isEmpty())
        {
            editText.setError(message);
            return false;
        }
        else
            return true;


    }
    public boolean password(EditText editText,String message)
    {
        String value=editText.getText().toString().trim();
        if(value.length()<6 || value.length()>15)
        {
            editText.setError(message);
            return false;
        }
        else
            return true;
    }

    public boolean isInputEditTextValid(EditText editText,String message)
    {
        String value =editText.getText().toString().trim();
        if(value.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(value).matches())
        {
            editText.setError(message);
            return false;
        }
        else

          return true ;
    }
    public static boolean isInputEditTextMatches(EditText editText1, EditText editText2, String message)
    {
        String value1=editText1.getText().toString().trim();
        String value2=editText2.getText().toString().trim();
        if(!value1.contentEquals(value2))
        {
            editText2.setError(message);
            return false;
        }
        else
            return true;
    }
    public boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();

    }
    public boolean isInputEditTextPhone(EditText editText,String message)
    {
        String value =editText.getText().toString().trim();
        if(value.isEmpty() || !Patterns.PHONE.matcher(value).matches())
        {
            editText.setError(message);
            return false;
        }
        else

            return true ;
    }

}

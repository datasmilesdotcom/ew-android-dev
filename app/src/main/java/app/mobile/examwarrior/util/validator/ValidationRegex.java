package app.mobile.examwarrior.util.validator;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;

import java.util.regex.Pattern;

public class ValidationRegex extends Validation {
    private Pattern mPattern;

    public ValidationRegex(Pattern pattern) {
        mPattern = pattern;
    }

    @Override
    public boolean isValid(View view) {
        return mPattern.matcher(((AppCompatEditText) view).getText().toString().trim()).matches();
    }
}

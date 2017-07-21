package app.mobile.examwarrior.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import app.mobile.examwarrior.util.Utility;


public class CustomFontTextView extends AppCompatTextView {
    private static final String sScheme =
            "http://schemas.android.com/apk/res-auto";
    private static final String sAttribute = "tv_customFont";

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode()) {
            return;
        }

        String fontName = attrs.getAttributeValue(sScheme, sAttribute);
        if (!Utility.isEmpty(fontName)) {
            try {
                final Typeface customTypeface = StaticTypeface.asTypeface(context, fontName);
                if (customTypeface != null) setTypeface(customTypeface);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package app.mobile.examwarrior.widget;

import android.content.Context;
import android.graphics.Typeface;


/**
 * Created by sandesh on 20/4/16.
 */
public class StaticTypeface {

    public static Typeface asTypeface(Context context, String fontName) {
        try {
            return FontCache.getTypeface("fonts/" + fontName, context);
        } catch (Exception e) {

        }
        return null;
    }
}

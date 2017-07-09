package app.mobile.examwarrior.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.LruCache;

/**
 * Created by sandesh on 19/4/16.
 */
class FontCache {
    private static final LruCache<String, Typeface> sTypefaceCache = new LruCache<>(4);


    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = sTypefaceCache.get(fontname);
        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }
            sTypefaceCache.put(fontname, typeface);
        }
        return typeface;
    }


}

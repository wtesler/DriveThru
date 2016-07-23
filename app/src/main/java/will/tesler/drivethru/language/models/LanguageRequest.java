package will.tesler.drivethru.language.models;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class LanguageRequest {

    public LanguageDocument document;
    public Features features;
    public @EncodingType String encodingType;

    @Retention(SOURCE)
    @StringDef({
            UTF8,
            UTF16,
            UTF32
    })
    public @interface EncodingType {}
    public static final String UTF8 = "UTF8";
    public static final String UTF16 = "UTF16";
    public static final String UTF32 = "UTF32";
}

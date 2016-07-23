package will.tesler.drivethru.language.models;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class LanguageDocument {

    public @TextType String type;
    public @Iso String language;
    public String content;

    @Retention(SOURCE)
    @StringDef({
            TYPE_UNSPECIFIED,
            PLAIN_TEXT,
            HTML
    })
    public @interface TextType {}
    public static final String TYPE_UNSPECIFIED = "TYPE_UNSPECIFIED";
    public static final String PLAIN_TEXT = "PLAIN_TEXT";
    public static final String HTML = "HTML";


    @Retention(SOURCE)
    @StringDef({
            ENGLISH,
            SPANISH,
            JAPANESE
    })
    public @interface Iso {}
    public static final String ENGLISH = "en";
    public static final String SPANISH = "es";
    public static final String JAPANESE = "ja";
}

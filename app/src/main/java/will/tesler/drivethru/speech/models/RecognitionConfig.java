package will.tesler.drivethru.speech.models;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class RecognitionConfig {

    public @AudioEncoding String encoding;
    public int sampleRate;
    public String languageCode;
    public int maxAlternatives;
    public boolean profanityFilter;
    public SpeechContext speechContext;


    @Retention(SOURCE)
    @StringDef({
            ENCODING_UNSPECIFIED,
            LINEAR16,
            FLAC,
            MULAW,
            AMR,
            AMR_WB
    })
    public @interface AudioEncoding {}
    public static final String ENCODING_UNSPECIFIED = "ENCODING_UNSPECIFIED";
    public static final String LINEAR16 = "LINEAR16";
    public static final String FLAC = "FLAC";
    public static final String MULAW = "MULAW";
    public static final String AMR = "AMR";
    public static final String AMR_WB = "AMR_WB";
}

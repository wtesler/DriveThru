package will.tesler.drivethru.language.models;

import android.support.annotation.FloatRange;

public class Sentiment {

    public @FloatRange(from = -1.0, to = 1.0) float polarity;
    public @FloatRange(from = 0) float magnitude;
}

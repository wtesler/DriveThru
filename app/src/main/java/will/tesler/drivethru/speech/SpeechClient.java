package will.tesler.drivethru.speech;

import android.support.annotation.NonNull;

import rx.Observable;
import will.tesler.drivethru.speech.models.GcsSpeechRequest;
import will.tesler.drivethru.speech.models.SpeechResponse;
import will.tesler.drivethru.utils.RxUtils;

public class SpeechClient {

    SpeechService mSpeechService;

    public SpeechClient(SpeechService service) {
        mSpeechService = service;
    }

    public Observable<SpeechResponse> analyze(@NonNull GcsSpeechRequest request) {
        return mSpeechService.getSpeechResults(request)
                .compose(RxUtils.<SpeechResponse>retrofitTransformer());
    }
}

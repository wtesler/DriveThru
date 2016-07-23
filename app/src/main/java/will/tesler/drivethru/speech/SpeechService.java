package will.tesler.drivethru.speech;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;
import will.tesler.drivethru.speech.models.GcsSpeechRequest;
import will.tesler.drivethru.speech.models.SpeechResponse;

public interface SpeechService {

    @POST("v1beta1/speech:syncrecognize")
    Observable<SpeechResponse> getSpeechResults(@Body GcsSpeechRequest request);
}

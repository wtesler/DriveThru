package will.tesler.drivethru.language;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;
import will.tesler.drivethru.language.models.GcsLanguageRequest;
import will.tesler.drivethru.language.models.LanguageRequest;
import will.tesler.drivethru.language.models.LanguageResponse;

public interface LanguageService {

    @POST("v1beta1/documents:annotateText")
    Observable<LanguageResponse> parse(@Body LanguageRequest request);

    @POST("v1beta1/documents:annotateText")
    Observable<LanguageResponse> parse(@Body GcsLanguageRequest request);
}

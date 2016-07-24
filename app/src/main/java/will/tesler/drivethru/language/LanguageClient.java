package will.tesler.drivethru.language;


import android.support.annotation.NonNull;

import com.squareup.okhttp.ResponseBody;

import rx.Observable;
import will.tesler.drivethru.language.models.GcsLanguageRequest;
import will.tesler.drivethru.language.models.LanguageRequest;
import will.tesler.drivethru.language.models.LanguageResponse;
import will.tesler.drivethru.utils.RxUtils;

public class LanguageClient {

    private final LanguageService mLanguageService;

    public LanguageClient(LanguageService languageService) {
        mLanguageService = languageService;
    }

    public Observable<LanguageResponse> parse(@NonNull LanguageRequest request) {
        return mLanguageService.parse(request)
                .compose(RxUtils.<LanguageResponse>retrofitTransformer());
    }

    public Observable<LanguageResponse> parse(@NonNull GcsLanguageRequest request) {
        return mLanguageService.parse(request)
                .compose(RxUtils.<LanguageResponse>retrofitTransformer());
    }

    public Observable<ResponseBody> rawParse(@NonNull LanguageRequest request) {
        return mLanguageService.rawParse(request)
                .compose(RxUtils.<ResponseBody>retrofitTransformer());
    }
}

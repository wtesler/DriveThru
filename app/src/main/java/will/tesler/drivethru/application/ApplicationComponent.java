package will.tesler.drivethru.application;

import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;
import will.tesler.drivethru.activities.MainActivity;
import will.tesler.drivethru.speech.SpeechClient;
import will.tesler.drivethru.language.LanguageClient;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity activity);

    LanguageClient languageClient();

    OkHttpClient okHttpClient();

    @ApplicationModule.ForLanguage
    Retrofit languageRetrofit();

    SpeechClient speechClient();

    @ApplicationModule.ForSpeech
    Retrofit speechRetrofit();
}

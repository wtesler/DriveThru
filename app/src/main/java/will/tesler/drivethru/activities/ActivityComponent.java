package will.tesler.drivethru.activities;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;
import will.tesler.drivethru.controllers.AnalyzeController;
import will.tesler.drivethru.controllers.HistoryController;
import will.tesler.drivethru.navigation.DrawerController;
import will.tesler.drivethru.speech.SpeechClient;
import will.tesler.drivethru.language.LanguageClient;

@Singleton
@Component(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(AnalyzeController analyzeController);

    void inject(DrawerController drawerController);

    void inject(HistoryController historyController);

    void inject(MainActivity mainActivity);

    AnalyzeController analyzeController();

    DrawerController drawerController();

    Gson gson();

    HistoryController historyController();

    LanguageClient languageClient();

    @ActivityModule.ForLanguage
    OkHttpClient okHttpClient();

    @ActivityModule.ForLanguage
    Retrofit languageRetrofit();

    SpeechClient speechClient();

    @ActivityModule.ForSpeech
    Retrofit speechRetrofit();

    @ActivityModule.ForUpload
    Retrofit storageRetrofit();

    @ActivityModule.ForUpload
    OkHttpClient uploadHttpClient();
}

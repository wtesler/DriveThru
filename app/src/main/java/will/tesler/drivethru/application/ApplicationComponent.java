package will.tesler.drivethru.application;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit.Retrofit;
import will.tesler.drivethru.activities.MainActivity;
import will.tesler.drivethru.controllers.AnalyzeController;
import will.tesler.drivethru.controllers.HistoryController;
import will.tesler.drivethru.navigation.DrawerController;
import will.tesler.drivethru.speech.SpeechClient;
import will.tesler.drivethru.language.LanguageClient;
import will.tesler.drivethru.ui.DrawerTextTransformer;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(AnalyzeController analyzeController);

    void inject(DrawerController drawerController);

    void inject(HistoryController historyController);

    void inject(MainActivity mainActivity);

    AnalyzeController analyzeController();

    DrawerController drawerController();

    Gson gson();

    HistoryController historyController();

    LanguageClient languageClient();

    @ApplicationModule.ForLanguage
    OkHttpClient okHttpClient();

    @ApplicationModule.ForLanguage
    Retrofit languageRetrofit();

    SpeechClient speechClient();

    @ApplicationModule.ForSpeech
    Retrofit speechRetrofit();

    @ApplicationModule.ForUpload
    Retrofit storageRetrofit();

    @ApplicationModule.ForUpload
    OkHttpClient uploadHttpClient();
}

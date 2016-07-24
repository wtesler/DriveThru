package will.tesler.drivethru.application;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Qualifier;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import will.tesler.drivethru.language.LanguageClient;
import will.tesler.drivethru.language.LanguageService;
import will.tesler.drivethru.security.GoogleAuth;
import will.tesler.drivethru.speech.SpeechClient;
import will.tesler.drivethru.speech.SpeechService;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Module
public class ApplicationModule {

    @Provides
    @Singleton
    LanguageClient provideLanguageClient(@ForLanguage Retrofit retrofit) {
        LanguageService languageService = retrofit.create(LanguageService.class);
        return new LanguageClient(languageService);
    }

    @Provides
    @ForLanguage
    @Singleton
    OkHttpClient provideLanguageOkHttpClient() {
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.httpUrl();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter("key", GoogleAuth.BROWSER_KEY)
                        .build();

                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);

        return httpClient;
    }

    @Provides
    @ForUpload
    @Singleton
    OkHttpClient provideStorageOkHttpClient() {
        OkHttpClient httpClient = new OkHttpClient();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.interceptors().add(interceptor);

        return httpClient;
    }

    @Provides
    @ForLanguage
    @Singleton
    Retrofit provideLanguageRetrofit(@ForLanguage OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://language.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    SpeechClient provideSpeechClient(@ForSpeech Retrofit retrofit) {
        SpeechService speechService = retrofit.create(SpeechService.class);
        return new SpeechClient(speechService);
    }

    @Provides
    @ForSpeech
    @Singleton
    Retrofit provideSpeechRetrofit(@ForLanguage OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://speech.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ForUpload
    @Singleton
    Retrofit provideUploadRetrofit(@ForUpload OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    StorageReference provideStorageReference() {
        return FirebaseStorage.getInstance().getReference();
    }

    @Qualifier
    @Documented
    @Retention(RUNTIME)
    public @interface ForLanguage { }

    @Qualifier
    @Documented
    @Retention(RUNTIME)
    public @interface ForSpeech { }

    @Qualifier
    @Documented
    @Retention(RUNTIME)
    public @interface ForUpload { }
}

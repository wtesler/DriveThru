package will.tesler.drivethru.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import javax.inject.Inject;

import rx.Subscription;
import rx.functions.Action1;
import will.tesler.drivethru.R;
import will.tesler.drivethru.application.DriveThruApplication;
import will.tesler.drivethru.language.LanguageClient;
import will.tesler.drivethru.language.models.Features;
import will.tesler.drivethru.language.models.GcsLanguageDocument;
import will.tesler.drivethru.language.models.GcsLanguageRequest;
import will.tesler.drivethru.language.models.LanguageDocument;
import will.tesler.drivethru.language.models.LanguageRequest;
import will.tesler.drivethru.language.models.LanguageResponse;
import will.tesler.drivethru.speech.SpeechClient;
import will.tesler.drivethru.speech.models.GcsAudio;
import will.tesler.drivethru.speech.models.GcsSpeechRequest;
import will.tesler.drivethru.speech.models.RecognitionConfig;
import will.tesler.drivethru.speech.models.SpeechResponse;
import will.tesler.drivethru.utils.RxUtils;

import static will.tesler.drivethru.speech.models.RecognitionConfig.FLAC;

public class MainActivity extends AppCompatActivity {

    @Inject LanguageClient mLanguageClient;
    @Inject SpeechClient mSpeechClient;

    @Nullable private Subscription languageSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((DriveThruApplication) getApplication()).getApplicationComponent().inject(this);

        requestSpeechAnalysis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RxUtils.unsubscribe(languageSubscription);
    }

    private void requestLanguageAnalysis() {
        GcsLanguageDocument document = new GcsLanguageDocument();
        document.gcsContentUri = "gs://dialogs/example_0.txt";
        document.language = LanguageDocument.ENGLISH;
        document.type = LanguageDocument.PLAIN_TEXT;

        Features features = new Features();
        features.extractSyntax = true;

        GcsLanguageRequest request = new GcsLanguageRequest();
        request.document = document;
        request.features = features;
        request.encodingType = LanguageRequest.UTF8;

        languageSubscription = mLanguageClient.parse(request)
                .subscribe(new Action1<LanguageResponse>() {
                    @Override
                    public void call(@NonNull LanguageResponse languageResponse) {
                        Log.i("MainActivity", languageResponse.sentences[0].text.content);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MainActivity", "Could not parse request.");
                    }
                });
    }

    private void requestSpeechAnalysis() {
        RecognitionConfig config = new RecognitionConfig();
        config.encoding = FLAC;
        config.sampleRate = 16000;

        GcsAudio audio = new GcsAudio();
        audio.uri = "gs://cloud-samples-tests/speech/brooklyn.flac";

        GcsSpeechRequest request = new GcsSpeechRequest();
        request.config = config;
        request.audio = audio;

        mSpeechClient.analyze(request)
                .subscribe(new Action1<SpeechResponse>() {
                    @Override
                    public void call(@NonNull SpeechResponse speechResponse) {
                        Log.i("MainActivity", speechResponse.results.get(0).alternatives.get(0).transcript);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MainActivity", "Could not analyze request.");
                    }
                });
    }
}

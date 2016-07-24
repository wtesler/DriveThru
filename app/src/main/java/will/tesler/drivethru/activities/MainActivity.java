package will.tesler.drivethru.activities;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;
import will.tesler.drivethru.R;
import will.tesler.drivethru.analysis.DependencyTree;
import will.tesler.drivethru.application.DriveThruApplication;
import will.tesler.drivethru.language.LanguageClient;
import will.tesler.drivethru.language.models.Features;
import will.tesler.drivethru.language.models.GcsLanguageDocument;
import will.tesler.drivethru.language.models.GcsLanguageRequest;
import will.tesler.drivethru.language.models.LanguageDocument;
import will.tesler.drivethru.language.models.LanguageRequest;
import will.tesler.drivethru.language.models.LanguageResponse;
import will.tesler.drivethru.language.models.Sentence;
import will.tesler.drivethru.language.models.Token;
import will.tesler.drivethru.speech.SpeechClient;
import will.tesler.drivethru.speech.models.GcsAudio;
import will.tesler.drivethru.speech.models.GcsSpeechRequest;
import will.tesler.drivethru.speech.models.RecognitionConfig;
import will.tesler.drivethru.speech.models.SpeechResponse;
import will.tesler.drivethru.ui.Section;
import will.tesler.drivethru.ui.SentenceTransformer;
import will.tesler.drivethru.ui.TokenRowTransformer;
import will.tesler.drivethru.ui.TreeTransformer;
import will.tesler.drivethru.ui.UniversalAdapter;
import will.tesler.drivethru.utils.RxUtils;
import will.tesler.drivethru.utils.UiUtils;

import static will.tesler.drivethru.speech.models.RecognitionConfig.FLAC;

public class MainActivity extends AppCompatActivity {

    @Inject LanguageClient mLanguageClient;
    @Inject SpeechClient mSpeechClient;
    @Inject StorageReference mStorageReference;

    @Bind(R.id.button_analyze) Button mButtonAnalyze;
    @Bind(R.id.edittext_query) EditText mEditTextQuery;
    @Bind(R.id.recylclerview_main) RecyclerView mRecyclerView;

    @Nullable private Subscription languageSubscription;
    @NonNull private UniversalAdapter mAdapter = new UniversalAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ((DriveThruApplication) getApplication()).getApplicationComponent().inject(this);

        mAdapter.register(Sentence.class, SentenceTransformer.class);
        mAdapter.register(Token[].class, TokenRowTransformer.class);
        mAdapter.register(DependencyTree.class, TreeTransformer.class);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

//        GcsLanguageRequest request = constructGcsLanguageRequest("gs://dialogs/example_1.txt");
//        requestLanguageAnalysis(mLanguageClient.parse(request));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        RxUtils.unsubscribe(languageSubscription);
    }

    @OnClick(R.id.button_analyze)
    public void onAnalyzeClick() {
        String query = mEditTextQuery.getText().toString();
        if (!query.equals("")) {
            LanguageRequest request = constructLanguageRequest(query);
            requestLanguageAnalysis(mLanguageClient.rawParse(request));

            mEditTextQuery.setText("");
            mRecyclerView.scrollToPosition(0);
            UiUtils.hideSoftKeyboard(this);
        }
    }

    private void requestLanguageAnalysis(@NonNull final Observable<ResponseBody> response) {
        languageSubscription = response
                .subscribe(new Action1<ResponseBody>() {
                    @Override
                    public void call(@NonNull ResponseBody responseBody) {
                        try {
                            String jsonResponse = responseBody.string();
                            Gson gson = new Gson();
                            LanguageResponse languageResponse = gson.fromJson(jsonResponse, LanguageResponse.class);
                            storeLanguageResponse(jsonResponse);
                            visualizeLanguageResponse(languageResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MainActivity", "Error requesting analysis.", throwable);
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

        final long startTime = SystemClock.elapsedRealtime();

        mSpeechClient.analyze(request)
                .concatMap(new Func1<SpeechResponse, Observable<LanguageResponse>>() {
                    @Override
                    public Observable<LanguageResponse> call(@NonNull SpeechResponse speechResponse) {
                        String transcript = speechResponse.results.get(0).alternatives.get(0).transcript;
                        LanguageRequest request = constructLanguageRequest(transcript);
                        return mLanguageClient.parse(request);
                    }
                })
                .subscribe(new Action1<LanguageResponse>() {
                    @Override
                    public void call(@NonNull LanguageResponse languageResponse) {

                        visualizeLanguageResponse(languageResponse);

                        Log.i("MainActivity", "Time to complete: "
                                + Long.toString(SystemClock.elapsedRealtime() - startTime));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("MainActivity", "Could not parse request.", throwable);
                    }
                });
    }

    private void visualizeLanguageResponse(@NonNull LanguageResponse languageResponse) {
        int currentTokenIndex = 0;
        int startTokenIndex = 0;

        for (int i = 0; i < languageResponse.sentences.length; i++) {
            Section section = new Section();

            section.add(languageResponse.sentences[i]);

            int endOffset = i < languageResponse.sentences.length - 1
                    ? languageResponse.sentences[i + 1].text.beginOffset
                    : Integer.MAX_VALUE;

            ArrayList<Token> sentenceTokenList = new ArrayList<>();

            for (int j = currentTokenIndex; j < languageResponse.tokens.length; j++) {
                Token currentToken = languageResponse.tokens[currentTokenIndex];
                if (currentToken.text.beginOffset < endOffset) {
                    sentenceTokenList.add(currentToken);
                    currentTokenIndex++;
                } else {
                    break;
                }
            }

            Token[] sentenceTokens = sentenceTokenList.toArray(new Token[sentenceTokenList.size()]);
            section.add(sentenceTokens);

            DependencyTree dependencyTree = new DependencyTree(sentenceTokens, startTokenIndex);
            section.add(dependencyTree);

            mAdapter.add(section, 0);

            startTokenIndex = currentTokenIndex;
        }
    }

    private void storeLanguageResponse(String jsonResponse) {
        StringBuilder filePathBuilder = new StringBuilder("analysis/")
            .append(UUID.randomUUID().toString());

        StorageReference searchReference = mStorageReference.child(filePathBuilder.toString());

        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("text/plain")
                .build();

        searchReference.putBytes(jsonResponse.getBytes(), metadata)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        Log.i("MainActivity", "Success!");
                        Log.i("MainActivity", "Transferred bytes: " + taskSnapshot.getBytesTransferred());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.e("MainActivity", "Failure!", exception);
                    }
                });
    }

    private LanguageRequest constructLanguageRequest(String content) {
        LanguageDocument document = new LanguageDocument();
        document.content = content;
        document.language = LanguageDocument.ENGLISH;
        document.type = LanguageDocument.PLAIN_TEXT;

        Features features = new Features();
        features.extractSyntax = true;

        LanguageRequest request = new LanguageRequest();
        request.document = document;
        request.features = features;
        request.encodingType = LanguageRequest.UTF8;

        return request;
    }

    private GcsLanguageRequest constructGcsLanguageRequest(String gcsUri) {
        GcsLanguageDocument document = new GcsLanguageDocument();
        document.gcsContentUri = gcsUri;
        document.language = LanguageDocument.ENGLISH;
        document.type = LanguageDocument.PLAIN_TEXT;

        Features features = new Features();
        features.extractSyntax = true;

        GcsLanguageRequest request = new GcsLanguageRequest();
        request.document = document;
        request.features = features;
        request.encodingType = LanguageRequest.UTF8;

        return request;
    }
}

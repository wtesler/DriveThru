package will.tesler.drivethru.ui;

import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.language.models.Sentence;

public class SentenceTransformer extends UniversalAdapter.Transformer<Sentence> {

    @Bind(R.id.textview_sentence) TextView mTextViewSentence;

    public SentenceTransformer(ViewGroup parent) {
        super(R.layout.row_sentence, parent);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void transform(Sentence model) {
        mTextViewSentence.setText(model.text.content);
    }
}

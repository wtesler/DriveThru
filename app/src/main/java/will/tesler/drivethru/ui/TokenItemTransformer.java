package will.tesler.drivethru.ui;

import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.adapter.UniversalAdapter;
import will.tesler.drivethru.adapter.UniversalSubject;
import will.tesler.drivethru.language.models.Token;

public class TokenItemTransformer extends UniversalAdapter.Transformer<Token> {

    @Bind(R.id.textview_lemma) TextView mTextViewLemma;
    @Bind(R.id.textview_partofspeech) TextView mTextViewPartOfSpeech;

    public TokenItemTransformer(ViewGroup parent) {
        super(R.layout.item_part_of_speech, parent);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void transform(Token token, final UniversalSubject universalSubject) {
        mTextViewLemma.setText(token.lemma);
        mTextViewPartOfSpeech.setText(token.partOfSpeech.tag);
    }
}

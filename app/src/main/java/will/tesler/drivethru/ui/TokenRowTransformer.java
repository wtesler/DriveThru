package will.tesler.drivethru.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.language.models.Token;

public class TokenRowTransformer extends UniversalAdapter.Transformer<Token[]> {

    @Bind(R.id.recyclerview_partofspeech) RecyclerView mRecyclerViewPartOfSpeech;

    public TokenRowTransformer(ViewGroup parent) {
        super(R.layout.row_part_of_speech, parent);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void transform(Token[] tokens) {
        UniversalAdapter adapter = new UniversalAdapter();
        adapter.register(Token.class, TokenItemTransformer.class);

        mRecyclerViewPartOfSpeech.setAdapter(adapter);

        mRecyclerViewPartOfSpeech.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        for (Token token : tokens) {
            adapter.add(token);
        }
    }
}

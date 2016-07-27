package will.tesler.drivethru.controllers;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.activities.MainActivity;
import will.tesler.drivethru.ui.UniversalAdapter;

public class HistoryController extends Controller {

    @Bind(R.id.recyclerview_history) RecyclerView mRecyclerView;

    private final UniversalAdapter mAdapter = new UniversalAdapter();

    @Override
    public void attachTo(MainActivity mainActivity, ViewGroup parent) {
        inflate(R.layout.layout_history, parent);

        ButterKnife.bind(this, getView());

        mainActivity.getActivityComponent().inject(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    @Override
    public void detach() { }

    private void loadMetadata() { }
}

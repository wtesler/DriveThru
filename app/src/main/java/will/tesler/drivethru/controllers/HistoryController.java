package will.tesler.drivethru.controllers;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.activities.MainActivity;
import will.tesler.drivethru.ui.UniversalAdapter;

public class HistoryController implements Controller {

    @Bind(R.id.recyclerview_history) RecyclerView mRecyclerView;

    @NonNull private UniversalAdapter mAdapter = new UniversalAdapter();

    private View mView;
    private MainActivity mActivity;

    @Override
    public void attachTo(MainActivity mainActivity, ViewGroup parent) {
        if (mView == null) {
            mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history, parent, false);
            parent.addView(mView);
            ButterKnife.bind(this, mView);
        } else {
            throw new IllegalStateException("Cannot attach twice without detaching.");
        }

        mActivity = mainActivity;

        mainActivity.getActivityComponent().inject(this);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
    }

    @Override
    public void detach() { }

    @Override
    public void show() {
        mView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        mView.setVisibility(View.GONE);
    }
}

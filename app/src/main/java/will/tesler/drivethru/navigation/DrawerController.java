package will.tesler.drivethru.navigation;

import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.activities.MainActivity;
import will.tesler.drivethru.controllers.AnalyzeController;
import will.tesler.drivethru.controllers.HistoryController;
import will.tesler.drivethru.navigation.models.DrawerTextItem;
import will.tesler.drivethru.ui.DrawerTextTransformer;
import will.tesler.drivethru.ui.UniversalAdapter;

public class DrawerController {

    @Inject AnalyzeController mAnalyzeController;
    @Inject HistoryController mHistoryController;

    @Bind(R.id.recyclerview_drawer) RecyclerView mRecyclerView;

    private final UniversalAdapter mUniversalAdapter = new UniversalAdapter();

    @Nullable private MainActivity mActivity;

    public void attachTo(MainActivity activity, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drawer, parent, true);
        ButterKnife.bind(this, view);

        mActivity = activity;

        mActivity.getActivityComponent().inject(this);

        mUniversalAdapter.register(DrawerTextItem.class, DrawerTextTransformer.class);
        mRecyclerView.setAdapter(mUniversalAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));

        addAnalyze();
        addHistory();
    }

    private void addAnalyze() {
        DrawerTextItem item = new DrawerTextItem();
        item.title = "Analyze";
        item.iconResource = android.R.drawable.ic_menu_search;
        item.controller = mAnalyzeController;

        mUniversalAdapter.add(item);
    }

    private void addHistory() {
        DrawerTextItem item = new DrawerTextItem();
        item.title = "History";
        item.iconResource = android.R.drawable.ic_menu_recent_history;
        item.controller = mHistoryController;

        mUniversalAdapter.add(item);
    }
}

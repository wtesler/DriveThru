package will.tesler.drivethru.navigation;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.functions.Action1;
import will.tesler.drivethru.R;
import will.tesler.drivethru.activities.MainActivity;
import will.tesler.drivethru.analysis.AnalyzeController;
import will.tesler.drivethru.controllers.Controller;
import will.tesler.drivethru.history.HistoryController;
import will.tesler.drivethru.navigation.models.DrawerTextItem;
import will.tesler.drivethru.ui.DrawerTextTransformer;
import will.tesler.drivethru.adapter.UniversalAdapter;

public class DrawerController extends Controller {

    @Inject AnalyzeController mAnalyzeController;
    @Inject HistoryController mHistoryController;

    @Bind(R.id.recyclerview_drawer) RecyclerView mRecyclerView;

    private final UniversalAdapter mUniversalAdapter = new UniversalAdapter();

    private MainActivity mMainActivity;

    @Override
    public void attachTo(final MainActivity activity, ViewGroup parent) {
        inflate(R.layout.layout_drawer, parent);

        ButterKnife.bind(this, getView());

        mMainActivity = activity;

        mMainActivity.getActivityComponent().inject(this);

        mUniversalAdapter.register(DrawerTextItem.class, DrawerTextTransformer.class);
        mRecyclerView.setAdapter(mUniversalAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(parent.getContext()));

        subscribeToDrawerItemClicks();

        addAnalyze();
        addHistory();
    }

    @Override
    public void detach() { }

    private void addAnalyze() {
        DrawerTextItem item = new DrawerTextItem();
        item.title = getView().getResources().getString(R.string.drawer_analyze);
        item.iconResource = android.R.drawable.ic_menu_search;
        item.controller = mAnalyzeController;

        mUniversalAdapter.add(item);
    }

    private void addHistory() {
        DrawerTextItem item = new DrawerTextItem();
        item.title = getView().getResources().getString(R.string.drawer_history);
        item.iconResource = android.R.drawable.ic_menu_recent_history;
        item.controller = mHistoryController;

        mUniversalAdapter.add(item);
    }

    private void subscribeToDrawerItemClicks() {
        mUniversalAdapter.getObservable(DrawerTextItem.class, DrawerTextTransformer.ACTION_CLICK)
                .subscribe(new Action1<DrawerTextItem>() {
                    @Override
                    public void call(@NonNull DrawerTextItem drawerTextItem) {
                        mMainActivity.setController(drawerTextItem.controller);
                        mMainActivity.closeDrawer();
                    }
                });
    }
}

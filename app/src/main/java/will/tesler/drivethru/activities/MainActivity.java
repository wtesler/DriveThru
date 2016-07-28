package will.tesler.drivethru.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.analysis.AnalyzeController;
import will.tesler.drivethru.controllers.Controller;
import will.tesler.drivethru.history.HistoryController;
import will.tesler.drivethru.navigation.DrawerController;
import will.tesler.drivethru.utils.UiUtils;

public class MainActivity extends AppCompatActivity {

    @Inject AnalyzeController mAnalyzeController;
    @Inject DrawerController mDrawerController;
    @Inject HistoryController mHistoryController;

    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.viewgroup_content) ViewGroup mViewGroupContent;
    @Bind(R.id.viewgroup_drawer) ViewGroup mViewGroupDrawer;

    @Nullable private ActionBarDrawerToggle mToggle;

    private final List<Controller> mControllerList = new ArrayList<>();

    private ActivityComponent mActivityComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        mActivityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule())
                .build();

        mActivityComponent.inject(this);

        mToggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

        };
        mDrawerLayout.addDrawerListener(mToggle);
        mDrawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                UiUtils.hideSoftKeyboard(MainActivity.this);
            }
        });
        mToggle.syncState();

        mDrawerController.attachTo(this, mViewGroupDrawer);

        setController(mAnalyzeController);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mToggle != null) {
            mDrawerLayout.removeDrawerListener(mToggle);
        }

        for (Controller controller : mControllerList) {
            controller.detach();
        }
        mControllerList.clear();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
        }
    }

    public void setController(@NonNull Controller currentController) {
        boolean controllerFound = false;
        for (Controller controller : mControllerList) {
            if (currentController == controller) {
                controller.show();
                controllerFound = true;
            } else {
                controller.hide();
            }
        }
        if (!controllerFound) {
            currentController.attachTo(this, mViewGroupContent);
            mControllerList.add(currentController);
        }
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawers();
    }

    public ActivityComponent getActivityComponent() {
        return mActivityComponent;
    }

}

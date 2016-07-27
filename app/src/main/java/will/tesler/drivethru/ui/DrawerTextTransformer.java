package will.tesler.drivethru.ui;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.subjects.PublishSubject;
import will.tesler.drivethru.R;
import will.tesler.drivethru.activities.MainActivity;
import will.tesler.drivethru.navigation.models.DrawerTextItem;

public class DrawerTextTransformer extends UniversalAdapter.Transformer<DrawerTextItem> {

    @Bind(R.id.imageview_icon) ImageView mImageViewIcon;
    @Bind(R.id.textview_title) TextView mTextViewTitle;

    public DrawerTextTransformer(ViewGroup parent) {
        super(R.layout.row_drawer_text, parent);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void transform(final DrawerTextItem drawerTextItem,
                             final PublishSubject<Pair<DrawerTextItem, String>> universalSubject) {
        mImageViewIcon.setImageResource(drawerTextItem.iconResource);
        mTextViewTitle.setText(drawerTextItem.title);

        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                universalSubject.onNext(new Pair<>(drawerTextItem, "Click"));
                MainActivity activity = (MainActivity) getContext();
                activity.setController(drawerTextItem.controller);
                activity.closeDrawer();
            }
        });
    }
}

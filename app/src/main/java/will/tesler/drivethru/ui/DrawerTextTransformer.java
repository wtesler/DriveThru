package will.tesler.drivethru.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
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
    protected void transform(final DrawerTextItem drawerTextItem) {
        mImageViewIcon.setImageResource(drawerTextItem.iconResource);
        mTextViewTitle.setText(drawerTextItem.title);

        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getContext();
                activity.setController(drawerTextItem.controller);
                activity.closeDrawer();
            }
        });
    }
}

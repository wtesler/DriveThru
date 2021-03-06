package will.tesler.drivethru.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import will.tesler.drivethru.R;
import will.tesler.drivethru.adapter.UniversalAdapter;
import will.tesler.drivethru.adapter.UniversalSubject;
import will.tesler.drivethru.navigation.models.DrawerTextItem;

public class DrawerTextTransformer extends UniversalAdapter.Transformer<DrawerTextItem> {

    public static final String ACTION_CLICK = "DrawerTextTransformer.onClick";

    @Bind(R.id.imageview_icon) ImageView mImageViewIcon;
    @Bind(R.id.textview_title) TextView mTextViewTitle;

    public DrawerTextTransformer(ViewGroup parent) {
        super(R.layout.row_drawer_text, parent);
        ButterKnife.bind(this, getView());
    }

    @Override
    protected void transform(final DrawerTextItem drawerTextItem, final UniversalSubject universalSubject) {
        mImageViewIcon.setImageResource(drawerTextItem.iconResource);
        mTextViewTitle.setText(drawerTextItem.title);

        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                universalSubject.onNext(drawerTextItem, ACTION_CLICK);
            }
        });
    }
}

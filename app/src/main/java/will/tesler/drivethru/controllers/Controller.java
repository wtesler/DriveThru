package will.tesler.drivethru.controllers;

import android.app.Activity;
import android.view.ViewGroup;

public interface Controller {

    void attachTo(Activity activity, ViewGroup parent);

    void detach();

    void show();

    void hide();
}

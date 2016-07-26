package will.tesler.drivethru.controllers;

import android.app.Activity;
import android.view.ViewGroup;

import will.tesler.drivethru.activities.MainActivity;

public interface Controller {

    void attachTo(MainActivity activity, ViewGroup parent);

    void detach();

    void show();

    void hide();
}

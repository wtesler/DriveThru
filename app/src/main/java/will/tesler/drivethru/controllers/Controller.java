package will.tesler.drivethru.controllers;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import will.tesler.drivethru.activities.MainActivity;

public abstract class Controller {

    private View mView;

    public abstract void attachTo(MainActivity activity, ViewGroup parent);

    public abstract void detach();

    public void show() {
        mView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mView.setVisibility(View.GONE);
    }

    protected void inflate(@LayoutRes int layoutRes, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false);
        setView(view, parent);
    }

    protected View getView() {
        return mView;
    }

    protected void setView(@NonNull View view, ViewGroup parent) {
        mView = view;
        parent.addView(mView);
    }
}

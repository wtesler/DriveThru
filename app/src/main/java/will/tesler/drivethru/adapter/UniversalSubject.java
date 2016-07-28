package will.tesler.drivethru.adapter;

import android.util.Pair;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class UniversalSubject {

    private final PublishSubject<Pair<Object, String>> mSubject = PublishSubject.create();

    public void onNext(Object object, String action) {
        mSubject.onNext(Pair.create(object, action));
    }

    public Observable<Pair<Object, String>> filter(Func1<Pair<Object, String>, Boolean> predicate) {
        return mSubject.filter(predicate);
    }
}

package will.tesler.drivethru.adapter;

import android.support.annotation.NonNull;
import android.util.Pair;

import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

public class UniversalSubject {

    private final PublishSubject<Pair<Object, String>> mSubject = PublishSubject.create();

    public void onNext(@NonNull Object object, @NonNull String action) {
        mSubject.onNext(Pair.create(object, action));
    }

    public Observable<Pair<Object, String>> filter(@NonNull Func1<Pair<Object, String>, Boolean> predicate) {
        return mSubject.filter(predicate);
    }
}

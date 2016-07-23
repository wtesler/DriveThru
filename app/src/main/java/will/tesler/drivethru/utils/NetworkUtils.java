package will.tesler.drivethru.utils;

import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;

import retrofit.HttpException;

public class NetworkUtils {

    private NetworkUtils() { }

    public static void logThrowable(@Nullable Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            try {
                Log.e("Network", httpException.response().errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Network", throwable.toString());
        }
    }
}

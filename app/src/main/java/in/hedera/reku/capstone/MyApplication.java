package in.hedera.reku.capstone;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by reku on 22/12/16.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}

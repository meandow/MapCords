package omab.mapcords;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Meandow on 9/23/2017.
 */

public class ApplicationProvider extends Application {

    private static ApplicationProvider instance;

    public ApplicationProvider() {
        super();
    }

    public static ApplicationProvider getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static SharedPreferences getPreferences() {
        Application app = getInstance();
        return app.getSharedPreferences(app.getPackageName(), Context.MODE_PRIVATE);
    }
}

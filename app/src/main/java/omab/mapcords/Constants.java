package omab.mapcords;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by Meandow on 9/23/2017.
 */

public class Constants {
    @Retention(SOURCE)
    @IntDef({NavigationMode.NAVIGATION_MODE_WGS84, NavigationMode.NAVIGATION_MODE_SWEREF99, NavigationMode.NAVIGATION_MODE_RT90})
    public @interface NavigationMode {
        int NAVIGATION_MODE_WGS84 = 0;
        int NAVIGATION_MODE_SWEREF99 = 1;
        int NAVIGATION_MODE_RT90 = 2;
    }

}

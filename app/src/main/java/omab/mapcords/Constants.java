package omab.mapcords;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Meandow on 9/23/2017.
 */

public class Constants {
    @IntDef({NAVIGATION_MODE_WGS84, NAVIGATION_MODE_SWEREF99, NAVIGATION_MODE_RT90})
    public   @interface NavigationMode {}
    private static final int NAVIGATION_MODE_WGS84 = 0;
    private static final int NAVIGATION_MODE_SWEREF99 = 1;
    private static final int NAVIGATION_MODE_RT90 = 2;
}

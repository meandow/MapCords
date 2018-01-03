package omab.mapcords;

import android.content.SharedPreferences;

import com.google.android.gms.maps.GoogleMap;

/**
 * Created by Meandow on 9/23/2017.
 */

public class PrefHelp {

    public static final String COORDINATES_NAVIGATION_TYPE_PREF = "CordPrefTypePreference";
    public static final String MAP_TYPE_PREF = "CordPrefTypePreference";

    public static void setCoordinateSystemPreference(SharedPreferences.Editor editor, int mode) {
        editor.putInt(COORDINATES_NAVIGATION_TYPE_PREF, mode);
        editor.commit();
    }

    public static int getCoordinateSystemPreference(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(COORDINATES_NAVIGATION_TYPE_PREF, Constants.NavigationMode.NAVIGATION_MODE_SWEREF99);
    }

    public static void setMapSystemPreference(SharedPreferences.Editor editor, int mode) {
        editor.putInt(MAP_TYPE_PREF, mode);
        editor.commit();
    }

    public static int getMapSystemPreference(SharedPreferences sharedPreferences) {
        return sharedPreferences.getInt(MAP_TYPE_PREF, GoogleMap.MAP_TYPE_NORMAL);
    }
}

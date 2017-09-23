package omab.mapcords;

import android.content.SharedPreferences;

/**
 * Created by Meandow on 9/23/2017.
 */

public class PrefHelp {

    public static final String COORDINATES_NAVIGATION_TYPE_PREF = "CordPrefTypePreference";

    public void setCoordinateSystemPreference(@Constants.NavigationMode int mode) {
        SharedPreferences.Editor editor = ApplicationProvider.getPreferences().edit();
        editor.putInt(COORDINATES_NAVIGATION_TYPE_PREF, mode);
        editor.apply();
    }

    public int getCoordinateSystemPreference() {
        SharedPreferences sharedPreferences = ApplicationProvider.getPreferences();
        return sharedPreferences.getInt(COORDINATES_NAVIGATION_TYPE_PREF, 0);
    }
}

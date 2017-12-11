package omab.mapcords;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class HideServiceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stopService(new Intent(this, GpsCoordinatesOverlay.class));
        finish();
    }
}

package omab.mapcords;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import omab.mapcords.positions.SWEREF99Position;
import omab.mapcords.positions.WGS84Position;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String SET_POSITION_DIALOG_FRAGMENT_KEY = "DialogFragmentKey";

    private Button addPin;
    private GoogleMap mMap;
    private boolean mapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addPin = (Button) findViewById(R.id.add);
        addPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
    }

    public void openDialog() {
        DialogFragment fragment = new DialogFragment();
        FragmentManager fm = getSupportFragmentManager();
        fragment.show(fm, SET_POSITION_DIALOG_FRAGMENT_KEY);
    }

    public void setPin() {
        if (mapReady) {
            //Todo: add set pin;

        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        mMap = googleMap;
        SWEREF99Position sweref99Position = new SWEREF99Position(6366436, 318624);
        WGS84Position wgs84Position = sweref99Position.toWGS84();
        LatLng sydney = new LatLng(wgs84Position.latitude, wgs84Position.longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


}

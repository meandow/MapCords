package omab.mapcords;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import omab.mapcords.fragments.DrawerFragment;
import omab.mapcords.fragments.PositionDialog;
import omab.mapcords.positions.PositionHelper;
import omab.mapcords.positions.SWEREF99Position;
import omab.mapcords.positions.WGS84Position;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

import static android.R.attr.permission;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String SET_POSITION_DIALOG_FRAGMENT_KEY = "DialogFragmentKey";
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 111;
    public final static int REQUEST_CODE = 7686;
    private FloatingActionButton addPin;
    private GoogleMap mMap;
    private boolean mapReady = false;
    private TextView sweref_lat, sweref_long, wgs84_lat, wgs84_long;
    private boolean locationPermission = false;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        sweref_lat = (TextView) findViewById(R.id.swe_ref_cords_lat);
        sweref_long = (TextView) findViewById(R.id.swe_ref_cords_long);
        wgs84_lat = (TextView) findViewById(R.id.wgs84_cords_lat);
        wgs84_long = (TextView) findViewById(R.id.wgs84_cords_long);
        addPin = (FloatingActionButton) findViewById(R.id.add_pin);
        addPin.setOnClickListener((View v) -> {
//            openDialog();
            initiateService();
        });
        //initializeLocationManager();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, // Activity
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        } else {
            locationPermission = true;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        DrawerFragment drawerFragment = new DrawerFragment();
        ft.add(R.id.drawer_container, drawerFragment);
    }

    public void openDialog() {
        PositionDialog positionDialog = new PositionDialog();
        positionDialog.setPositionValueSaveListener((String name, double firstValue, double secondValue) -> {
            setPin(name, firstValue, secondValue);
        });
        positionDialog.show(getSupportFragmentManager(), SET_POSITION_DIALOG_FRAGMENT_KEY);
    }

    public void setPin(String name, double firstValue, double secondValue) {
        WGS84Position position;
        if (mapReady) {
            switch (PrefHelp.getCoordinateSystemPreference()) {
                case Constants.NAVIGATION_MODE_SWEREF99:
                    position = PositionHelper.getWgsFromSweRef(firstValue, secondValue);
                    break;
                case Constants.NAVIGATION_MODE_RT90:
                    position = PositionHelper.getWgsFromRt90(firstValue, secondValue);
                    break;
                default:
                    position = new WGS84Position(firstValue, secondValue);
                    break;
            }
            LatLng pin = new LatLng(position.latitude, position.longitude);
            mMap.addMarker(new MarkerOptions().position(pin).title(name));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(pin));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribeForLocations();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, GpsCoordinatesOverlay.class));
    }

    public void subscribeForLocations() {
        LocationRequest request = LocationRequest.create() //standard GMS LocationRequest
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setNumUpdates(5)
                .setInterval(100);
        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(getApplicationContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapReady = true;
        mMap = googleMap;
        if (locationPermission) {
            try {
                mMap.setMyLocationEnabled(true);
            } catch (SecurityException e) {
                showAlert();
            }
        }
    }

    public void updateLocationText(Location location) {
        WGS84Position wgs84Position = new WGS84Position(location.getLatitude(), location.getLongitude());
        SWEREF99Position sweref99Position = new SWEREF99Position(wgs84Position, SWEREF99Position.SWEREFProjection.sweref_99_12_00);

        sweref_lat.setText(String.format(" %.0f", sweref99Position.getLatitude()));
        sweref_long.setText(String.format(" %.0f", sweref99Position.getLongitude()));
        wgs84_lat.setText(String.format(" %f°", location.getLatitude()));
        wgs84_long.setText(String.format(" %f°", location.getLongitude()));
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private void initializeLocationManager() {
        if (locationManager == null) {
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermission = true;
                } else {
                    locationPermission = false;
                }
                return;
            }
        }
    }

    public void initiateService() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE);
        } else {
            startService();
        }
        Log.i("hesv", "initiateService");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("hesv", "activityResult w: " + requestCode);
        if (requestCode == REQUEST_CODE) {
            if (Settings.canDrawOverlays(this)) {
                startService();
            }
        }
    }

    private void startService() {
        Intent intent = new Intent(this, HideServiceActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification noti = new Notification.Builder(this)
                .setTicker("Koordinater")
                .setContentTitle(getString(R.string.shut_off_coordinate_overlay))
                .setSmallIcon(R.drawable.close)
                .setContentIntent(pIntent).getNotification();
        noti.flags= Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);

        Intent serviceIntent = new Intent();
        serviceIntent.setComponent(new ComponentName("omab.mapcords", "omab.mapcords.GpsCoordinatesOverlay"));
        startService(serviceIntent);

    }
}

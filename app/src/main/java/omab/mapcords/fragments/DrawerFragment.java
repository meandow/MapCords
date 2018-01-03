package omab.mapcords.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.maps.GoogleMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import omab.mapcords.Constants;
import omab.mapcords.PrefHelp;
import omab.mapcords.R;

/**
 * Created by Meandow on 9/23/2017.
 */

public class DrawerFragment extends Fragment{

    private RadioButton sweRef, wgs84;
    private RadioButton standard, terrain, hybrid, satellite;

    private SharedPreferences sharedPreferences;
    public DrawerFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.drawer_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        RadioGroup mapGroup = (RadioGroup) view.findViewById(R.id.map_type_group);
        RadioGroup coordGroup = (RadioGroup) view.findViewById(R.id.coordinates_type_group);

        sweRef = (RadioButton) view.findViewById(R.id.swe_ref_type);
        wgs84 = (RadioButton) view.findViewById(R.id.wgs84_type);
        standard = (RadioButton) view.findViewById(R.id.standard);
        terrain = (RadioButton) view.findViewById(R.id.terrain);
        hybrid = (RadioButton) view.findViewById(R.id.hybrid);
        satellite = (RadioButton) view.findViewById(R.id.satellite);
        updateSelections();
        coordGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.swe_ref_type:
                        PrefHelp.setCoordinateSystemPreference(editor, Constants.NavigationMode.NAVIGATION_MODE_SWEREF99);
                        break;
                    case R.id.wgs84_type:
                        PrefHelp.setCoordinateSystemPreference(editor, Constants.NavigationMode.NAVIGATION_MODE_WGS84);
                        break;
                }
            }
        });

        mapGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                switch (checkedId) {
                    case R.id.standard:
                        PrefHelp.setMapSystemPreference(editor, GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case R.id.terrain:
                        PrefHelp.setMapSystemPreference(editor, GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    case R.id.hybrid:
                        PrefHelp.setMapSystemPreference(editor, GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case R.id.satellite:
                        PrefHelp.setMapSystemPreference(editor, GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void updateSelections() {
        sweRef.setChecked(PrefHelp.getCoordinateSystemPreference(sharedPreferences) == Constants.NavigationMode.NAVIGATION_MODE_SWEREF99);
        wgs84.setChecked(PrefHelp.getCoordinateSystemPreference(sharedPreferences) == Constants.NavigationMode.NAVIGATION_MODE_WGS84);
        standard.setChecked(PrefHelp.getMapSystemPreference(sharedPreferences) == GoogleMap.MAP_TYPE_NORMAL);
        hybrid.setChecked(PrefHelp.getMapSystemPreference(sharedPreferences) == GoogleMap.MAP_TYPE_HYBRID);
        terrain.setChecked(PrefHelp.getMapSystemPreference(sharedPreferences) == GoogleMap.MAP_TYPE_TERRAIN);
        satellite.setChecked(PrefHelp.getMapSystemPreference(sharedPreferences) == GoogleMap.MAP_TYPE_SATELLITE);
    }
}

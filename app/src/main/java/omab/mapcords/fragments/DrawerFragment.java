package omab.mapcords.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import omab.mapcords.R;

/**
 * Created by Meandow on 9/23/2017.
 */

public class DrawerFragment extends Fragment{

    public DrawerFragment(){
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//        super.onActivtyCreated(savedInstanceState);
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.drawer_fragment, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }
}

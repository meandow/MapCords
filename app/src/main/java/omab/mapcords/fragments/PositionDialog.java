package omab.mapcords.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import omab.mapcords.R;
import rx.Subscription;


public class PositionDialog extends DialogFragment {

    protected String mTitle;
    protected String mHint;
    protected String mSecondTitle;
    protected String mSecondHint;

    protected double firstValue;
    protected double secondValue;

    private Subscription subscribe;
    private PositionValueSaveListener mPositionValueSaveListener;

    private Button saveButton, cancelButton;
    public PositionDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


    }

    public interface PositionValueSaveListener{
        void onPositionSave(String name, double firstValue, double secondValue);
    }

    public void setPositionValueSaveListener(PositionValueSaveListener onPositionValueSave){
        mPositionValueSaveListener = onPositionValueSave;
    }
}

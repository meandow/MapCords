package omab.mapcords.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.reactivestreams.Subscription;

import omab.mapcords.ApplicationProvider;
import omab.mapcords.R;



public class PositionDialog extends DialogFragment {

    protected TextView mTitle;
    protected TextView mHint;
    protected TextView mSecondTitle;
    protected TextView mSecondHint;

    protected EditText firstValueEdit;
    protected EditText secondValueEdit;
    protected double firstValue;
    protected double secondValue;

    protected Button save, cancel;
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
        firstValueEdit = (EditText) view.findViewById(R.id.firstValue);
        secondValueEdit = (EditText) view.findViewById(R.id.secondValue);
        save = (Button) view.findViewById(R.id.save);
        cancel = (Button) view.findViewById(R.id.cancel);

        firstValueEdit.requestFocus();
        InputMethodManager imm = (InputMethodManager) ApplicationProvider.getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public interface PositionValueSaveListener{
        void onPositionSave(String name, double firstValue, double secondValue);
    }

    public void setPositionValueSaveListener(PositionValueSaveListener onPositionValueSave){
        mPositionValueSaveListener = onPositionValueSave;
    }
}

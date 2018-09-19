package kr.ac.shinhan.travelpartner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.List;

public class ContentTypeSpinnerAdapter extends ArrayAdapter<String>{

    public ContentTypeSpinnerAdapter(@NonNull Context context, int resource, @NonNull String[] objects) {
        super(context, resource, objects);
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}

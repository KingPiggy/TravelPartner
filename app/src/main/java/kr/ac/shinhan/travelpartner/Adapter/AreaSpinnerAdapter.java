package kr.ac.shinhan.travelpartner.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class AreaSpinnerAdapter extends ArrayAdapter<String>{
    ArrayList<String> guNameList;
    public AreaSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.guNameList = objects;
    }

}

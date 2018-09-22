package kr.ac.shinhan.travelpartner.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.shinhan.travelpartner.R;


public class BlankFragment extends Fragment {
    View view;

    public BlankFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_blank, container, false);

        return view;
    }
}
package kr.ac.shinhan.travelpartner.bottombar;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.shinhan.travelpartner.R;

public class SettionFragment extends Fragment {

    View view;

    public SettionFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_place, container, false);

        return view;
    }
}

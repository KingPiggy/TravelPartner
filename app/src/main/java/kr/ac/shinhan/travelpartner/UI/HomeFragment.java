package kr.ac.shinhan.travelpartner.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.shinhan.travelpartner.Adapter.VpAdapter;
import kr.ac.shinhan.travelpartner.R;

public class HomeFragment extends Fragment {
    com.android.woong.viewpagerindicator.custom.CircleAnimIndicator circleAnimIndicator;
    ViewPager noticeViewPager;
    View view;
    VpAdapter vpAdapter;
    public HomeFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        noticeViewPager = (ViewPager)view.findViewById(R.id.viewPager_main_notice);
        circleAnimIndicator = (com.android.woong.viewpagerindicator.custom.CircleAnimIndicator)view.findViewById(R.id.indicator);

        vpAdapter = new VpAdapter(this.getActivity());
        noticeViewPager.setAdapter(vpAdapter);

        CircularViewPagerHandler circularViewPagerHandler = new CircularViewPagerHandler(noticeViewPager, circleAnimIndicator);
        noticeViewPager.addOnPageChangeListener(circularViewPagerHandler);
        circleAnimIndicator.createDotPanel(vpAdapter.getCount(), R.drawable.indicator_non , R.drawable.indicator_on);

        return view;
    }
}

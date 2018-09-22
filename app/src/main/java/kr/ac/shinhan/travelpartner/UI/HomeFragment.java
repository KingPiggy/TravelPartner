package kr.ac.shinhan.travelpartner.UI;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.shinhan.travelpartner.Adapter.VpAdapter;
import kr.ac.shinhan.travelpartner.R;
import kr.ac.shinhan.travelpartner.bottombar.ViewPagerAdapter;

public class HomeFragment extends Fragment {
    com.android.woong.viewpagerindicator.custom.CircleAnimIndicator circleAnimIndicator;
    ViewPager noticeViewPager;
    View view;

    public HomeFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        circleAnimIndicator = (com.android.woong.viewpagerindicator.custom.CircleAnimIndicator)view.findViewById(R.id.indicator);
//        noticeViewPager = (ViewPager)view.findViewById(R.id.viewPager_main_notice);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        final VpAdapter vpAdapter = new VpAdapter();
//        CircularViewPagerHandler circularViewPagerHandler = new CircularViewPagerHandler(noticeViewPager, circleAnimIndicator);
//        noticeViewPager.setAdapter(vpAdapter);
//        noticeViewPager.addOnPageChangeListener(circularViewPagerHandler);
//        circleAnimIndicator.createDotPanel(vpAdapter.getCount(), R.drawable.indicator_non , R.drawable.indicator_on);
    }
}

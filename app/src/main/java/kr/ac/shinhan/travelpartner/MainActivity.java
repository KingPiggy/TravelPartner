package kr.ac.shinhan.travelpartner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;

import com.google.android.gms.maps.MapFragment;

import kr.ac.shinhan.travelpartner.bottombar.BlankFragment;
import kr.ac.shinhan.travelpartner.bottombar.BottomNavigationViewHelper;
import kr.ac.shinhan.travelpartner.bottombar.HomeFragment;
import kr.ac.shinhan.travelpartner.bottombar.MypageFragment;
import kr.ac.shinhan.travelpartner.bottombar.SettionFragment;
import kr.ac.shinhan.travelpartner.bottombar.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {
    public static final String PREFNAME = "Preferences";
    public static final int USERSETTINGS = 10000;
    public static final int PERMISSION_INTERNET = 100;
    public static final int PERMISSON_ACCESS_FINE_LOCATION = 200;
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment;
    MapFragment mapFragment;
    MypageFragment mypageFragment;
    SettionFragment SettionFragment;

    private ViewPager mainViewPager;
    ViewPagerAdapter adapter;

    int currentMenu;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission();
        isFirstTime();

        mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        mainViewPager.setOffscreenPageLimit(5);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);


        currentMenu = R.id.action_home;
        setupViewPager(mainViewPager);
        prevMenuItem = bottomNavigationView.getMenu().getItem(0);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        currentMenu = R.id.action_home;
                        mainViewPager.setCurrentItem(0);
                        break;
                    case R.id.action_Map:
                        currentMenu = R.id.action_Map;
                        mainViewPager.setCurrentItem(1);
                        break;
                    case R.id.action_Mypage:
                        currentMenu = R.id.action_Mypage;
                        mainViewPager.setCurrentItem(2);
                        break;
                    case R.id.action_settings:
                        currentMenu = R.id.action_Mypage;
                        mainViewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });
        mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                currentMenu = bottomNavigationView.getMenu().getItem(position).getItemId();
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void permission() {
        //checkSelfPermission으로 권한 확인, 권한 승인은 PERMISSION_GRANTED, 거절은 PERMISSION_DENIED
        // 인터넷 권한 확인
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
                // 이전에 거부 하였을 경우 권한 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSION_INTERNET);
            } else {
                // 최초 권한 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, PERMISSION_INTERNET);
            }
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                // 이전에 거부 하였을 경우 권한 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSON_ACCESS_FINE_LOCATION);
            } else {
                // 최초 권한 요청
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSON_ACCESS_FINE_LOCATION);
            }
        }

    }

    public void isFirstTime() {
        SharedPreferences settings = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if (settings.getBoolean("isFirstTime", true)) {
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), UserPrefActivity.class);
            startActivityForResult(intent, USERSETTINGS);
        } else {
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case USERSETTINGS:
                if (resultCode == Activity.RESULT_OK) {

                }
                break;
        }

    }

    public void move(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_main_place:
                intent = new Intent(getApplicationContext(), PlaceActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_main_info:
                intent = new Intent(getApplicationContext(), PlaceInfoActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getFragmentManager());

        homeFragment = new HomeFragment();
        mapFragment = new MapFragment();
        mypageFragment = new MypageFragment();
        SettionFragment = new SettionFragment();
        


        adapter.addFragment(homeFragment);
        adapter.addFragment( mapFragment);
        adapter.addFragment(mypageFragment);
        adapter.addFragment(SettionFragment);

        viewPager.setAdapter(adapter);
    }
}

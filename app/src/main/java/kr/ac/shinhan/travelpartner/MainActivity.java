package kr.ac.shinhan.travelpartner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import kr.ac.shinhan.travelpartner.Firebase.GoogleSignInActivity;
import kr.ac.shinhan.travelpartner.UI.BottomBar.BottomNavigationViewHelper;
import kr.ac.shinhan.travelpartner.Adapter.MenuFragmentAdapter;
import kr.ac.shinhan.travelpartner.UI.FavoriteFragment;
import kr.ac.shinhan.travelpartner.UI.HomeFragment;
import kr.ac.shinhan.travelpartner.UI.MyPageFragment;
import kr.ac.shinhan.travelpartner.UI.PlaceFragment;


public class MainActivity extends AppCompatActivity {
    public static final String PREFNAME = "Preferences";
    public static final int USERSETTINGS = 10000;
    public static final int PERMISSION_INTERNET = 100;
    public static final int PERMISSON_ACCESS_FINE_LOCATION = 200;
    BottomNavigationView bottomNavigationView;
    private String contentId, image, contentTypeId, uiContentType, title, tel, addr;
    private double lat, lon;

    HomeFragment homeFragment;
    PlaceFragment placeFragment;
    FavoriteFragment favoriteFragment;
    MyPageFragment myPageFragment;

    private ViewPager mainViewPager;
    MenuFragmentAdapter adapter;

    int currentMenu;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission();
        isFirstTime();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FAD956"));
        }

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
                    case R.id.action_place:
                        currentMenu = R.id.action_place;
                        mainViewPager.setCurrentItem(1);
                        break;
                    case R.id.action_favorites:
                        currentMenu = R.id.action_favorites;
                        mainViewPager.setCurrentItem(2);
                        break;
                    case R.id.action_my_page:
                        currentMenu = R.id.action_my_page;
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
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSON_ACCESS_FINE_LOCATION);
            } else {
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

            Intent intent = new Intent(getApplicationContext(), GoogleSignInActivity.class);
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

    public void setupViewPager(ViewPager viewPager) {
        adapter = new MenuFragmentAdapter(getFragmentManager());

        homeFragment = new HomeFragment();
        placeFragment = new PlaceFragment();
        favoriteFragment = new FavoriteFragment();
        myPageFragment = new MyPageFragment();

        adapter.addFragment(homeFragment);
        adapter.addFragment(placeFragment);
        adapter.addFragment(favoriteFragment);
        adapter.addFragment(myPageFragment);

        viewPager.setAdapter(adapter);
    }

    public void onClick(View v){
        Intent intent;
        switch (v.getId()){
            case R.id.menu2_btn1:
                contentTypeId ="12";
                uiContentType = "#관광지";
                lat= 126.9769930325;
                lon = 37.5788222356;
                contentId = "126508";
                image ="http://tong.visitkorea.or.kr/cms/resource/40/1568040_image2_1.jpg";
                title ="경복궁";
                tel ="02-3700-3900";
                addr ="서울특별시 종로구 사직로 161";

                intent = new Intent(getApplicationContext(), PlaceInfoActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                intent.putExtra("contentid", contentId);
                intent.putExtra("contentTypeId", contentTypeId);
                intent.putExtra("uiContentTypeId", uiContentType);
                intent.putExtra("image", image);
                intent.putExtra("title", title);
                intent.putExtra("tel", tel);
                intent.putExtra("addr", addr);
                startActivity(intent);
                break;

            case R.id.menu2_btn2:
                contentTypeId ="12";
                uiContentType = "#관광지";
                lat= 126.9876206116;
                lon = 37.5516394747;
                contentId = "126535";
                image ="http://tong.visitkorea.or.kr/cms/resource/30/2477030_image2_1.jpg";
                title ="남산서울타워";
                tel ="02-3455-9277";
                addr ="서울특별시 용산구 남산공원길 105";

                intent = new Intent(getApplicationContext(), PlaceInfoActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                intent.putExtra("contentid", contentId);
                intent.putExtra("contentTypeId", contentTypeId);
                intent.putExtra("uiContentTypeId", uiContentType);
                intent.putExtra("image", image);
                intent.putExtra("title", title);
                intent.putExtra("tel", tel);
                intent.putExtra("addr", addr);
                startActivity(intent);
                break;

            case R.id.menu2_btn3:
                contentTypeId ="12";
                uiContentType = "#관광지";
                lat= 126.9940059289;
                lon =  37.5591248302;
                contentId = "126747";
                image =" http://tong.visitkorea.or.kr/cms/resource/62/1946562_image2_1.jpg";
                title ="남산골한옥마을";
                tel ="02-2261-0517";
                addr ="서울특별시 중구 퇴계로34길 28";

                intent = new Intent(getApplicationContext(), PlaceInfoActivity.class);
                intent.putExtra("latitude", lat);
                intent.putExtra("longitude", lon);
                intent.putExtra("contentid", contentId);
                intent.putExtra("contentTypeId", contentTypeId);
                intent.putExtra("uiContentTypeId", uiContentType);
                intent.putExtra("image", image);
                intent.putExtra("title", title);
                intent.putExtra("tel", tel);
                intent.putExtra("addr", addr);
                startActivity(intent);
                break;

        }

    }
}

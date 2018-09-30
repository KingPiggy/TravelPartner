package kr.ac.shinhan.travelpartner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import kr.ac.shinhan.travelpartner.Firebase.GoogleSignInActivity;
import kr.ac.shinhan.travelpartner.UI.BottomBar.BottomNavigationViewHelper;
import kr.ac.shinhan.travelpartner.Adapter.MenuFragmentAdapter;
import kr.ac.shinhan.travelpartner.UI.HomeFragment;
import kr.ac.shinhan.travelpartner.UI.MyPageFragment;
import kr.ac.shinhan.travelpartner.UI.PlaceFragment;


public class MainActivity extends AppCompatActivity {
    public static final String PREFNAME = "Preferences";
    public static final int LOG_IN = 10000;
    BottomNavigationView bottomNavigationView;
    private String contentId, image, contentTypeId, uiContentType, title, tel, addr;
    private double lat, lon;
    private BackPressHandler backPressCloseHandler;

    HomeFragment homeFragment;
    PlaceFragment placeFragment;
    MyPageFragment myPageFragment;

    private ViewPager mainViewPager;
    MenuFragmentAdapter adapter;

    int currentMenu;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        backPressCloseHandler = new BackPressHandler(this);

        isFirstTime();

        TedPermission.with(getApplicationContext())
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("권한이 허가되지 않으면\n일부 서비스가 제공되지 않습니다.")
                .setPermissions(Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

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
    PermissionListener permissionlistener = new PermissionListener() {
        @Override
        public void onPermissionGranted() {
        }
        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
            Toast.makeText(MainActivity.this, "권한이 거부되었습니다.\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }
    };

    public void isFirstTime() {
        SharedPreferences settings = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if (settings.getBoolean("isFirstTime", true)) {
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), GoogleSignInActivity.class);
            startActivityForResult(intent, LOG_IN);

        } else {
            //한 번 실행되어 false라면 그 액티비티로 안넘어감
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        GoogleSignInActivity remoteActivity = (GoogleSignInActivity)GoogleSignInActivity.cloneActivity;
        remoteActivity.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOG_IN:
                if (resultCode == Activity.RESULT_OK) {
                    Log.d("hoon", "메인으로 돌아왔니" + resultCode);
                    GoogleSignInActivity remoteActivity = (GoogleSignInActivity)GoogleSignInActivity.cloneActivity;
                    remoteActivity.finish();
                }
                break;
        }
    }
    public void setupViewPager(ViewPager viewPager) {
        adapter = new MenuFragmentAdapter(getFragmentManager());

        homeFragment = new HomeFragment();
        placeFragment = new PlaceFragment();
        myPageFragment = new MyPageFragment();

        adapter.addFragment(homeFragment);
        adapter.addFragment(placeFragment);
        adapter.addFragment(myPageFragment);

        viewPager.setAdapter(adapter);
    }

    public void recommendedPlace(View v){
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

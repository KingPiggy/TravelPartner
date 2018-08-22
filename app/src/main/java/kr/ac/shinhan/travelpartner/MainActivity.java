package kr.ac.shinhan.travelpartner;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;


public class MainActivity extends AppCompatActivity {
    public static final String PREFNAME = "Preferences";
    public static final int USERSETTINGS = 10000;
    public static final int PERMISSION_INTERNET = 100;
    public static final int PERMISSON_ACCESS_FINE_LOCATION = 200;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permission();
        isFirstTime();


        tabHost = (TabHost) findViewById(R.id.tapHost);

        tabHost.setup();


//        tabHost = getTabHost();


//        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Map2");
//        tabSpec.setIndicator("Map");
//        Context ctx = this.getApplicationContext();
//        Intent i = new Intent(ctx, MapsActivity.class);
//        tabSpec.setContent(i);
//        tabHost.addTab(tabSpec);
//        tabHost.addTab(tabHost.newTabSpec("Map2").setIndicator("Map").setContent(R.id.tab2));
//        tabHost.setCurrentTab(0);
//

        tabHost.addTab(tabHost.newTabSpec("Home").setContent(R.id.tab1).setIndicator("홈타이틀"));

        tabHost.addTab(tabHost.newTabSpec("Travel")
                .setIndicator("여행지")
                .setContent(new TabHost.TabContentFactory() {
                    @Override
                    public View createTabContent(String tag) {
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        View view = View.inflate(MainActivity.this, R.layout.activity_maps, null);
                        return view;
                    }
                }));
        tabHost.addTab(tabHost.newTabSpec("MyPage")
                .setIndicator("마이페이지")
                .setContent(R.id.tab3));

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
            case R.id.btn_main_map:
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
                break;
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
}

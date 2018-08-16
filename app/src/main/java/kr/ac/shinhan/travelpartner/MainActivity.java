package kr.ac.shinhan.travelpartner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

        isFirstTime();

        tabHost = (TabHost)findViewById(R.id.tapHost);

        tabHost.setup();


//        tabHost = getTabHost();


//        TabHost.TabSpec tabSpec = tabHost.newTabSpec("tab_test1");
//        tabSpec.setIndicator("Map");
//        Context ctx = this.getApplicationContext();
//        Intent i = new Intent(ctx, MapsActivity.class);
//        tabSpec.setContent(i);
//        tabHost.addTab(tabSpec);
//        tabHost.addTab(tabHost.newTabSpec("Map2").setIndicator("맵").setContent(R.id.extext2));
//        tabHost.setCurrentTab(0);


        tabHost.addTab(tabHost.newTabSpec("Home").setContent(R.id.tab1).setIndicator("홈타이틀"));

        Intent intent;
        intent = new Intent().setClass(this,MapsActivity.class);
        tabHost.addTab(tabHost.newTabSpec("Travel")
                .setIndicator("여행지")
                .setContent(intent));


        tabHost.addTab(tabHost.newTabSpec("MyPage")
                .setIndicator("마이페이지")
                .setContent(R.id.tab3));

    }

    public void isFirstTime(){
        SharedPreferences settings = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        if(settings.getBoolean("isFirstTime", true)){
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            Intent intent = new Intent(getApplicationContext(), UserPrefActivity.class);
            startActivityForResult(intent, USERSETTINGS);
        }
        else{
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case USERSETTINGS:
                if(resultCode == Activity.RESULT_OK){

                }
                break;
        }

    }

//    public void move(View v){
//        switch (v.getId()) {
//            case R.id.btn_main_map:
//                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
//                startActivity(intent);
//        }
//    }
}

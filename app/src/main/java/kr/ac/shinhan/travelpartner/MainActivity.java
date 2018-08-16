package kr.ac.shinhan.travelpartner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    public static final String PREFNAME = "Preferences";
    public static final int USERSETTINGS = 10000;
    public static final int PERMISSION_INTERNET = 100;
    public static final int PERMISSON_ACCESS_FINE_LOCATION = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isFirstTime();

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

    public void move(View v){
        switch (v.getId()) {
            case R.id.btn_main_map:
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
        }
    }
}

package kr.ac.shinhan.travelpartner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import static kr.ac.shinhan.travelpartner.MainActivity.PREFNAME;

public class UserPrefActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pref);

        SharedPreferences settings = getSharedPreferences(PREFNAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();


        editor.putString("usertype", "비장애인");
        editor.apply();
    }

    public void finishPref(View v){
        switch (v.getId()){
            case R.id.btn_temp2:
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
    }
}

package kr.ac.shinhan.travelpartner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PlaceInfoActivity extends AppCompatActivity {
    private TextView mResultTv;
    private final String myKey = "OxP7Yce7jnNVFihdyT%2FAp4pC%2FPwXwQoQiKrQr%2Bc8kOaQs%2FXjPH8m2MzB6s3CzMAHh8HnJ2Cjw93kiYRmCPgZkA%3D%3D";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);


    }

    public String parsingXml(){

        return "ss";
    }
}

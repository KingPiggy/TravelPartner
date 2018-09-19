package kr.ac.shinhan.travelpartner;

import android.app.Application;
import com.tsengvn.typekit.Typekit;


public class Font extends Application {

    public void onCreate(){
        super.onCreate();

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this,"fonts/yano.ttf"))
                .addBold(Typekit.createFromAsset(this,"fonts/yano.ttf"));
    }
}
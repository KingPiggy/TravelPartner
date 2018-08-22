package kr.ac.shinhan.travelpartner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static kr.ac.shinhan.travelpartner.PlaceActivity.*;

public class PlaceInfoActivity extends AppCompatActivity {
    public final String SERVICE_URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorWithService/";
    public final String SERVICE_COMMON = "detailCommon?";
    public final String OS = "AND";
    public final String APPNAME = "TravelPartner";
    public final String KEY = "OxP7Yce7jnNVFihdyT%2FAp4pC%2FPwXwQoQiKrQr%2Bc8kOaQs%2FXjPH8m2MzB6s3CzMAHh8HnJ2Cjw93kiYRmCPgZkA%3D%3D";
    private TextView mHomepage, mModifiedTime, mTel, mTitle, mAddr, mOverview;
    private ImageView image;
    private String tag, contentId;
    private String homepage, modifiedtime, tel, title, addr1, overview, firstimage;
    private Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);

        //인텐트로 콘텐트 아이디 받아올 것
        contentId = "252581";

        mHomepage = (TextView)findViewById(R.id.tv_info_homepage);
        mModifiedTime = (TextView)findViewById(R.id.tv_info_modifiedtime);
        mTel = (TextView)findViewById(R.id.tv_info_tel);
        mTitle = (TextView)findViewById(R.id.tv_info_title);
        mAddr = (TextView)findViewById(R.id.tv_info_addr);
        mOverview = (TextView)findViewById(R.id.tv_info_overview);
        image = (ImageView)findViewById(R.id.iv_info_image);

        init();
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                setDetailCommon();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHomepage.setText(homepage);
                        mModifiedTime.setText(modifiedtime);
                        mTel.setText(tel);
                        mTitle.setText(title);
                        mAddr.setText(addr1);
                        mOverview.setText(overview);
                        setImage();
                    }
                });
            }
        }).start();
    }

    public void setDetailCommon(){
        try {
            URL url = new URL(SERVICE_URL + SERVICE_COMMON + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
            "&contentId="+ contentId + "&defaultYN=Y" + "&firstImageYN=Y" + "&addrinfoYN=Y" + "&overviewYN=Y" );
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), "UTF-8");
            int parserEvent = parser.getEventType();
            while(parserEvent != XmlPullParser.END_DOCUMENT){
                switch (parserEvent){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if(tag.equals("homepage")){
                            parser.next();
                            homepage = parser.getText();
                        }
                        else if(tag.equals("modifiedtime")){
                            parser.next();
                            modifiedtime = parser.getText();
                        }
                        else if(tag.equals("tel")){
                            parser.next();
                            tel = parser.getText();
                        }
                        else if(tag.equals("title")){
                            parser.next();
                            title = parser.getText();
                        }
                        else if(tag.equals("addr1")){
                            parser.next();
                            addr1 = parser.getText();
                        }
                        else if(tag.equals("overview")){
                            parser.next();
                            overview = parser.getText();
                        }
                        else if(tag.equals("firstimage")){
                            parser.next();
                            firstimage = parser.getText();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;

                }
                parserEvent = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setImage(){
        Thread mThread = new Thread(){
            public void run(){
                try {
                    URL imageUrl = new URL(firstimage);
                    HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    }

                  catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        mThread.start();
        try{
            mThread.join();
            image.setImageBitmap(bitmap);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}

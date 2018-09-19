package kr.ac.shinhan.travelpartner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.APPNAME;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.KEY;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.OS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_DETAIL_COMMON;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_DETAIL_INTRO;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_URL;

public class PlaceInfoActivity extends AppCompatActivity {
    // 서비스 : 공통정보 조회, 소개정보 조회, 반복정보 조회, 이미지정보 조회(컨텐츠ID에 따라 상세이미지들), 무장애정보 조회
    // 반복정보 조회 : 콘텐츠ID, 관광타입 ID 필수
    // 이미지정보 조회 : 콘텐츠ID 조회
    // 무장애정보 조회 : 콘텐츠ID 조회
    private TextView mHomepage, mModifiedTime, mTel, mTitle, mAddr, mOverview, mContentTypeId;
    private ImageView image;
    private String tag, contentId, tempStr;
    private String homepage, modifiedtime, tel, title, addr1, overview, firstimage, contentTypeId;
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
        mContentTypeId = (TextView)findViewById(R.id.tv_info_contenttypeid);

        image = (ImageView)findViewById(R.id.iv_info_image);

        init();
    }

    public void init(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                setDetailCommon();
                setDetailIntro();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mHomepage.setText(homepage);
                        mModifiedTime.setText("최종 수정일 : " + modifiedtime);
                        mTel.setText(tel);
                        mTitle.setText(title);
                        mAddr.setText(addr1);
                        mOverview.setText(overview);
                        mContentTypeId.setText(contentTypeId);
                        setImage();
                    }
                });
            }
        }).start();
    }

    public void setDetailCommon(){
        try {
            URL url = new URL(SERVICE_URL + SERVICE_DETAIL_COMMON + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
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
                            tempStr = parser.getText().substring(0,7);
                            modifiedtime = tempStr;
                        }
                        else if(tag.equals("tel")){
                            parser.next();
                            tel = parser.getText();
                        }
                        else if(tag.equals("contenttypeid")){
                            parser.next();
                            contentTypeId = parser.getText();
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
    public void setDetailIntro(){
        try {
            URL url = new URL(SERVICE_URL + SERVICE_DETAIL_INTRO + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
                    "&contentId="+ contentId + "&contentTypeId=" + contentTypeId + "&introYN=Y");
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), "UTF-8");
            int parserEvent = parser.getEventType();
            while(parserEvent != XmlPullParser.END_DOCUMENT){
                switch (parserEvent){
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        if(tag.equals("homepage")){
                            parser.next();
                            homepage = parser.getText();
                        }
                        else if(tag.equals("modifiedtime")) {
                            parser.next();
                            tempStr = parser.getText().substring(0, 7);
                            modifiedtime = tempStr;
                        }
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

                  catch (Exception e) {
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

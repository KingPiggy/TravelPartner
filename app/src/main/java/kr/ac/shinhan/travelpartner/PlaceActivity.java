package kr.ac.shinhan.travelpartner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity{
    public final String SERVICE_URL = "http://api.visitkorea.or.kr/openapi/service/rest/KorWithService/";
    public final String SERVICE_AREACODE = "areaCode?";
    public final String OS = "AND";
    public final String APPNAME = "TravelPartner";
    public final String KEY = "OxP7Yce7jnNVFihdyT%2FAp4pC%2FPwXwQoQiKrQr%2Bc8kOaQs%2FXjPH8m2MzB6s3CzMAHh8HnJ2Cjw93kiYRmCPgZkA%3D%3D";
    private Spinner mSpinner, mSpinner2;
    private ArrayList<String> mCodeList;
    private ArrayList<String> mNameList;
    private ArrayList<String> mName2List;
    private ArrayAdapter<String> spinAdapter;
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        new Thread(new Runnable() {
            @Override
            public void run() {
                viewAreaCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        initSpinner();
    }
    private void initSpinner(){
        mCodeList = new ArrayList<String>();
        mNameList = new ArrayList<String>();

        mSpinner = (Spinner)findViewById(R.id.spinner_place_1);
        spinAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mNameList);
        spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(spinAdapter);
        mSpinner.setOnItemSelectedListener(myListener);
    }
    private void viewAreaCode(){
        try {
            URL url = new URL(SERVICE_URL + SERVICE_AREACODE + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME);
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(url.openStream(), "UTF-8");
            int parserEvent = parser.getEventType();
            //parser.next();
            while(parserEvent != XmlPullParser.END_DOCUMENT){
                switch (parserEvent){
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        tag = parser.getName();
                        //지역코드1~10 그 1~10 마다 시군구코드1~10이 있다
                        if(tag.equals("code")){
                            parser.next();
                            mCodeList.add(parser.getText());
                        }
                        //지역명 또는 시군구명
                        else if(tag.equals("name")){
                            parser.next();
                            mNameList.add(parser.getText());
                        }
                        //일련번호
                        else if(tag.equals("rnum")){
                            parser.next();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;
                    case XmlPullParser.END_TAG:
                        break;

                }
                parserEvent = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
    AdapterView.OnItemSelectedListener myListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
}

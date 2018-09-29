package kr.ac.shinhan.travelpartner;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import kr.ac.shinhan.travelpartner.Adapter.DetailRecyclerAdapter;
import kr.ac.shinhan.travelpartner.Item.DetailWithTourItem;

import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.APPNAME;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.KEY;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.OS;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_DETAIL_WITH_TOUR;
import static kr.ac.shinhan.travelpartner.XMLparsing.ServiceDefinition.SERVICE_URL;

public class DetailInfoActivity extends AppCompatActivity {
    private DetailWithTourItem detailWithTourItem = new DetailWithTourItem();
    private TextView mTitle;
    private Button mCloseBtn;
    private String contentId, title;
    private RecyclerView mRecyclerView;
    private DetailRecyclerAdapter detailRecyclerAdapter;
    private ArrayList<DetailWithTourItem> firstItems = new ArrayList<DetailWithTourItem>();
    LinearLayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_info);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FAD956"));
        }

        Intent intent = getIntent();
        contentId = intent.getStringExtra("contentId");
        title = intent.getStringExtra("title");

        initUI();
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_detail);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());
        firstItems.add(new DetailWithTourItem());



        detailRecyclerAdapter = new DetailRecyclerAdapter(getApplicationContext(), firstItems, R.layout.activity_detail_info);

        mRecyclerView.setAdapter(detailRecyclerAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        //new DetailWithTourParsing().execute(contentId);
    }

    public void initUI() {
        mTitle = (TextView) findViewById(R.id.tv_detail_title);
        mCloseBtn = (Button)findViewById(R.id.btn_detail_close);
        mTitle.setText(title);
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class DetailWithTourParsing extends AsyncTask<String, String, DetailWithTourItem> {
        @Override
        protected DetailWithTourItem doInBackground(String... strings) {
            DetailWithTourItem detailWithTourItem = null;
            try {
                String contentId = strings[0];

                URL detailIntroUrl = new URL(SERVICE_URL + SERVICE_DETAIL_WITH_TOUR + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
                        "&contentId=" + contentId);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

                parser.setInput(detailIntroUrl.openStream(), "UTF-8");
                int parserEvent = parser.getEventType();

                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            String tag = parser.getName();
                            if (tag.contains("item")) {
                                detailWithTourItem = new DetailWithTourItem();
                            } else if (tag.equals("chkbabycarriage")) {
                                parser.next();
                                //chkbabycarriage = parser.getText();
                                //placeInfoItem.setChkbabycarriage(chkbabycarriage);
                            } else if (tag.equals("chkbabycarriage")) {
                                parser.next();
                                //chkbabycarriage = parser.getText();
                                //placeInfoItem.setChkbabycarriage(chkbabycarriage);
                            } else if (tag.equals("chkbabycarriage")) {
                                parser.next();
                                //chkbabycarriage = parser.getText();
                                //placeInfoItem.setChkbabycarriage(chkbabycarriage);
                            } else if (tag.equals("chkbabycarriage")) {
                                parser.next();
                                //chkbabycarriage = parser.getText();
                                //placeInfoItem.setChkbabycarriage(chkbabycarriage);
                            } else if (tag.equals("chkbabycarriage")) {
                                parser.next();
                                //chkbabycarriage = parser.getText();
                                //placeInfoItem.setChkbabycarriage(chkbabycarriage);
                            } else if (tag.equals("chkbabycarriage")) {
                                parser.next();
                                //chkbabycarriage = parser.getText();
                                //placeInfoItem.setChkbabycarriage(chkbabycarriage);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return detailWithTourItem;
        }

        @Override
        protected void onPostExecute(DetailWithTourItem detailWithTourItem) {
            super.onPostExecute(detailWithTourItem);

        }

    }
}

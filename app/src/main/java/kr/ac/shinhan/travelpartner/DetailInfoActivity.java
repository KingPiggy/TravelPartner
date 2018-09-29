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
    private String title;
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
        String contentId = intent.getStringExtra("contentId");
        title = intent.getStringExtra("title");

        initUI();
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_detail);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        detailRecyclerAdapter = new DetailRecyclerAdapter(getApplicationContext(), firstItems, R.layout.activity_detail_info);

        mRecyclerView.setAdapter(detailRecyclerAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        new DetailWithTourParsing().execute(contentId);
    }

    public void initUI() {
        mTitle = (TextView) findViewById(R.id.tv_detail_title);
        mCloseBtn = (Button) findViewById(R.id.btn_detail_close);
        mTitle.setText(title);
        mCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class DetailWithTourParsing extends AsyncTask<String, String, ArrayList<DetailWithTourItem>> {
        @Override
        protected ArrayList<DetailWithTourItem> doInBackground(String... strings) {
            ArrayList<DetailWithTourItem> newItems = new ArrayList<DetailWithTourItem>();
            try {
                String contentId = strings[0];

                URL detailIntroUrl = new URL(SERVICE_URL + SERVICE_DETAIL_WITH_TOUR + "ServiceKey=" + KEY + "&MobileOS=" + OS + "&MobileApp=" + APPNAME +
                        "&contentId=" + contentId);
                XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
                XmlPullParser parser = parserCreator.newPullParser();

                parser.setInput(detailIntroUrl.openStream(), "UTF-8");
                int parserEvent = parser.getEventType();

                DetailWithTourItem detailWithTourItem = null;
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            String tag = parser.getName();
                            String infoContents = "";
                            String infoTitle = "";
                            if (tag.equals("parking")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "장애인 주차 구역";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("publictransport")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "접근로";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("wheelchair")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "휠체어";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);

                            } else if (tag.equals("exit")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "출입 통로";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("elevator")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "엘리베이터";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("restroom")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "화장실";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("braileblock")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "점자블록";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("helpdog")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "보조견 동반";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("audioguide")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "오디오 가이드";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("signguide")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "수화안내";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("stroller")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "유모차";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            } else if (tag.equals("lactationroom")) {
                                parser.next();
                                detailWithTourItem = new DetailWithTourItem();
                                infoTitle = "수유실";
                                infoContents = parser.getText();
                                detailWithTourItem.setTitle(infoTitle);
                                detailWithTourItem.setContents(infoContents);
                                newItems.add(detailWithTourItem);
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException | IOException e) {
                e.printStackTrace();
            }
            return newItems;
        }

        @Override
        protected void onPostExecute(ArrayList<DetailWithTourItem> newItems) {
            super.onPostExecute(newItems);
            firstItems.clear();
            firstItems.addAll(newItems);
            detailRecyclerAdapter.notifyDataSetChanged();
        }

    }
}

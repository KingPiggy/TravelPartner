package kr.ac.shinhan.travelpartner;

import android.Manifest;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import kr.ac.shinhan.travelpartner.R;

public class MapActivity extends AppCompatActivity implements MapView.POIItemEventListener {

    MapView mMapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.map_view);
        mMapView.setDaumMapApiKey("e172e319329f44bfe3ec8c7cfa6b2273");
//        ViewGroup mapGroup = (ViewGroup) findViewById(R.id.map_view);
//        mapGroup.addView(mMapView);
//        mMapView.setMapViewEventListener((MapView.MapViewEventListener) this); // this에 MapView.MapViewEventListener 구현.
        mMapView.setPOIItemEventListener(this);
       // mMapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.3358, 126.5840), true);

        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(37.5536067, 126.9696195);
        mMapView.setMapCenterPoint(mapPoint, true);

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("서울역");
        marker.setTag(0);
        marker.setMapPoint(mapPoint);
        // 기본으로 제공하는 BluePin 마커 모양.
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
        mMapView.addPOIItem(marker);


    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {

    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}

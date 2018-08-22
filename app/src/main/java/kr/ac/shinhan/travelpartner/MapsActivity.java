package kr.ac.shinhan.travelpartner;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;



public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;          //화면이동, 마커달기 등등으로 쓰이는 구글맵 변수.
    private CameraPosition mCameraPosition;    //1번 게시물에서 사용했었던, 좌표로이동 + 3d 효과까지 줄 수 있는 변수

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;     // 구글 Places API에 접근해서 지역정보를 얻는 변수(안쓰임)
    private PlaceDetectionClient mPlaceDetectionClient; //구글 Places API에 접근해서 <현재 위치>를 얻는 변수

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;  // 구글 Places API에 접근해서, <융합된 주위정보>를 얻는 변수

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(35.050538, 126.720457); //인터넷 연결안됬을 때, 연결된 default 시작장소의 좌표값 <수정해서 사용>
    private static final int DEFAULT_ZOOM = 15;                // 줌의 정도 상수
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1; // 위치정보사용에 대한 동의 상수로, requestCode랑 비교해서 같으면 ok
    private boolean mLocationPermissionGranted;                // 불린형으로, 위치정보사용 허가에 대한 유/무

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;      // mFusedLocationProviderClient에 의해 Places API에 연결되어 현재위치의 주위Location정보를 담는 Location변수

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    // 액티비티가 잠시 중지되어서 다시 켜질 때를 위해, 값을 저장할라고 호출되는 onSaveInstanceState()에 사용되는 키값으로, 파라미터로 넘어오는 번들객체에다가
    // 번들의 밸류인 현재좌표, 현재위치정보를 담을 때 사용될 번들의 키값 상수들이다.


    // Used for selecting the current place.
    private static final int M_MAX_ENTRIES = 5;    // [메뉴]버튼을 눌렀을 때, 현재위치에서 좋아할만한 장소를 띄울 <최대>갯수로서, 아래 있는 각 장소에 대한 이름/주소/속성/좌표의 최대 갯수가 되기도 한다. 만약 내가 좋아할만한 장소가 int count랑 비교해서 5개가 안되면 count 만큼 띄워지도록 비교하는 문장이 있다.
    private String[] mLikelyPlaceNames;
    private String[] mLikelyPlaceAddresses;
    private String[] mLikelyPlaceAttributions;
    private LatLng[] mLikelyPlaceLatLngs; // 좋아할만한 장소에 대한 값들

    public GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_maps);

        getLocationPermission();
        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);
        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);
        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    //액티비티가 중지되었을 때, 맵 만 살아 있으면, 맵변수에서 좌표정보/위치 주변정보를 번들객체에 담아둔다. -> onCreate()호출시 xml화면 보여주기 전에 이 값들을 회수할 것이다.
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap; //공통변수를 mMap으로 선언해 받아쓴다.

        googleMap.getUiSettings().setZoomControlsEnabled(true);  //+,- 표시

        //응용1. mMap위의 마커클릭시 행동을 나타낼 수 있는 마커클릭리스너가 있다.
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(marker.getPosition())      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                marker.showInfoWindow(); //클릭리스너를  달면, 클릭->정보창 안떴는데, 클릭리스너안에 showInfoWindow()를 호출하면 정보창도 같이 뜬다!!

                return true;
            }
        });


        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // 8. 핸드폰의 현재위치를 맵의 포지션에 지정하는 매쏘드
        getDeviceLocation();


/*        final LatLng hoban_bustop = new LatLng(35.050607, 126.722983); //마커를 달 위치를 담는다.
        googleMap.addMarker(new MarkerOptions().position(hoban_bustop).title("호반아파트 정류장")); //구글맵에 마커를 다는데, 옵션으로서, LatLng에 담긴 위치와 타이틀을 넣어준다.

        LatLng center_bustop = new LatLng(35.048169, 126.720031); //처음 맵을 시작시킬 위치 담기
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center_bustop, 15)); //구글맵의 위치를 로딩이 완료된 시점에서,  맵의 보이는 위치를 옮겨 놓는다. 좌표와 함께, 줌의 정도도 같이준다.

        //3d효과를 위한 버튼
        Button button   = (Button) findViewById(R.id.button_3d);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //3d효과
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(hoban_bustop)      // Sets the center of the map to Mountain View
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(30)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }
        });*/
    }


    private void getLocationPermission() { // 퍼미션을 요청하는 매쏘드
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    //11. 업데이트 ui 매쏘드를 만들어준다. onMapReady로 넘어오는 googlemap변수를 썼었는데, 여기서도 공통으로 쓰기위해, 전역변수로 선언해주고, onMapReady에서도 파라미터를 전역변수에 받아주어 사용한다.

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @SuppressLint("MissingPermission")
    private void getDeviceLocation() { //15 디바이스 위치를 얻는 함수 추가. //17. 안에 LocationServices 이놈이 import 안된다... gradle compile 'com.google.android.gms:play-services-location:11.8.0' 추가해줄 것이다. maps랑 같은 버전으로 이후 rebuild

        /*
         * Before getting the device location, you must check location
         * permission, as described earlier in the tutorial. Then:
         * Get the best and most recent location of the device, which may be
         * null in rare cases when a location is not available.
         */

        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {


                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));


                        } else {
                            Log.d("", "Current location is null. Using defaults.");
                            Log.e("", "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void showCurrentPlace() {
        if (mMap == null) {
            return;
        }

//        if (mLocationPermissionGranted) {
//
//
//            //필터 넣으려해봤지만 안됨.
///*            ArrayList<String> filters = new ArrayList<>();
//            filters.add(Place.TYPE_ATM + "");
//            filters.add(Place.TYPE_BANK + "");
//            filters.add(Place.TYPE_BUS_STATION + "");
//            filters.add("restaurant");
//            filters.add("establishment");
//            filters.add(Place.TYPE_STORE + "");
//
//            PlaceFilter placeFilter = new PlaceFilter(false, filters);*/
//
//
//            // Get the likely places - that is, the businesses and other points of interest that
//            // are the best match for the device's current location.
//            @SuppressWarnings("MissingPermission") final
//            Task<PlaceLikelihoodBufferResponse> placeResult = mPlaceDetectionClient.getCurrentPlace(null);
//            placeResult.addOnCompleteListener
//                    (new OnCompleteListener<PlaceLikelihoodBufferResponse>() {
//                        @Override
//                        public void onComplete(@NonNull Task<PlaceLikelihoodBufferResponse> task) {
//                            if (task.isSuccessful() && task.getResult() != null) {
//                                PlaceLikelihoodBufferResponse likelyPlaces = task.getResult();
//
//                                // Set the count, handling cases where less than 5 entries are returned.
//                                int count;
//                                if (likelyPlaces.getcounter() <= M_MAX_ENTRIES) {
//                                    count = likelyPlaces.getCount();
//                                } else {
//                                    count = M_MAX_ENTRIES;
//                                }
//
//                                int i = 0;
//                                mLikelyPlaceNames = new String[count];
//                                mLikelyPlaceAddresses = new String[count];
//                                mLikelyPlaceAttributions = new String[count];
//                                mLikelyPlaceLatLngs = new LatLng[count];
//
//                                for (PlaceLikelihood placeLikelihood : likelyPlaces) {
//                                    // Build a list of likely places to show the user.
//                                    mLikelyPlaceNames[i] = (String) placeLikelihood.getPlace().getName();
//                                    mLikelyPlaceAddresses[i] = (String) placeLikelihood.getPlace()
//                                            .getAddress();
//                                    mLikelyPlaceAttributions[i] = (String) placeLikelihood.getPlace()
//                                            .getAttributions();
//                                    mLikelyPlaceLatLngs[i] = placeLikelihood.getPlace().getLatLng();
//
//                                    i++;
//                                    if (i > (count - 1)) {
//                                        break;
//                                    }
//                                }
//
//                                // Release the place likelihood buffer, to avoid memory leaks.
//                                likelyPlaces.release();
//
//                                // Show a dialog offering the user the list of likely places, and add a
//                                // marker at the selected place.
//                                openPlacesDialog();
//
//                            } else {
//                            }
//                        }
//                    });
//        } else {
//            // The user has not granted permission.
//
//            // Add a default marker, because the user hasn't selected a place.
//            mMap.addMarker(new MarkerOptions()
//                    .title(getString(R.string.default_info_title))
//                    .position(mDefaultLocation)
//                    .snippet(getString(R.string.default_info_snippet)));
//
//            // Prompt the user for permission.
//            getLocationPermission();
//        }
//    }

    }
}
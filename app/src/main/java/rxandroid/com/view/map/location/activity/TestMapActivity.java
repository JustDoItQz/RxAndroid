package rxandroid.com.view.map.location.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import rxandroid.com.R;
import rxandroid.com.view.map.baseline.activity.BusStationActivity;
import rxandroid.com.view.map.baseline.activity.BuslineActivity;
import rxandroid.com.view.map.geo.activity.GeocoderActivity;
import rxandroid.com.view.map.geo.activity.ReGeocoderActivity;
import rxandroid.com.view.map.overlay.activity.CircleActivity;
import rxandroid.com.view.map.overlay.activity.GroundOverlayActivity;
import rxandroid.com.view.map.overlay.activity.MarkerClickActivity;
import rxandroid.com.view.map.overlay.activity.PolygonActivity;
import rxandroid.com.view.map.overlay.activity.PolylineActivity;
import rxandroid.com.view.map.poisearch.activity.PoiAroundSearchActivity;
import rxandroid.com.view.map.poisearch.activity.PoiKeywordSearchActivity;
import rxandroid.com.view.map.route.activity.BusRouteActivity;
import rxandroid.com.view.map.route.activity.DriveRouteActivity;
import rxandroid.com.view.map.route.activity.WalkRouteActivity;

/**
 * Created on 2017/7/13.
 * Author by Aaron.Wang
 */

public class TestMapActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_test);

        initView();

    }
    private void initView(){
        findViewById(R.id.busline).setOnClickListener(this);
        findViewById(R.id.busstation).setOnClickListener(this);
        findViewById(R.id.geocoder).setOnClickListener(this);
        findViewById(R.id.regeocoder).setOnClickListener(this);
        findViewById(R.id.customlocation).setOnClickListener(this);
        findViewById(R.id.district).setOnClickListener(this);
        findViewById(R.id.districtwithboundary).setOnClickListener(this);
        findViewById(R.id.locationmarker).setOnClickListener(this);
        findViewById(R.id.circle).setOnClickListener(this);
        findViewById(R.id.groundmarker).setOnClickListener(this);
        findViewById(R.id.markerclick).setOnClickListener(this);
        findViewById(R.id.polygon).setOnClickListener(this);
        findViewById(R.id.polyline).setOnClickListener(this);
        findViewById(R.id.poiaroundsearch).setOnClickListener(this);
        findViewById(R.id.poikeywordsearch).setOnClickListener(this);
        findViewById(R.id.busroute).setOnClickListener(this);
        findViewById(R.id.driverroute).setOnClickListener(this);
        findViewById(R.id.walkroute).setOnClickListener(this);


    }
    Intent intent = null ;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.busline:
                intent = new Intent(TestMapActivity.this, BuslineActivity.class) ;
                break;
            case R.id.busstation:
                intent = new Intent(TestMapActivity.this, BusStationActivity.class) ;
                break;
            case R.id.geocoder:
                intent = new Intent(TestMapActivity.this, GeocoderActivity.class) ;
                break;
            case R.id.regeocoder:
                intent = new Intent(TestMapActivity.this, ReGeocoderActivity.class) ;
                break;
            case R.id.customlocation:
                intent = new Intent(TestMapActivity.this, CustomLocationActivity.class) ;
                break;
            case R.id.district:
                intent = new Intent(TestMapActivity.this, DistrictActivity.class) ;
                break;
            case R.id.districtwithboundary:
                intent = new Intent(TestMapActivity.this, DistrictWithBoundaryActivity.class) ;
                break;
            case R.id.locationmarker:
                intent = new Intent(TestMapActivity.this, LocationMarkerActivity.class) ;
                break;
            case R.id.circle:
                intent = new Intent(TestMapActivity.this, CircleActivity.class) ;
                break;
            case R.id.groundmarker:
                intent = new Intent(TestMapActivity.this, GroundOverlayActivity.class) ;
                break;
            case R.id.markerclick:
                intent = new Intent(TestMapActivity.this, MarkerClickActivity.class) ;
                break;
            case R.id.polygon:
                intent = new Intent(TestMapActivity.this, PolygonActivity.class) ;
                break;
            case R.id.polyline:
                intent = new Intent(TestMapActivity.this, PolylineActivity.class) ;
                break;
            case R.id.poiaroundsearch:
                intent = new Intent(TestMapActivity.this, PoiAroundSearchActivity.class) ;
                break;
            case R.id.poikeywordsearch:
                intent = new Intent(TestMapActivity.this, PoiKeywordSearchActivity.class) ;
                break;
            case R.id.busroute:
                intent = new Intent(TestMapActivity.this, BusRouteActivity.class) ;
                break;
            case R.id.driverroute:
                intent = new Intent(TestMapActivity.this, DriveRouteActivity.class) ;
                break;
            case R.id.walkroute:
                intent = new Intent(TestMapActivity.this, WalkRouteActivity.class) ;
                break;
        }

        startActivity(intent);

    }
}

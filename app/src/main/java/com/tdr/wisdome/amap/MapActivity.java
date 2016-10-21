package com.tdr.wisdome.amap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.AddOlderActivity;

/**
 * Created by Linus_Xie on 2016/8/18.
 */
public class MapActivity extends Activity implements AMap.OnCameraChangeListener,
        AMap.OnMapLoadedListener, OnLocationGetListener, View.OnClickListener,
        RouteTask.OnRouteCalculateListener {

    private MapView mMapView;

    private AMap mAmap;

    private TextView mAddressTextView;

    private Marker mPositionMark;

    private LatLng mStartPosition;

    private RegeocodeTask mRegeocodeTask;

    private LinearLayout mDestinationContainer;

    private TextView mRouteCostText;

    private TextView mDesitinationText;

    private LocationTask mLocationTask;

    private ImageView mLocationImage;

    private LinearLayout mFromToContainer;

    private boolean mIsFirst = true;

    private boolean mIsRouteSuccess = false;

    private String address = "";
    private double lat = 0.0;//纬度
    private double lng = 0.0;//经度

    public interface OnGetLocationListener {
        public void getLocation(String locationAddress);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init(savedInstanceState);
        mLocationTask = LocationTask.getInstance(getApplicationContext());
        mLocationTask.setOnLocationGetListener(this);
        mRegeocodeTask = new RegeocodeTask(getApplicationContext());
        RouteTask.getInstance(getApplicationContext())
                .addRouteCalculateListener(this);
    }

    private ImageView image_back;
    private TextView text_title;
    private TextView text_deal;

    private void init(Bundle savedInstanceState) {

        image_back = (ImageView) findViewById(R.id.image_back);
        image_back.setOnClickListener(this);
        text_title = (TextView) findViewById(R.id.text_title);
        text_title.setText("活动中心");
        text_deal = (TextView) findViewById(R.id.text_deal);
        text_deal.setOnClickListener(this);
        text_deal.setVisibility(View.VISIBLE);
        text_deal.setText("完成");

        mAddressTextView = (TextView) findViewById(R.id.address_text);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mAmap = mMapView.getMap();
        mAmap.getUiSettings().setZoomControlsEnabled(false);
        mAmap.setOnMapLoadedListener(this);
        mAmap.setOnCameraChangeListener(this);

        mDestinationContainer = (LinearLayout) findViewById(R.id.destination_container);
        mRouteCostText = (TextView) findViewById(R.id.routecost_text);
        mDesitinationText = (TextView) findViewById(R.id.destination_text);
        mDesitinationText.setOnClickListener(this);
        mLocationImage = (ImageView) findViewById(R.id.location_image);
        mLocationImage.setOnClickListener(this);
        mFromToContainer = (LinearLayout) findViewById(R.id.fromto_container);

    }

    private void hideView() {
        mFromToContainer.setVisibility(View.GONE);
    }

    private void showView() {
        mFromToContainer.setVisibility(View.VISIBLE);
        if (mIsRouteSuccess) {
        }
    }

    @Override
    public void onCameraChange(CameraPosition arg0) {
        hideView();
    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        showView();
        mStartPosition = cameraPosition.target;
        mRegeocodeTask.setOnLocationGetListener(this);
        mRegeocodeTask
                .search(mStartPosition.latitude, mStartPosition.longitude);
        if (mIsFirst) {
            if (mPositionMark != null) {
                mPositionMark.setToTop();
            }
            mIsFirst = false;
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        mLocationTask.onDestroy();
    }

    @Override
    public void onMapLoaded() {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.setFlat(true);
        markerOptions.anchor(0.5f, 0.5f);
        markerOptions.position(new LatLng(0, 0));
        markerOptions
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(),
                                R.mipmap.icon_loaction_start)));
        mPositionMark = mAmap.addMarker(markerOptions);

        mPositionMark.setPositionByPixels(mMapView.getWidth() / 2,
                mMapView.getHeight() / 2);
        mLocationTask.startSingleLocate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.image_back:
                finish();
                break;

            case R.id.text_deal:
                pointBack();
                break;

            case R.id.location_image:
                mLocationTask.startSingleLocate();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            pointBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void pointBack() {
        Intent intent = new Intent();
        intent.setClass(MapActivity.this, AddOlderActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("address", address);
        bundle.putDouble("lat",lat);
        bundle.putDouble("lng",lng);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onLocationGet(PositionEntity entity) {
        // todo 这里在网络定位时可以减少一个逆地理编码
        address = entity.address;
        lat = entity.latitue;
        lng = entity.longitude;
        mAddressTextView.setText(address);
        RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);

        mStartPosition = new LatLng(entity.latitue, entity.longitude);
        CameraUpdate cameraUpate = CameraUpdateFactory.newLatLngZoom(
                mStartPosition, mAmap.getCameraPosition().zoom);
        mAmap.animateCamera(cameraUpate);

    }

    @Override
    public void onRegecodeGet(PositionEntity entity) {
        address = entity.address;
        lat = entity.latitue;
        lng = entity.longitude;
        mAddressTextView.setText(address);
        entity.latitue = mStartPosition.latitude;
        entity.longitude = mStartPosition.longitude;
        RouteTask.getInstance(getApplicationContext()).setStartPoint(entity);
        RouteTask.getInstance(getApplicationContext()).search();
    }

    @Override
    public void onRouteCalculate(float cost, float distance, int duration) {
        mDestinationContainer.setVisibility(View.VISIBLE);
        mIsRouteSuccess = true;
        mRouteCostText.setVisibility(View.VISIBLE);
        mDesitinationText.setText(RouteTask
                .getInstance(getApplicationContext()).getEndPoint().address);
        mRouteCostText.setText(String.format("预估费用%.2f元，距离%.1fkm,用时%d分", cost,
                distance, duration));
    }
}

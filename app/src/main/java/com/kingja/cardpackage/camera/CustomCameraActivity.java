package com.kingja.cardpackage.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tdr.wisdome.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.kingja.cardpackage.base.App.context;

/**
 * Created by KingJA on 2016/1/24.
 */
public class CustomCameraActivity extends Activity implements SurfaceHolder.Callback{
    public static final int REQUEST_CODE=1111;
    private Camera mCamera;
    private SurfaceView mPreview;
    private SurfaceHolder mHolder;
    private TextView tv_date;
    private File imageFile;
    private Camera.PictureCallback pictureCallback=new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            imageFile = CameraUtil.createImageFile();
            try {
                FileOutputStream fis = new FileOutputStream(imageFile);
                fis.write(data);
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_camera);
        mPreview = (SurfaceView) findViewById(R.id.preview);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setText(CameraUtil.getDateString());
        mHolder=mPreview.getHolder();
        mHolder.addCallback(this);
        mPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCamera.autoFocus(null);
            }
        });
    }

    public void shutter(View view) {
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.setPictureSize(1280,720);
        parameters.setRotation(90);
        parameters.setFlashMode(Camera.Parameters.FOCUS_MODE_AUTO);
        mCamera.setParameters(parameters);
        mCamera.takePicture(null,null,pictureCallback);
    }

    public void save(View v) {
        Intent intent = new Intent();
        intent.putExtra("PHOTO",imageFile.getAbsolutePath());
        setResult(RESULT_OK,intent);
        finish();
    }

    public Camera getCamera(){
        Camera camera =  Camera.open();
        return camera;
    }
    public void releaseCameraAndPreview(){
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera=null;
        }

    }

    public void setStartPreview(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
            camera.setDisplayOrientation(90);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera==null){
            mCamera=getCamera();
            if (mHolder != null) {
                setStartPreview(mCamera,mHolder);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCameraAndPreview();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        setStartPreview(mCamera,mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        setStartPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCameraAndPreview();
    }

    public static void goCameraActivity(Activity activity) {
        Intent intent = new Intent(activity, CustomCameraActivity.class);
        activity.startActivityForResult(intent,REQUEST_CODE);
    }
}

package com.ghy.katherinejy.criminalintent;

import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

public class CrimeCameraFragment extends Fragment {
    private Camera mCamera;
    private SurfaceView mSurfaceView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceSate){
        View v = inflater.inflate(R.layout.fragment_crime_camera,parent,false);
        Button takePictureButton = (Button)v.findViewById(R.id.crime_camera_takePictureButton);
        takePictureButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mSurfaceView = (SurfaceView) v.findViewById(R.id.crime_camera_surfaceView);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try{
                    if( mCamera != null ){
                        mCamera.setPreviewDisplay(holder);
                    }
                }
                catch (Exception e){

                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if( mCamera==null ) {
                    Camera.Parameters parameters = mCamera.getParameters();
                    Camera.Size s = getBestSupportiveSize(parameters.getSupportedPictureSizes(),width,height);
                    parameters.setPreviewSize(s.width,s.height);
                    mCamera.setParameters(parameters);
                    try {
                        mCamera.startPreview();
                    }
                    catch( Exception e ) {
                        mCamera.release();
                        mCamera = null;
                    }
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if( mCamera!=null ) {
                    mCamera.stopPreview();
                }
            }
        });
        return v;
    }

    @TargetApi(9)
    @Override
    public void onResume(){
        super.onResume();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.GINGERBREAD) {
            mCamera = Camera.open(0);
        } else {
            mCamera = Camera.open();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mCamera!=null){
            mCamera.release();
            mCamera=null;
        }
    }

    private Camera.Size getBestSupportiveSize(List<Camera.Size> sizes, int width, int height){
        Camera.Size bestSize = sizes.get(0);
        int largestArea = bestSize.width*bestSize.height;
        for(Camera.Size size:sizes) {
            int area = size.width*size.height;
            if (area>largestArea) {
                largestArea = area;
                bestSize = size;
            }
        }
        return bestSize;
    }
}

package com.puzzleall.glesbook2;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class AirHockeyActivity extends ActionBarActivity {

    private GLSurfaceView glSurfaceView;
    private boolean renderedSet = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);

        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;

        getSupportActionBar().hide();


        if (supportsEs2) {
            // Request an OpenGL ES 2.0 compatible context.
            glSurfaceView.setEGLContextClientVersion(2);
            // Assign our renderer
            glSurfaceView.setRenderer(new AirHockeyRenderer(this));
            renderedSet = true;
        } else {
            return;
        }

        setContentView(glSurfaceView);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (renderedSet) {
            glSurfaceView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (renderedSet) {
            glSurfaceView.onResume();
        }
    }
}

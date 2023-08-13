package com.abc.qrscannerpro.utils;

import android.hardware.Camera;
import androidx.annotation.NonNull;

public class CameraWrapperUtils {
    public final Camera mCamera;
    public final int mCameraId;

    private CameraWrapperUtils(@NonNull Camera camera, int cameraId) {
        if (camera == null) {
            throw new NullPointerException("Camera cannot be null");
        }
        this.mCamera = camera;
        this.mCameraId = cameraId;
    }

    public static CameraWrapperUtils getWrapper(Camera camera, int cameraId) {
        if (camera == null) {
            return null;
        } else {
            return new CameraWrapperUtils(camera, cameraId);
        }
    }
}

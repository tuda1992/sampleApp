package findlocation.bateam.com.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.cameraview.CameraView;

/**
 * Created by acv on 12/14/17.
 */

public class CameraUtil {

    public static boolean checkCameraFront(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkCameraBack(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkCameraRear() {
        int numCamera = Camera.getNumberOfCameras();
        if (numCamera > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean hasFlash(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public static boolean hasImage(ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }

        return hasImage;
    }


    public static Bitmap squareBitmap(Bitmap resource) {
        int dimension = getSquareCropDimensionForBitmap(resource);
        Bitmap bmpResource = ThumbnailUtils.extractThumbnail(resource, dimension, dimension);
        return bmpResource;
    }


    public static int getSquareCropDimensionForBitmap(Bitmap bitmap) {
        //use the smallest dimension of the image to crop to
        return Math.min(bitmap.getWidth(), bitmap.getHeight());
    }

    public static Bitmap resizeBitmap(byte[] data, CameraView mCameraView) {
        int width;
        int height;
        Matrix matrix = new Matrix();
        Bitmap bitPic = BitmapFactory.decodeByteArray(data, 0, data.length);
        bitPic = ScalingUtil.getScaledDownBitmap(bitPic, 1024, ScalingUtil.ScalingLogic.CROP);
        width = bitPic.getWidth();
        height = bitPic.getHeight();
        // Perform matrix rotations/mirrors depending on camera that took the photo
        if (mCameraView.getFacing() == CameraView.FACING_FRONT) {
            float[] mirrorY = {-1, 0, 0, 0, 1, 0, 0, 0, 1};
            Matrix matrixMirrorY = new Matrix();
            matrixMirrorY.setValues(mirrorY);
            matrix.postConcat(matrixMirrorY);
            Log.d("TUDA", "Build.MANUFACTURER = " + Build.MANUFACTURER);
            if (Build.MANUFACTURER.contains("HTC")) {
                matrix.postRotate(ExifUtil.getOrientation(data) + 180);
            } else {
                matrix.postRotate(ExifUtil.getOrientation(data));
            }
        }
        matrix.postRotate(ExifUtil.getOrientation(data));
        // Create new Bitmap out of the old one
        Bitmap bitPicFinal = Bitmap.createBitmap(bitPic, 0, 0, width, height, matrix, true);
        return bitPicFinal;
    }

}

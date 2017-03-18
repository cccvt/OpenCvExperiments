package tomco.video.camera.facedetection.helpers;

import org.opencv.core.Scalar;

/**
 * Created by tomco on 23/01/2017.
 */
public class Colors {
    //Because for some reason, OpenCV uses BGR instead of the RGB :p
    public final static Scalar RED = new Scalar(0, 0, 255);
    public final static Scalar GREEN = new Scalar(0, 255, 0);
    public final static Scalar BLUE = new Scalar(255, 0, 0);
}

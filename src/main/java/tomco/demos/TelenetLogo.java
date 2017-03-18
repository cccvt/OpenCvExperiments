package tomco.demos;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import static org.opencv.core.Core.minMaxLoc;

/**
 * Created by tomco on 19/01/2017.
 */
public class TelenetLogo {
    public void classify() {
        CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/telenet/telenet-logo-cascade.xml").getPath().substring(1));
        Mat image = Imgcodecs.imread(getClass().getResource("/telenet/telenet.png").getPath().substring(1));

        // Detect faces in the image.
        // MatOfRect is a special container class for Rect.
        MatOfRect telenetDetection = new MatOfRect();
        faceDetector.detectMultiScale(image, telenetDetection);

        System.out.println(String.format("Detected %s telenet logos", telenetDetection.toArray().length));


        Mat template = Imgcodecs.imread(getClass().getResource("/telenet/exactlogo.png").getPath().substring(1));
        Mat actualImage = Imgcodecs.imread(getClass().getResource("/telenet/aanrekening.png").getPath().substring(1));

        Mat result = new Mat();
        Imgproc.matchTemplate(actualImage, template,result,4);
        Core.MinMaxLocResult locResult = minMaxLoc(result);
        System.out.println(locResult.maxLoc + "  " + locResult.maxVal);
        Point maxLoc = locResult.maxLoc;
        Imgproc.rectangle(actualImage, new Point(maxLoc.x, maxLoc.y), new Point(maxLoc.x + 159, maxLoc.y +123), new Scalar(0, 255, 0));
        Imgcodecs.imwrite(System.currentTimeMillis() + "templateMatch.png", actualImage);
    }
}

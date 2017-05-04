package tomco.demos;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import tomco.HelloOpenCV;
import tomco.video.camera.facedetection.helpers.Colors;
import tomco.video.camera.facedetection.helpers.Config;
import tomco.video.camera.facedetection.helpers.UI;
import tomco.video.camera.facedetection.helpers.VoiceTts;
import tomco.video.camera.facedetection.helpers.cognitiveservices.CognitiveServicesFacesNew;
import tomco.video.camera.facedetection.helpers.cognitiveservices.responses.DetectFaces;
import tomco.video.camera.facedetection.helpers.cognitiveservices.responses.IdentifyFaces;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import static org.opencv.videoio.Videoio.CV_CAP_PROP_FRAME_HEIGHT;
import static org.opencv.videoio.Videoio.CV_CAP_PROP_FRAME_WIDTH;
import static tomco.video.camera.facedetection.helpers.MatHelpers.mat2BufferedImage;

/**
 * Created by tomco on 20/01/2017.
 */
public class TrackingColorBased extends HelloOpenCV {
    public static void main(String[] args) {
        try {
            new TrackingColorBased().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private UI ui;
    private VoiceTts voice;

    //setup orange HSV upper and lower levels.
    private static final Scalar LOWER = new Scalar(5, 100, 50);
    private static final Scalar UPPER = new Scalar(25, 255, 255);

    public TrackingColorBased() {
        this.ui = new UI();
        this.voice = new VoiceTts();
    }

    public void run() throws InterruptedException {
        VideoCapture camera = new VideoCapture();

        //SET PARAMS: http://stackoverflow.com/questions/11420748/setting-camera-parameters-in-opencv-python
        camera.open(1);
        Mat frame = new Mat();


        System.out.println(camera.set(CV_CAP_PROP_FRAME_WIDTH,1280)); //video width;
        System.out.println(camera.set(CV_CAP_PROP_FRAME_HEIGHT,720)); //video height;
        System.out.println(camera.get(CV_CAP_PROP_FRAME_WIDTH) + "x" + camera.get(CV_CAP_PROP_FRAME_HEIGHT));
        camera.read(frame);
        System.out.println(frame.width() + "x" + frame.height());

        while (true) {
            if (camera.read(frame)) {
                //Mat resized = new Mat(new Size(2000,2000),1);
                Mat resized = frame;
                //Mat resized = new Mat();
                //Imgproc.resize(frame, resized,new Size(1280,960));
                //Imgproc.pyrUp(frame,resized);

                Mat hsv = new Mat();
                Imgproc.cvtColor(resized,hsv,Imgproc.COLOR_BGR2HSV);

                //Transformations
                Mat mask = new Mat();
                Core.inRange(hsv, LOWER, UPPER, mask);


                int erosionSize = 5;
                int dilationSize = 5;

                Mat structuringElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * erosionSize + 1, 2 * erosionSize + 1));
                Imgproc.erode(mask,mask,structuringElement); //Make it two iterations


                Mat dilationElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(2 * dilationSize + 1, 2 * dilationSize + 1));
                Imgproc.erode(mask,mask,dilationElement);

                List<MatOfPoint> points = new ArrayList<>();
                Imgproc.findContours(mask.clone(),points, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

                points.stream()
                      .forEach(p-> {
                 //       .max((a,b) -> Double.valueOf(b.size().area()).compareTo(Double.valueOf(a.size().area())))
                //.ifPresent(p -> {
                    Rect rect = Imgproc.boundingRect(p);
                    System.out.println(rect);
                    drawRectangle(frame, rect,Colors.GREEN);
                });

                Mat viewSize = new Mat();
                Imgproc.resize(frame, viewSize,new Size(640,400));
                render(viewSize);
            }

        }
    }

    private void drawRectangle(Mat frame, Rect rect, Scalar color) {
        Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), color, 5);
    }

    private void render(Mat frame) {
        BufferedImage bufferedImage = mat2BufferedImage(frame);
        ui.render(bufferedImage);
    }


    private void speak(String s) {
        voice.speak(s);
    }
}

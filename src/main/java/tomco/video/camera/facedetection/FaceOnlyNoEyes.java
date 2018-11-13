package tomco.video.camera.facedetection;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import tomco.HelloOpenCV;
import tomco.video.camera.facedetection.helpers.ClassifierLoader;
import tomco.video.camera.facedetection.helpers.Colors;
import tomco.video.camera.facedetection.helpers.Config;
import tomco.video.camera.facedetection.helpers.UI;
import tomco.video.camera.facedetection.helpers.cognitiveservices.CognitiveServicesFacesNew;
import tomco.video.camera.facedetection.helpers.cognitiveservices.responses.DetectFaces;
import tomco.video.camera.facedetection.helpers.cognitiveservices.responses.IdentifyFaces;

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import static tomco.video.camera.facedetection.helpers.MatHelpers.mat2BufferedImage;

public class FaceOnlyNoEyes extends HelloOpenCV {
    private CognitiveServicesFacesNew cognitiveServices;
    private UI ui;

    public FaceOnlyNoEyes() {
        this.ui = new UI();
        this.cognitiveServices = new CognitiveServicesFacesNew("6ff5ae598540491583a5ae9f11029697", "ap-java-spring-boot-one");
    }

    public static void main(String[] args) {
        new FaceOnlyNoEyes().run();
    }

    public void run() {
        VideoCapture camera = new VideoCapture(0);
        CascadeClassifier faceDetector =
                ClassifierLoader.load("/lbpcascade_frontalface.xml");

        Mat frame = new Mat();
        while (true) {
            if (camera.read(frame)) {
                MatOfRect faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(frame, faceDetections);
                for (Rect face : faceDetections.toArray()) {
                    drawRectangle(frame, face, Colors.GREEN);
                    cognitiveServices.detectFaces(frame);
                }
                render(frame);
            }
        }
    }

    private void drawRectangle(Mat frame, Rect rect, Scalar color) {
        Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), color);
    }

    private void render(Mat frame) {
        BufferedImage bufferedImage = mat2BufferedImage(frame);
        ui.render(bufferedImage);
    }
}

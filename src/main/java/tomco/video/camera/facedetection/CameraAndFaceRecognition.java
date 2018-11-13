package tomco.video.camera.facedetection;

import org.opencv.core.*;
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

import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import static tomco.video.camera.facedetection.helpers.MatHelpers.mat2BufferedImage;

/**
 * Created by tomco on 20/01/2017.
 */
public class CameraAndFaceRecognition extends HelloOpenCV {
    public static void main(String[] args) {
        try {
            new CameraAndFaceRecognition().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private CognitiveServicesFacesNew cs;
    private UI ui;
    private VoiceTts voice;

    private boolean busy = false;

    public CameraAndFaceRecognition() {
        this.cs = new CognitiveServicesFacesNew("6ff5ae598540491583a5ae9f11029697","ap-java-spring-boot-one");
        this.ui = new UI();
        this.voice = new VoiceTts();
    }

    public void run() throws InterruptedException {
        VideoCapture camera = new VideoCapture(0);
        CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath().substring(1));
        CascadeClassifier eyeDetector = new CascadeClassifier(getClass().getResource("/haarcascade_eye_tree_eyeglasses.xml").getPath().substring(1));


        Mat frame = new Mat();
        
        while (true) {
            frame = Imgcodecs.imread("/people-08.jpg");
            if(true) {
            //if (camera.read(frame)) {
                MatOfRect faceDetections = new MatOfRect();
                MatOfRect eyeDetections = new MatOfRect();
                faceDetector.detectMultiScale(frame, faceDetections);
                eyeDetector.detectMultiScale(frame, eyeDetections);
                for (Rect face : faceDetections.toArray()) {
                    int matchingEyes = 0;
                    for (Rect eye : eyeDetections.toArray()) {
                        if (face.contains(eye.tl()) && face.contains(eye.br())) {
                            matchingEyes++;
                        }
                        drawRectangle(frame, eye, Colors.GREEN);
                    }

                    drawRectangle(frame, face, Colors.BLUE);
                    if (matchingEyes == 2) {
                        detectedFace(frame);
                    }
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

    public void detectedFace(Mat frame) {
        if (Config.BACKEND_ENABLED && !busy) {
            System.out.println("Detected face :-)");
            setTimer();
            Executors.newSingleThreadExecutor().execute(() -> {
                MatOfByte matOfByte = new MatOfByte();
                Imgcodecs.imencode(".jpg", frame, matOfByte);

                byte[] bytes = matOfByte.toArray().clone();

                DetectFaces detectedFaces = cs.detectFaces(bytes);
                if (detectedFaces.size() > 0) {
                    System.out.println("Microsoft faceIds: " + detectedFaces);
                    identify(detectedFaces);
                } else {
                    System.out.println("No faces found by Microsoft");
                }
            });
        }
    }

    private void setTimer() {
        busy = true;
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                busy = false;
            }
        }, Config.BACKEND_TIMEOUT);
    }

    private void identify(DetectFaces detectedFaces) {
        IdentifyFaces identify = cs.identify(detectedFaces);
        if (identify.size() > 0) {
            String personName = cs.getPersonName(identify.getPersonId().get(0));
            System.out.println("Faces Identified: " + personName);
            speak("Welcome " + personName);
        } else {
            System.out.println("Microsoft could not identify the found faces");
        }

    }

    private void speak(String s) {
        voice.speak(s);
    }
}

package tomco.demos.mqtt;

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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

import static tomco.video.camera.facedetection.helpers.MatHelpers.mat2BufferedImage;

public class MQTTDemo extends HelloOpenCV {

    public static void main(String[] args) {
        try {
            new MQTTDemo().run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private CognitiveServicesFacesNew cs;
    private final MQTTClient mqtt;
    private UI ui;
    private VoiceTts voice;
    private Mat frame;

    private boolean busy = false;

    public MQTTDemo() {
        //this.cs = new CognitiveServicesFacesNew("6ff5ae598540491583a5ae9f11029697", "ap-java-spring-boot-one");
        this.ui = new UI();
        //this.voice = new VoiceTts();
        //this.mqtt = MQTTClient.connect("tcp://tomcools.cloudapp.net:1883", "demo");
        this.mqtt = MQTTClient.connect("tcp://localhost:1883", "test");
        mqtt.subscribe("camera1", (s, mqttMessage) -> {
            System.out.println("Camera 1");
            new Thread(() -> {
                byte[] decode = Base64.getDecoder().decode(mqttMessage.getPayload());
                InputStream in = new ByteArrayInputStream(decode);
                BufferedImage bImageFromConvert = null;
                try {
                    bImageFromConvert = ImageIO.read(in);
                } catch (IOException e) {
                    System.out.println("Exception!");
                }
                render(bImageFromConvert);
            }).start();

        });
        mqtt.subscribe("faceDetected", (s, mqttMessage) -> speak(new String(mqttMessage.getPayload()) + mqttMessage));
    }

    public void run() throws InterruptedException {
        VideoCapture camera = new VideoCapture(0);
        CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath().substring(1));
        CascadeClassifier eyeDetector = new CascadeClassifier(getClass().getResource("/haarcascade_eye_tree_eyeglasses.xml").getPath().substring(1));

        startWebsocketPolling();

        frame = new Mat();

        while (true) {
            if (camera.read(frame)) {
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
                //render(frame);
            }

        }
    }

    private void drawRectangle(Mat frame, Rect rect, Scalar color) {
        Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), color);
    }

    private void render(final Mat frame) {
        BufferedImage bufferedImage = mat2BufferedImage(frame);
        render(bufferedImage);
    }

    private void render(final BufferedImage frame) {
        ui.render(frame);
    }

    public void detectedFace(Mat frame) {
        if (Config.BACKEND_ENABLED && !busy) {
            System.out.println("Detected face :-)");
            setTimer();
            Executors.newSingleThreadExecutor().execute(() -> {
                MatOfByte matOfByte = new MatOfByte();
                Imgcodecs.imencode(".png", frame, matOfByte);
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

    private void startWebsocketPolling() {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                new Thread(() -> {
                    Mat small = new Mat();
                    Imgproc.resize(frame, small, new Size(640, 480));
                    MatOfByte matOfByte = new MatOfByte();
                    Imgcodecs.imencode(".png", small, matOfByte);
                    byte[] bytes = matOfByte.toArray().clone();
                    //String encoded = new String(Base64.getEncoder().encode(bytes), StandardCharsets.UTF_8);
                    String encoded = Base64.getEncoder().encodeToString(bytes);
                    mqtt.sendMessage("camera1", encoded);
                }).start();

            }
        }, 3000, 100);
    }

    private void identify(DetectFaces detectedFaces) {
        IdentifyFaces identify = cs.identify(detectedFaces);
        if (identify.size() > 0) {
            String personName = cs.getPersonName(identify.getPersonId().get(0));
            System.out.println("Faces Identified: " + personName);
            //mqtt.sendMessage("faceDetected", "Welcome " + personName);
        } else {
            System.out.println("Microsoft could not identify the found faces");
        }

    }

    private void speak(String s) {
        voice.speak(s);
    }
}

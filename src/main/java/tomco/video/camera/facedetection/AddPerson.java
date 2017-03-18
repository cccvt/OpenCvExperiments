package tomco.video.camera.facedetection;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import tomco.HelloOpenCV;
import tomco.video.camera.facedetection.helpers.Colors;
import tomco.video.camera.facedetection.helpers.UI;
import tomco.video.camera.facedetection.helpers.cognitiveservices.CognitiveServicesFacesNew;
import tomco.video.camera.facedetection.helpers.cognitiveservices.responses.PersonAdd;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

import static tomco.video.camera.facedetection.helpers.MatHelpers.mat2BufferedImage;

/**
 * Created by tomco on 15/03/2017.
 */
public class AddPerson extends HelloOpenCV {
    public static void main(String[] args) {
        try {
            //new AddPerson().run();
            //new AddPerson().retrainAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<CognitiveServicesFacesNew> cs;
    private UI ui;
    public AddPerson() {
        this.cs = Arrays.asList(new CognitiveServicesFacesNew("6ff5ae598540491583a5ae9f11029697","ap-java-spring-boot-one"),
                                new CognitiveServicesFacesNew("c4eb56e78d684b8da960552fa0f6ab06","ap-java-spring-boot-two"),
                                new CognitiveServicesFacesNew("6c6dba3703ef44e0a7d7adffdd5c6fe3","ap-java-spring-boot-three"),
                                new CognitiveServicesFacesNew("af763cbcc5414e0d92246d9d7f6e789d","ap-java-spring-boot-four"),
                                new CognitiveServicesFacesNew("4ba200a5f8694ba7b106bfafb8166f56","ap-java-spring-boot-five"),
                                new CognitiveServicesFacesNew("e0a8b007f3444af18c642f2ca8a96c30","ap-java-spring-boot-six"),
                                new CognitiveServicesFacesNew("111e2f2979744734a73f73e3e42b4714","ap-java-spring-boot-seven"),
                                new CognitiveServicesFacesNew("55e5314f238f4b3a9b3fc51bf96db0a2","ap-java-spring-boot-eight"),
                                new CognitiveServicesFacesNew("d8b81c46edc54589931956975a88dbaa","ap-java-spring-boot-nine"),
                                new CognitiveServicesFacesNew("5328e4b1e9bf43c6b0412a95c1a138c5","ap-java-spring-boot-ten"),
                                new CognitiveServicesFacesNew("7a88ffcf4735493495a662be30094595","ap-java-spring-boot-eleven"),
                                new CognitiveServicesFacesNew("e5d40cb372634bb98421c10b7dcb9242","ap-java-spring-boot-twelve"),
                                new CognitiveServicesFacesNew("c6e27ad546394ff1966634f61dba7afb","ap-java-spring-boot-thirteen"),
                                new CognitiveServicesFacesNew("38597efb125644cf98644740b059b40b", "ap-java-spring-boot-fourteen"),
                                new CognitiveServicesFacesNew("c233a3a2e87d49219f1e95637a0c411b", "ap-java-spring-boot-fifteen"));
    }

    public void retrainAll() {
        cs.forEach(c -> c.trainGroup());
    }

    public void run() throws InterruptedException {
        this.ui = new UI();
        VideoCapture camera = new VideoCapture(0);
        CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath().substring(1));
        CascadeClassifier eyeDetector = new CascadeClassifier(getClass().getResource("/haarcascade_eye_tree_eyeglasses.xml").getPath().substring(1));

        String name = "Glados";

        boolean pictureNotTaken = true;
        Mat frame = new Mat();

        while (pictureNotTaken) {
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
                        System.out.println("Detected face :-) Using it to add to repositories.");
                        takePicture(frame, name);
                        pictureNotTaken = false;
                        System.out.println("Added Face, you are done :-)");
                    }
                }
                Thread.sleep(500);
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

    private void takePicture(Mat frame, String name) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, matOfByte);

        byte[] bytes = matOfByte.toArray().clone();

        cs.parallelStream().forEach(c -> {
            PersonAdd person = c.addPerson(name);
            c.addFaceToPerson(person, bytes);
        });
    }
}
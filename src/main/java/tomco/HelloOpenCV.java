package tomco;

import org.opencv.core.Core;
import tomco.video.camera.facedetection.CameraAndFaceRecognition;

//
// Detects faces in an image, draws boxes around them, and writes the results
// to "faceDetection.png".
//
public class HelloOpenCV {
    static {
        // Load the native library.
        System.load("C:/Users/tomco/Downloads/opencv/build/java/x64/" + Core.NATIVE_LIBRARY_NAME + ".dll");
        System.load("C:/Users/tomco/Downloads/opencv/build/bin/" + "opencv_ffmpeg320_64" + ".dll");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello, OpenCV");


        //new TemplateMatchingDemo().run();
        //new DetectFaceDemo().run();
        //new TelenetLogo().classify();
        new CameraAndFaceRecognition().run();
        //new VideoToImages().run();
    }
}


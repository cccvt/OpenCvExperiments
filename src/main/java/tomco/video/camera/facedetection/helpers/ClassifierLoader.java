package tomco.video.camera.facedetection.helpers;

import org.opencv.objdetect.CascadeClassifier;

public class ClassifierLoader {
    public static CascadeClassifier load(String name) {
        return new CascadeClassifier(ClassifierLoader.class.getResource(name).getPath().substring(1));
    }
}

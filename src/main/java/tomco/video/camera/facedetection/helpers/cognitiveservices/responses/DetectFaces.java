package tomco.video.camera.facedetection.helpers.cognitiveservices.responses;

import java.util.List;

/**
 * Created by tomco on 22/01/2017.
 */
public class DetectFaces {
    List<String> detectedFaces;

    public DetectFaces(List<String> detectedFaces) {
        this.detectedFaces = detectedFaces;
    }

    public List<String> getDetectedFaces() {
        return detectedFaces;
    }

    public int size() {
        return detectedFaces.size();
    }

    @Override
    public String toString() {
        return detectedFaces.stream().reduce("", (a, b) -> a + b);
    }
}

package tomco.video.camera.facedetection.helpers.cognitiveservices.responses;

import java.util.List;

/**
 * Created by tomco on 22/01/2017.
 */
public class IdentifyFaces {
    private List<String> personId;

    public IdentifyFaces(List<String> personId) {
        this.personId = personId;
    }

    public List<String> getPersonId() {
        return personId;
    }

    public int size() {
        return personId.size();
    }
}

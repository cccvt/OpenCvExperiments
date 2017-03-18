package tomco.video.camera.facedetection.helpers.cognitiveservices.responses;

/**
 * Created by tomco on 14/03/2017.
 */
public class PersonAdd {
    private String personId;

    public PersonAdd() {
    }

    public PersonAdd(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }
}

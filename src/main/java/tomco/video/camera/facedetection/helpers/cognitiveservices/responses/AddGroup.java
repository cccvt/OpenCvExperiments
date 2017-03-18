package tomco.video.camera.facedetection.helpers.cognitiveservices.responses;

public class AddGroup {
    private String name;
    private String userdata;

    public AddGroup() {
    }

    public AddGroup(String name, String userdata) {
        this.name = name;
        this.userdata = userdata;
    }

    public String getName() {
        return name;
    }

    public String getUserdata() {
        return userdata;
    }

    @Override
    public String toString() {
        return "AddGroup{" +
                "name='" + name + '\'' +
                ", userdata='" + userdata + '\'' +
                '}';
    }
}

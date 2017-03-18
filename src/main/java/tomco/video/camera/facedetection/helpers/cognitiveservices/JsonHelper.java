package tomco.video.camera.facedetection.helpers.cognitiveservices;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import tomco.video.camera.facedetection.helpers.cognitiveservices.responses.DetectFaces;
import tomco.video.camera.facedetection.helpers.cognitiveservices.responses.IdentifyFaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomco on 22/01/2017.
 */
public class JsonHelper {
    private final static Gson GSON = new Gson();

    public DetectFaces toDetectFaces(String jsonResponse) {
        JsonArray jsonArray = GSON.fromJson(jsonResponse, JsonArray.class);

        List<String> faceIds = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            String faceId = jsonArray.get(i).getAsJsonObject().get("faceId").getAsString();
            faceIds.add(faceId);
        }
        return new DetectFaces(faceIds);
    }

    public String toIdentifyRequest(DetectFaces detectFaces, String persongroup) {
        //TODO expand so it identifies all found faces
        String faceId = detectFaces.getDetectedFaces().get(0);
        return "{    \n" +
                "    \"personGroupId\":\"" + persongroup + "\",\n" +
                "    \"faceIds\":[\n" +
                "        \"" + faceId + "\"\n" +
                "    ],\n" +
                "    \"maxNumOfCandidatesReturned\":1" +
                "}";
    }

    public String toAddPersonRequest(String name) {
        return "{ \"name\":\""+name+"\", \"userData\":\"\"}";
    }

    public IdentifyFaces toIdentifyFaces(String response) {
        JsonArray jsonArray = GSON.fromJson(response, JsonArray.class);

        List<String> personIds = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonArray candidates = jsonArray.get(0).getAsJsonObject()
                    .get("candidates")
                    .getAsJsonArray();

            if (candidates.size() == 1) {
                String personId = candidates.get(0)//only get best match per face for now :-)
                        .getAsJsonObject()
                        .get("personId")
                        .getAsString();
                personIds.add(personId);
            }

        }
        return new IdentifyFaces(personIds);
    }
}

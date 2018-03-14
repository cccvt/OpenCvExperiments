package tomco.video.camera.facedetection.helpers.cognitiveservices;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import tomco.video.camera.facedetection.helpers.cognitiveservices.responses.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class CognitiveServicesFacesNew {

    private final static CloseableHttpClient HTTP = HttpClientBuilder.create().build();
    private final static JsonHelper JSON = new JsonHelper();
    private String key;
    private String persongroup;

    public CognitiveServicesFacesNew(String key, String persongroup) {
        this.key = key;
        this.persongroup = persongroup;
    }

    public DetectFaces detectFaces(byte[] bytes) {
        HttpPost post = new HttpPost("https://westus.api.cognitive.microsoft.com/face/v1.0/detect");
        post.setEntity(new ByteArrayEntity(bytes));
        post.setHeader("content-type", "application/octet-stream");
        appendKeyHeader(post);
        return JSON.toDetectFaces(executeRequest(post));
    }

    public PersonAdd addPerson(String name) {
        //https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}/persons
        HttpPost post = new HttpPost("https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/" + persongroup + "/persons");
        post.setEntity(createEntity(JSON.toAddPersonRequest(name)));
        post.setHeader("content-type", "application/json");
        appendKeyHeader(post);

        String response = executeRequest(post);
        return new Gson().fromJson(response, PersonAdd.class);
    }

    public void addFaceToPerson(PersonAdd person, byte[] picture) {
        //https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces[?userData][&targetFace]
        HttpPost post = new HttpPost("https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/" + persongroup + "/persons/" + person.getPersonId() + "/persistedFaces");
        post.setEntity(new ByteArrayEntity(picture));
        post.setHeader("content-type", "application/octet-stream");
        appendKeyHeader(post);
        executeRequest(post);
    }

    public void clearAll() {
        HttpGet get = new HttpGet("https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/" + persongroup + "/persons/");
        appendKeyHeader(get);
        String response = executeRequest(get);

    }

    public void createGroup() {
        //https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}
        HttpPut put = new HttpPut("https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/" + persongroup);
        put.setEntity(createEntity(new Gson().toJson(new AddGroup("persongroup", ""))));
        put.setHeader("content-type", "application/json");
        appendKeyHeader(put);

        appendKeyHeader(put);
        executeRequest(put);
    }

    public void trainGroup() {
        //https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}/train
        HttpPost post = new HttpPost("https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/" + persongroup + "/train");
        appendKeyHeader(post);
        executeRequest(post);
    }

    public IdentifyFaces identify(DetectFaces detectedfaces) {
        HttpPost post = new HttpPost("https://westus.api.cognitive.microsoft.com/face/v1.0/identify");
        post.setEntity(createEntity(JSON.toIdentifyRequest(detectedfaces, persongroup)));
        post.setHeader("content-type", "application/json");
        appendKeyHeader(post);

        String response = executeRequest(post);

        return JSON.toIdentifyFaces(response);
    }

    //URL https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/{personGroupId}/persons/{personId}
    public String getPersonName(String personId) {
        HttpGet get = new HttpGet("https://westus.api.cognitive.microsoft.com/face/v1.0/persongroups/" + persongroup + "/persons/" + personId);
        appendKeyHeader(get);

        String response = executeRequest(get);
        JsonObject object = new Gson().fromJson(response, JsonObject.class);
        return object.get("name").toString();
    }

    private void appendKeyHeader(HttpRequestBase requestBase) {
        requestBase.setHeader("Ocp-Apim-Subscription-Key", key);
    }

    private StringEntity createEntity(String string) {
        try {
            return new StringEntity(string);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error while creating string entity: " + e);
        }
    }

    private String executeRequest(HttpRequestBase request) {
        Scanner scanner = null;
        try {
            CloseableHttpResponse response = HTTP.execute(request);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("No 200 response: " + response.getStatusLine().getReasonPhrase());
            } else {
                scanner = new Scanner(response.getEntity().getContent());
                StringBuilder sb = new StringBuilder();
                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }
                return sb.toString();
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while executing request;");
        }
    }
}

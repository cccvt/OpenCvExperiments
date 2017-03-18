package tomco.video.camera.facedetection.helpers.cognitiveservices.responses;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by tomco on 22/01/2017.
 */
public class PersonIdCache {

    public static String nameForPersonId(String id) {
        JsonArray array = new Gson().fromJson(HARDCODEDRESPONSE, JsonArray.class);
        for (int i = 0; i < array.size(); i++) {
            JsonObject obj = array.get(i).getAsJsonObject();
            String personId = obj.get("personId").getAsString();
            if (id.equals(personId)) {
                return obj.get("name").getAsString();
            }
        }
        return "Unknown name";
    }

    private static String HARDCODEDRESPONSE = "[\n" +
            "  {\n" +
            "    \"personId\": \"027f50a4-2553-4138-87d6-ea1e14342860\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Nick Michiels\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"064fddcf-f61b-4b60-8db8-560eeff1f35f\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Karolien Van Riel\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"0967e9fa-e222-44f0-ac41-4420f96df9f8\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Vincent Van Camp\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"097ee8ba-9644-4a98-b349-aeab6b66f6f6\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Jelle Spoelders\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"0af359b0-c351-45aa-bc54-2f26b8142e9f\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Sofiane Chahboun\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"0d8b8c01-f213-4a61-b6e4-aec27a16174c\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Kimberly Ivens\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"118b4d2e-1cac-4cd1-b8e3-6264a7943fe8\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Marc Wils\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"1533aba6-47e7-4aff-a9ab-a09bc4ba69b3\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Glenn Dierckx\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"1726b43d-a333-45a2-8dec-cdf874f5d6d2\",\n" +
            "    \"persistedFaceIds\": [\n" +
            "      \"79917bd3-5a70-4999-9637-e88781bfee3d\",\n" +
            "      \"a7eb86dc-48f8-40c8-86ce-0d038b6f03c6\"\n" +
            "    ],\n" +
            "    \"name\": \"Tim Mahy\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"173647ee-3adf-49b0-a66b-7e2638b47e0d\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Jonas Annaert\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"1b77f5dc-32b8-4b7d-9ec4-d7c53e8a517d\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Georgios Van Sanden\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"2b3b4368-c395-4fd8-8a25-db695282e0ca\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Dieter Sneyders\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"3d58f243-d185-425d-8011-1153329fad0d\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Kevin De Bruyn\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"585dfe4a-1c4a-449d-9988-942cfa2c670f\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Jasper Catthoor\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"60eed883-4160-4e51-95ea-84fe8c3304ee\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Tim Vermeulen\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"67b2387d-6b63-4d28-83b2-4d424467b121\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Yenthe Oerlemans\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"6bb85738-f703-45ec-8169-32813b0bbdd2\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Sara Shafaq\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"7c9b0844-3705-4076-a7f4-66afa4301134\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Joris Dirickx\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"91691e32-7232-4d41-b1c0-c3de91707685\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Tom Vervoort\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"989b0a6b-0e19-49c9-889f-77d81c5f6b82\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Stijn de Smet\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"a6b301ac-1c2e-41cc-87cf-2261cff0576b\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Martijn Moreel\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"a70b42b1-0532-46a1-b30c-43965405bb7a\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Hans Peeters\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"ab2a95e4-8b80-462a-8314-80464be968d2\",\n" +
            "    \"persistedFaceIds\": [\n" +
            "      \"91e6d406-dd9c-47a1-bca2-8eb872d50b1a\"\n" +
            "    ],\n" +
            "    \"name\": \"Robby Decosemaeker\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"bc09511e-d3ce-4ec0-94c5-4642e3014610\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Rudi Claes\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"bc605012-0d6a-4d16-99af-5228bcee52be\",\n" +
            "    \"persistedFaceIds\": [\n" +
            "      \"7b38ea6d-2cfa-421b-a56c-4c3f21794c66\",\n" +
            "      \"c18b084b-c40c-40ba-ae11-0e96dd3e491b\"\n" +
            "    ],\n" +
            "    \"name\": \"Yoeri Van Damme\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"cc5f7360-d8b8-44d9-9ba6-ec0567359053\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Christel Peeters\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"cea47848-c50e-4939-b62e-ecf99748936b\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Stefanie Manssens\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"d01278fa-48ef-46d4-aaf8-42b7a9305f64\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Benny Michielsen\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"d637bb86-4f07-490e-aab9-74bb0c111aa7\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Wannes Van Regenmortel\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"d6b81099-0835-473b-9fb7-94f6e15e9faa\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Chris Declat\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"ddc6d04f-2bbc-4d8c-b7c3-5ea3de6ae6a4\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Tim Geerts\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"f06ddc94-a493-4d45-ba37-4f4d5ac3f57f\",\n" +
            "    \"persistedFaceIds\": [],\n" +
            "    \"name\": \"Arne De Cock\",\n" +
            "    \"userData\": null\n" +
            "  },\n" +
            "  {\n" +
            "    \"personId\": \"fc80c52c-d2a5-44ee-ba5b-ac0950d2ce06\",\n" +
            "    \"persistedFaceIds\": [\n" +
            "      \"6ebd8908-7c0b-4c3c-b2ee-0ad0ff2951c4\"\n" +
            "    ],\n" +
            "    \"name\": \"Tom Cools\",\n" +
            "    \"userData\": \"The grand creator! :-)\"\n" +
            "  }\n" +
            "]";
}

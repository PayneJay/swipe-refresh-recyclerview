package jack.myapplication.utils;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class SerializeUtils {
    public static String toJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static String toJson(HashMap<String, String> hashMap) {
        JSONObject jsonObject = new JSONObject(hashMap);
        return jsonObject.toString();
    }
}

package jack.myapplication.http;

import java.util.HashMap;

import jack.myapplication.Constants;
import jack.myapplication.utils.SerializeUtils;

public class Sender {
    private static Sender instance;

    public static Sender getInstance() {
        if (instance == null) {
            instance = new Sender();
        }
        return instance;
    }

    private Runnable getRequestTask = new Runnable() {
        @Override
        public void run() {
            HttpClient.getInstance().requestGet(null, null);
        }
    };
    private Runnable postRequestTask = new Runnable() {
        @Override
        public void run() {
            HashMap<String, String> paramsMap = new HashMap<>();
            paramsMap.put("user_name", "admin");
            paramsMap.put("password", "123456");
            HttpClient.getInstance().requestPost(SerializeUtils.toJson(paramsMap), null);
        }
    };

    public void sendRequest(String method) {
        switch (method) {
            case Constants.GET:
                new Thread(getRequestTask).start();
                break;
            case Constants.POST:
                new Thread(postRequestTask).start();
                break;
        }
    }
}

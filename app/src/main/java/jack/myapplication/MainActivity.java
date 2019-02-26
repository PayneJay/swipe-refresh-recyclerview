package jack.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;

import jack.myapplication.http.HttpClient;
import jack.myapplication.http.INetCallback;
import jack.myapplication.utils.SerializeUtils;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //再次添加一行注释

        findViewById(R.id.btn_get).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(getRequestTask).start();
            }
        });
        findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(postRequestTask).start();
            }
        });
    }
}
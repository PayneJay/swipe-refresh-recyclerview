package jack.myapplication.http;

import android.accounts.NetworkErrorException;
import android.util.Log;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import jack.myapplication.utils.SerializeUtils;

public class HttpClient {
    private final String TAG = "Apollo";
    private final String baseUrl = "http://localhost:8008/data/student.json";
    private static HttpClient instance;
    private final String GET = "GET";
    private final String POST = "POST";

    public static HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    private HttpURLConnection getConnection(String requestUrl) throws IOException {
        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 设置连接主机超时时间
        connection.setConnectTimeout(5 * 1000);
        //设置从主机读取数据超时
        connection.setReadTimeout(5 * 1000);
        // 设置是否使用缓存  默认是true
        connection.setUseCaches(true);
        //connection设置请求头信息
        //设置请求中的媒体类型信息。
        connection.setRequestProperty("Content-Type", "application/json");
        //设置客户端与服务连接类型
        connection.addRequestProperty("Connection", "Keep-Alive");

        return connection;
    }

    /**
     * get请求
     *
     * @param paramsMap 请求参数
     */
    public void requestGet(HashMap<String, String> paramsMap, INetCallback callback) {
        HttpURLConnection urlConn = null;
        try {
            StringBuilder tempParams = new StringBuilder();
            if (paramsMap != null && paramsMap.size() > 0) {
                int pos = 0;
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                    pos++;
                }
            }
            String requestUrl = baseUrl + tempParams.toString();

            // 打开一个HttpURLConnection连接
            urlConn = getConnection(requestUrl);
            // 设置为GET请求
            urlConn.setRequestMethod(GET);
            //urlConn设置请求头信息
            // 开始连接
            urlConn.connect();
            handleResponse(callback, urlConn, GET);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            // 关闭连接
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }


    /**
     * post请求
     *
     * @param params 请求参数
     */
    public void requestPost(String params, INetCallback callback) {
        HttpURLConnection urlConn = null;
        try {
            // 请求的参数转换为byte数组
            byte[] postData = params.getBytes();
            // 打开一个HttpURLConnection连接
            urlConn = getConnection(baseUrl);
            urlConn.setChunkedStreamingMode(0);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod(POST);
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            // 开始连接
            urlConn.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            handleResponse(callback, urlConn, POST);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            // 关闭连接
            if (urlConn != null) {
                urlConn.disconnect();
            }
        }
    }

    private void handleResponse(INetCallback callback, HttpURLConnection urlConn, String requestMethod) throws IOException {
        // 判断请求是否成功
        if (urlConn.getResponseCode() == 200) {
            // 获取返回的数据
            String result = streamToString(urlConn.getInputStream());
            if (callback != null) {
                callback.onSuccess(result);
            }
            Log.e(TAG, requestMethod + "方式请求成功，result--->" + result);
        } else {
            if (callback != null) {
                callback.onFailed();
            }
            Log.e(TAG, requestMethod + "方式请求失败");
        }
    }

    private String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

    public String get(String url) {
        HttpURLConnection conn = null;
        try {
            // 利用string url构建URL对象
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {

                InputStream is = conn.getInputStream();
                String response = streamToString(is);
                return response;
            } else {
                throw new NetworkErrorException("response status is " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

}

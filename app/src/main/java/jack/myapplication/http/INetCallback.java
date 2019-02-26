package jack.myapplication.http;

public interface INetCallback {
    void onSuccess(String result);

    void onFailed();
}

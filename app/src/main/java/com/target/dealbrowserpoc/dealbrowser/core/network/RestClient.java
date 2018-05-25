package com.target.dealbrowserpoc.dealbrowser.core.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.ParameterizedType;
import com.target.dealbrowserpoc.dealbrowser.R;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RestClient {
    public final static String HTTP_RESPONSE = "http-response";
    public final static String HTTP_RESPONSE_CODE = "http-status";
    public final static String USER_AGENT = "Mobile";
    public final static String ACCEPT_ENCODING = "gzip";
    public static final String KEY_CONTENT_ENCODING = "Content-Encoding";
    private final static int HTTP_RESPONSE_OK = 200;
    private final static int HTTP_RESPONSE_FORBIDDEN = 403;
    private final static int HTTP_RESPONSE_BAD_REQUEST = 400;
    private static final String KEY_HEADER_TOKEN = "X-Auth-TOKEN";
    private static final String KEY_HEADER_MOBILE_APK_VERSION = "mobileApkVersion";
    private final static String KEY_SOURCE = "X-Source";
    private final static String KEY_USER_AGENT = "user-agent";
    private final static String KEY_USER_PREFERRED_LANGUAGE_CODE = "preferredLanguageCode";
    private final static String KEY_ACCEPT_ENCODING = "Accept-Encoding";
    private static final MediaType MEDIA_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_IMAGE = MediaType.parse("image/png");
    private static final int TIMEOUT_IN_SECONDS = 60;
    private static OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
    private static Headers.Builder headersBuilder = new Headers.Builder();
    private Context context;

    private Handler uiMessageHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message message) {
            if (context != null) {
                Toast.makeText(context, R.string.no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public RestClient(Context context) {
        this.context = context;
    }

    private static OkHttpClient configureClient() {
        httpClientBuilder.readTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
        httpClientBuilder.retryOnConnectionFailure(true);
        return httpClientBuilder.build();
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        // if running on emulator return true always.
        return android.os.Build.MODEL.equals("google_sdk");
    }

    private Headers getHeaders() {
        headersBuilder.set(KEY_USER_AGENT, USER_AGENT);
        headersBuilder.set(KEY_ACCEPT_ENCODING, ACCEPT_ENCODING);

        return headersBuilder.build();
    }

    public HashMap<String, Object> get(@NonNull final String endPoint, ParameterizedType type) {
        Request request = new Request.Builder().headers(getHeaders()).url(endPoint).get().build();
        return execute(request, type, Boolean.FALSE);
    }

    protected HashMap<String, Object> execute(@NonNull final Request request, ParameterizedType type, boolean isShortLiveServiceCall) {
        HashMap<String, Object> status = new HashMap<>();
        try {
            if (isOnline()) {
                OkHttpClient client = configureClient();
                // Execute the request and retrieve the response.
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    Object data;
                    if (response.header(KEY_CONTENT_ENCODING) != null && response.header(KEY_CONTENT_ENCODING).contains(ACCEPT_ENCODING)) {

                        data = (type != null)
                                ? LoganSquare.parse(response.body().byteStream(), type)
                                : response.body().string();//bytes();
                    } else {
                        data = (type != null)
                                ? LoganSquare.parse(response.body().byteStream(), type)
                                : response.body().string().trim();
                    }

                    status.put(HTTP_RESPONSE, data);
                }
                status.put(HTTP_RESPONSE_CODE, response.code());
            }
        } catch (@NonNull UnknownHostException | SocketTimeoutException e) {
            uiMessageHandler.sendEmptyMessageDelayed(0, 2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }

    public Object getData(@NonNull final HashMap<String, Object> response) {
        return response.get(HTTP_RESPONSE);
    }

    public boolean isSuccessful(final HashMap<String, Object> response) {
        int httpResponseCode = -1;
        if (!response.isEmpty()) {
            httpResponseCode = Integer.parseInt(response.get(HTTP_RESPONSE_CODE).toString());
        }
        return (!response.isEmpty() && httpResponseCode == HTTP_RESPONSE_OK);
    }

    public boolean isBadRequest(final HashMap<String, Object> response) {
        int httpResponseCode = -1;
        if (!response.isEmpty()) {
            httpResponseCode = Integer.parseInt(response.get(HTTP_RESPONSE_CODE).toString());
        }
        return (!response.isEmpty() && httpResponseCode == HTTP_RESPONSE_BAD_REQUEST);
    }

    public boolean isForbidden(final HashMap<String, Object> response) {
        int httpResponseCode = -1;
        if (!response.isEmpty()) {
            httpResponseCode = Integer.parseInt(response.get(HTTP_RESPONSE_CODE).toString());
        }
        return (!response.isEmpty() && httpResponseCode == HTTP_RESPONSE_FORBIDDEN);
    }
}
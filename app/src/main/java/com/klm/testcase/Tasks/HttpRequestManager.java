package com.klm.testcase.Tasks;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;

import org.json.JSONObject;


import okhttp3.OkHttpClient;

/**
 * Responsible for managing http requests
 *
 * @author Heitor Zanetti
 * @version 1.0.0
 */
public class HttpRequestManager implements JSONObjectRequestListener {

    private final String BASE_URL = "https://fox.klm.com/fox/json/flightstatuses?";

    private Object                     responseObjectClass;
    private TaskManager.TaskListener   taskListener;

    public HttpRequestManager(Object responseObjectClass, TaskManager.TaskListener taskListener) {
        this.responseObjectClass       = responseObjectClass;
        this.taskListener              = taskListener;
    }


    public void get(String url) {

        AndroidNetworking.get(getAbsoluteUrl(url))
                .setPriority(Priority.HIGH)
                .setOkHttpClient(newOkHttpClient())
                .build()
                .getAsJSONObject(this);
    }


    private OkHttpClient newOkHttpClient(){
        return new OkHttpClient().newBuilder()
                .retryOnConnectionFailure(true)
                .build();
    }


    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }



    @Override
    public void onResponse(JSONObject response) {

        Object responseObject = new Gson().fromJson(response.toString(), responseObjectClass.getClass());

        if(taskListener != null) {
            taskListener.performTask(responseObject);
        }
    }

    @Override
    public void onError(ANError anError) {
        Log.e("REQUEST", "Error: " + anError.getMessage());
    }
}
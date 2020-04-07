package com.adim.example.dynamic_list.Presenter;

import android.app.ProgressDialog;
import android.util.Log;

import com.adim.example.dynamic_list.Interfaces.MainContract;
import com.adim.example.dynamic_list.View.MainActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainPresenter implements MainContract.Operations{
    private MainContract.ViewOps viewOps;
    ProgressDialog pdia;

    public MainPresenter(MainContract.ViewOps viewOps) {
        this.viewOps = viewOps;
    }

    @Override
    public void getItems(MainActivity mainActivity) {
        pdia = new ProgressDialog(mainActivity);
        pdia.setMessage("Loading...");
        pdia.setCanceledOnTouchOutside(false);
        pdia.show();


        String PRODUCTS_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/facts.json";
        Log.e("PRODUCTS_URL", "" + PRODUCTS_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, PRODUCTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Res>>>>>>>", "" + response);
                        viewOps.ResSuccess(response);
                        pdia.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        String message = null;
                        if (volleyError instanceof ServerError) {
                            message = "The server could not be found. Please try again after some time!!";
                        } else if (volleyError instanceof AuthFailureError) {
                            message = "Auth failure error!";
                        } else if (volleyError instanceof ParseError) {
                            message = "Parsing error! Please try again after some time!!";
                        } else if (volleyError instanceof NoConnectionError || volleyError instanceof TimeoutError) {
                            message = "Communication error";
                        }
                        final String finalMessage = message;
                        viewOps.onError(message);
                        pdia.dismiss();
                    }
                }) {


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(mainActivity);
        requestQueue.add(stringRequest);
    }
}

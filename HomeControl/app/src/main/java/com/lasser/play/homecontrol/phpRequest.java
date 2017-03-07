package com.lasser.play.homecontrol;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class phpRequest extends AsyncTask<String, Void, String> {

    private Context context;
    public String requestData;
    public phpRequest(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        String parameters = "?"+arg0[0];
        String requestType = arg0[1];
        String link="http://lowcost-env.jxaj2rarqq.ap-southeast-1.elasticbeanstalk.com/WebsiteCode/";
        String data;
        BufferedReader bufferedReader;
        String result;
        try {
            //data = "?fullname=" + URLEncoder.encode(fullName, "UTF-8");
            link +=requestType+parameters;
            URL url = new URL(link);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            return result;
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //this.requestData = result;
        //Log.d("Data",result);
    }
}
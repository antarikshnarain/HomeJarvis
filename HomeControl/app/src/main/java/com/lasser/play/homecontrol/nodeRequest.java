package com.lasser.play.homecontrol;

/**
 * Created by antar on 27-Jan-17.
 */

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;


public class nodeRequest extends AsyncTask<String, Void, String> {

    private Context context;
    public String requestData;
    public nodeRequest(Context context) {
        this.context = context;
    }

    protected void onPreExecute() {

    }

    @Override
    protected String doInBackground(String... arg0) {
        //String parameters = "?"+arg0[0];
        //String requestType = arg0[1];
        String link="http://192.168.43.231:8080/swap";
        String uurl = arg0[0];
        JSONObject jsonobj = new JSONObject();
        // NOTE: Nested JSON is possible
        try {
            jsonobj.put("name", "antariksh");
            jsonobj.put("age", 23);
        }
        catch (JSONException ex){
            ex.printStackTrace();
        }
        if(uurl==""){
            uurl=link;
        }
        else {
            uurl = "http://" + uurl;
        }
        BufferedReader bufferedReader;
        String result;
        try {
            //data = "?fullname=" + URLEncoder.encode(fullName, "UTF-8");
            String message = jsonobj.toString();
            URL url = new URL(uurl);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setChunkedStreamingMode(0);// Size of data unknown
            // making http header
            con.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            con.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            //Connect
            con.connect();
            //setup send
            OutputStream os = new BufferedOutputStream(con.getOutputStream());
            os.write(message.getBytes());
            os.flush();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = bufferedReader.readLine();
            //con.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
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
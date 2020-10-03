package com.automobilegt.carmechanicworkshop.controller;

import android.text.TextUtils;

import com.automobilegt.carmechanicworkshop.model.RepairVideo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RepairVideoQuery {
    public RepairVideoQuery() {
    }
    public static List<RepairVideo> fetchRepairVideoData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            e.printStackTrace();
        }
        return extractCarBrandFromJson(jsonResponse);
    }

    private static List<RepairVideo> extractCarBrandFromJson(String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<RepairVideo> repairVideoList = new ArrayList<>();
        try {
            JSONArray response = new JSONArray(jsonResponse);
            for (int i=0; i<response.length(); i++){
                JSONObject jsonObject = response.getJSONObject(i);
                repairVideoList.add(new RepairVideo(jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("link")));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return repairVideoList;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }
}

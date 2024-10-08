package com.automobilegt.carmechanicworkshop.controller;

import android.text.TextUtils;

import com.automobilegt.carmechanicworkshop.model.Car;
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

public class RequestQuery {
    public RequestQuery() {
    }

    //method to fetch brand list for CarBrandActivity from 2 links
    public static List<Car> fetchBrandData(String firstUrl, String secondUrl){
        String jsonResponse = null;
        try{
            jsonResponse = makeRequest(firstUrl);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(jsonResponse == null || jsonResponse.isEmpty()){
            try {
                jsonResponse = makeRequest(secondUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return extractBrandFromJsonFile(jsonResponse);
    }

    private static List<Car> extractBrandFromJsonFile(String carBrandJSON) {
        if (TextUtils.isEmpty(carBrandJSON)){
            return null;
        }
        List<Car> Brands = new ArrayList<>();
        try {
            JSONArray carBrandArray = new JSONArray(carBrandJSON);
            for (int i =0; i < carBrandArray.length(); i++){
                Brands.add(new Car(carBrandArray.getString(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Brands;
    }

    //method to fetch model list for CarModelActivity from 2 links
    public static List<Car> fetchModelData(String firstUrl, String secondUrl, int logo){
        String jsonResponse = null;
        try{
            jsonResponse = makeRequest(firstUrl);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(jsonResponse == null || jsonResponse.isEmpty()){
            try {
                jsonResponse = makeRequest(secondUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return extractModelFromJsonFile(jsonResponse, logo);
    }

    private static List<Car> extractModelFromJsonFile(String jsonResponse, int carModelLogo) {
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<Car> carList = new ArrayList<>();
        try {
            JSONArray carModelArray = new JSONArray(jsonResponse);
            for (int i=0; i<carModelArray.length(); i++){
                carList.add(new Car(carModelArray.getString(i), carModelLogo));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return carList;
    }

    //method to fetch year list for CarYearActivity from 2 links
    public static List<Car> fetchYearData(String firstUrl, String secondUrl, int logo) {
        String jsonResponse = null;
        try{
            jsonResponse = makeRequest(firstUrl);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(jsonResponse == null || jsonResponse.isEmpty()){
            try {
                jsonResponse = makeRequest(secondUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return extractYearFromJsonFile(jsonResponse, logo);
    }

    private static List<Car> extractYearFromJsonFile(String jsonResponse, int logo) {
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }
        List<Car> list = new ArrayList<>();
        try {
            JSONArray response = new JSONArray(jsonResponse);
            for (int i=0; i<response.length(); i++){
                list.add(new Car(response.getString(i), logo));
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return list;
    }

    //method to fetch video list for VideoListActivity from 2 links
    public static List<RepairVideo> fetchRepairVideoData(String firstUrl, String secondUrl){
        String jsonResponse = null;
        try{
            jsonResponse = makeRequest(firstUrl);
        }catch (IOException e){
            e.printStackTrace();
        }
        if(jsonResponse == null || jsonResponse.isEmpty()){
            try {
                jsonResponse = makeRequest(secondUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return extractVideoListFromJson(jsonResponse);
    }

    private static List<RepairVideo> extractVideoListFromJson(String jsonResponse) {
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

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try{
            url = new URL(requestUrl);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    //method to make an http request and return a String JSON variable
    private static String  makeRequest(String requestUrl) throws IOException{
        URL url  = createUrl(requestUrl);
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
}

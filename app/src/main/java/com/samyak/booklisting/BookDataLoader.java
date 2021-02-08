package com.samyak.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class BookDataLoader extends AsyncTaskLoader<List<BookData>> {

    private static final String LOG_TAG = "BookDataLoader";
    String mUrl;

    public BookDataLoader(@NonNull Context context,String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public List<BookData> loadInBackground() {
        if (mUrl==null) {
            return null;
        }

        ArrayList<BookData> books = fetchBooks(mUrl);
        return books;
    }

    private ArrayList<BookData> fetchBooks(String stringUrl){
        URL url = createUrl(stringUrl);
        String jsonresponse = "";
        try{
            jsonresponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        ArrayList<BookData> bookData = extractBookData(jsonresponse);
        return bookData;

    }

    private URL createUrl(String UrlString){
        URL url = null;
        try {
            url = new URL(UrlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }

        return url;
    }

    private String makeHttpRequest(URL url) throws IOException {
        String jsonresponse = "";
        if(url==null){
            return null;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream =null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonresponse = readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonresponse;
    }

    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private Bitmap fetchBookImage(String stringUrl){
            URL url = createUrl(stringUrl);
            Bitmap photoBitmap = null;
            try{
                photoBitmap = makeHttpRequestforimage(url);
                return photoBitmap;
            }catch (IOException e){
                Log.e(LOG_TAG, "Error closing input stream", e);
            }
            return null;
        }

    private Bitmap makeHttpRequestforimage(URL url) throws IOException {
        Bitmap photoBitmap;
        if(url==null){
            return null;
        }
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream =null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                photoBitmap = readFromStreamforimage(inputStream);
                return photoBitmap;
            }else {
                Log.e(LOG_TAG, "Error response code: " + httpURLConnection.getResponseCode());
            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        }finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;
    }

    private Bitmap readFromStreamforimage(InputStream inputStream) throws IOException {
        if (inputStream!=null){
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            return BitmapFactory.decodeStream(bufferedInputStream);
        }
        return null;
    }


    private ArrayList<BookData> extractBookData(String jsonresponse){
        ArrayList<BookData> bookDataArrayList = new ArrayList<BookData>();

        try {
            JSONObject jsonObject = new JSONObject(jsonresponse);
            JSONArray bookinfo = jsonObject.getJSONArray("items");

            for(int i = 0 ; i<bookinfo.length(); i++){
                JSONObject singlebookinfo = bookinfo.getJSONObject(i);
                JSONObject volumeinfo = singlebookinfo.getJSONObject("volumeInfo");

                String booktitle = volumeinfo.getString("title");

                JSONArray bookauthors = volumeinfo.getJSONArray("authors");
                String tempauthors = "";
                for(int j = 0 ; j<bookauthors.length(); j++){
                    tempauthors = bookauthors.getString(j)+" ";
                }

                String authors = tempauthors.trim();

                if(volumeinfo.has("imageLinks")) {
                    JSONObject image = volumeinfo.getJSONObject("imageLinks");
                    String bookthumbnail = image.getString("smallThumbnail");
                    bookthumbnail = bookthumbnail.replace("http://","https://");
                    Bitmap mybitmap = fetchBookImage(bookthumbnail);
                    bookDataArrayList.add(new BookData(booktitle,authors,mybitmap));
                }

                bookDataArrayList.add(new BookData(booktitle,authors,null));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        return bookDataArrayList;
    }

}


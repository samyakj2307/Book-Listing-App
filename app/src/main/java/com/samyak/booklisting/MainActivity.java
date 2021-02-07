package com.samyak.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookData>> {

    private final String API_KEY = "AIzaSyDLMs9WTD8VQBajPlznYPCf5evWHHYXxW4";
    private String url_api_call = "https://www.googleapis.com/books/v1/volumes?q=subtle%20art&maxResults=20&key=AIzaSyDLMs9WTD8VQBajPlznYPCf5evWHHYXxW4";
    private static final int BOOK_DATA_LOADER_ID = 1;

    private BookDataAdapter mAdapter;
    private EditText mSearchBar;
    private Button mSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(BOOK_DATA_LOADER_ID,null,this);

        mSearchBar = (EditText) findViewById(R.id.my_search_bar);
        mSearchButton = (Button) findViewById(R.id.my_search_button);
        ListView listView = (ListView) findViewById(R.id.book_listview);
        mAdapter = new BookDataAdapter(this, new ArrayList<BookData>());
        listView.setAdapter(mAdapter);

    }

    @NonNull
    @Override
    public Loader<List<BookData>> onCreateLoader(int id, @Nullable Bundle args) {
        return new BookDataLoader(this,url_api_call);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<BookData>> loader, List<BookData> bookData) {
        mAdapter.clear();

        if(bookData != null && !bookData.isEmpty()){
            mAdapter.addAll(bookData);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<BookData>> loader) {
        mAdapter.clear();
    }
}
package com.samyak.booklisting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private final String API_KEY = "AIzaSyDLMs9WTD8VQBajPlznYPCf5evWHHYXxW4";
    private String url_api_call = "https://www.googleapis.com/books/v1/volumes?q=subtle%20art&maxResults=20&key=AIzaSyDLMs9WTD8VQBajPlznYPCf5evWHHYXxW4";
    private static final int BOOK_DATA_LOADER_ID = 1;
    private static final int BOOK_IMAGE_LOADER_ID = 2;

    private BookDataAdapter mAdapter;
    private EditText mSearchBar;
    private Button mSearchButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBar = (EditText) findViewById(R.id.my_search_bar);
        mSearchButton = (Button) findViewById(R.id.my_search_button);
        ListView listView = (ListView) findViewById(R.id.book_listview);
        mAdapter = new BookDataAdapter(this, new ArrayList<BookData>());
        listView.setAdapter(mAdapter);

        LoaderManager loaderManager = LoaderManager.getInstance(this);

        LoaderManager.LoaderCallbacks<List<BookData>> bookDataLoaderListener = new LoaderManager.LoaderCallbacks<List<BookData>>() {
            @NonNull
            @Override
            public Loader<List<BookData>> onCreateLoader(int id, @Nullable Bundle args) {
                return new BookDataLoader(MainActivity.this,url_api_call);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<BookData>> loader, List<BookData> bookData) {
                mAdapter.clear();

                if(bookData != null && !bookData.isEmpty()){
                    mAdapter.addAll(bookData);
                }

                mProgressBar = findViewById(R.id.progressbar);
                mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<BookData>> loader) {
                mAdapter.clear();
            }
        };
        loaderManager.initLoader(BOOK_DATA_LOADER_ID,null,bookDataLoaderListener);

    }
}
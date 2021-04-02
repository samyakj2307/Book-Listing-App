package com.samyak.booklisting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookDataRecyclerAdapter.BookDataRecyclerAdapterOnClickHandler {

    private static final int BOOK_DATA_LOADER_ID = 1;
    private final String API_KEY = "API-KEY";
    private final String mainUrl = String.format("https://www.googleapis.com/books/v1/volumes?maxResults=10&key=%s&q=", API_KEY);
    private String url_api_call;

    private EditText mSearchBar;
    private Button mSearchButton;
    private ProgressBar mProgressBar;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private BookDataRecyclerAdapter bookDataRecyclerAdapter;

    private LoaderManager loaderManager;
    private LoaderManager.LoaderCallbacks<List<BookData>> bookDataLoaderListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressbar);
        mSearchBar = findViewById(R.id.my_search_bar);
        mSearchButton = findViewById(R.id.my_search_button);

        recyclerView = findViewById(R.id.bookRecyclerView);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        bookDataRecyclerAdapter = new BookDataRecyclerAdapter(this);
        recyclerView.setAdapter(bookDataRecyclerAdapter);

        loaderManager = LoaderManager.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookDataRecyclerAdapter.setBookData(new ArrayList<BookData>());
                bookDataRecyclerAdapter.notifyDataSetChanged();
                String searchText = mSearchBar.getText().toString();
                url_api_call = mainUrl + searchText;
                mProgressBar.setVisibility(View.VISIBLE);
                loaderManager.restartLoader(BOOK_DATA_LOADER_ID, null, bookDataLoaderListener);

            }
        });

        mSearchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {

                    bookDataRecyclerAdapter.setBookData(new ArrayList<BookData>());
                    bookDataRecyclerAdapter.notifyDataSetChanged();
                    String searchText = mSearchBar.getText().toString();
                    url_api_call = mainUrl + searchText;
                    mProgressBar.setVisibility(View.VISIBLE);
                    loaderManager.restartLoader(BOOK_DATA_LOADER_ID, null, bookDataLoaderListener);

                    return true;
                }
                return false;
            }
        });

        bookDataLoaderListener = new LoaderManager.LoaderCallbacks<List<BookData>>() {
            @NonNull
            @Override
            public Loader<List<BookData>> onCreateLoader(int id, @Nullable Bundle args) {
                return new BookDataLoader(MainActivity.this, url_api_call);
            }

            @Override
            public void onLoadFinished(@NonNull Loader<List<BookData>> loader, List<BookData> bookData) {
                bookDataRecyclerAdapter.setBookData(new ArrayList<BookData>());

                if (bookData != null && !bookData.isEmpty()) {
                    bookDataRecyclerAdapter.setBookData((ArrayList<BookData>) bookData);
                } else {
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<BookData>> loader) {
                bookDataRecyclerAdapter.setBookData(new ArrayList<BookData>());
            }
        };
    }

    @Override
    public void OnClick(String Url) {
        Uri bookInfoUrl = Uri.parse(Url);
        Intent bookInfoIntent = new Intent(Intent.ACTION_VIEW, bookInfoUrl);
        startActivity(bookInfoIntent);
    }
}
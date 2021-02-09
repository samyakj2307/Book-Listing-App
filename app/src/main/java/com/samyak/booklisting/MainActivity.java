package com.samyak.booklisting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private final String API_KEY = "AIzaSyDLMs9WTD8VQBajPlznYPCf5evWHHYXxW4";
    private final String mainurl = String.format("https://www.googleapis.com/books/v1/volumes?maxResults=10&key=%s&q=", API_KEY);
    private String url_api_call;

    private static final int BOOK_DATA_LOADER_ID = 1;

    private BookDataAdapter mAdapter;
    private EditText mSearchBar;
    private Button mSearchButton;
    private TextView mEmptyStateTextView;
    private ProgressBar mProgressBar;
    private ListView listView;
    private LoaderManager loaderManager;
    private LoaderManager.LoaderCallbacks<List<BookData>> bookDataLoaderListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgressBar = findViewById(R.id.progressbar);
        mSearchBar = (EditText) findViewById(R.id.my_search_bar);
        mSearchButton = (Button) findViewById(R.id.my_search_button);
        listView = (ListView) findViewById(R.id.book_listview);
        mAdapter = new BookDataAdapter(this, new ArrayList<BookData>());
        listView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        loaderManager = LoaderManager.getInstance(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                String searchtext = mSearchBar.getText().toString();
                url_api_call = mainurl + searchtext;
                mProgressBar.setVisibility(View.VISIBLE);
                mEmptyStateTextView.setText("");
                loaderManager.restartLoader(BOOK_DATA_LOADER_ID, null, bookDataLoaderListener);

            }
        });

        mSearchBar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)) {

                    mAdapter.clear();
                    mAdapter.notifyDataSetChanged();
                    String searchtext = mSearchBar.getText().toString();
                    url_api_call = mainurl + searchtext;
                    mProgressBar.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setText("");
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
                mAdapter.clear();

                if (bookData != null && !bookData.isEmpty()) {
                    mAdapter.addAll(bookData);
                } else {
                    mEmptyStateTextView.setText(R.string.nobooks_str);
                }
                mProgressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onLoaderReset(@NonNull Loader<List<BookData>> loader) {
                mAdapter.clear();
            }
        };

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                BookData currentbookdata = mAdapter.getItem(i);
                Uri bookinfourl = Uri.parse(currentbookdata.getmBookInfoLink());
                Intent bookinfointent = new Intent(Intent.ACTION_VIEW, bookinfourl);
                startActivity(bookinfointent);
            }
        });

    }
}
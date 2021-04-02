package com.samyak.booklisting;

import android.net.Uri;

public class BookData {

    private final String mBookTitle;
    private final String mBookAuthor;
    private final Uri mBookThumbnailUri;
    private final String mBookInfoLink;

    public BookData(String Title, String Author, Uri ThumbnailUri, String BookInfoLink) {
        mBookTitle = Title;
        mBookAuthor = Author;
        mBookThumbnailUri = ThumbnailUri;
        mBookInfoLink = BookInfoLink;

    }

    public String getmBookAuthor() {
        return mBookAuthor;
    }

    public String getmBookTitle() {
        return mBookTitle;
    }

    public Uri getmBookThumbnailUri() {
        return mBookThumbnailUri;
    }

    public String getmBookInfoLink() {
        return mBookInfoLink;
    }
}

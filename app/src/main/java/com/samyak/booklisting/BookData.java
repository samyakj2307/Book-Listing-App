package com.samyak.booklisting;

import android.graphics.Bitmap;

public class BookData {

    private final String mBookTitle;
    private final String mBookAuthor;
    private Bitmap mBookThumbnail;
    private String mBookInfoLink;

    public BookData(String Title, String Author, Bitmap PhotoBitmap, String BookInfoLink) {
        mBookTitle = Title;
        mBookAuthor = Author;
        mBookThumbnail = PhotoBitmap;
        mBookInfoLink = BookInfoLink;

    }

    public String getmBookAuthor() {
        return mBookAuthor;
    }

    public String getmBookTitle() {
        return mBookTitle;
    }


    public Bitmap getmBookThumbnail() {
        return mBookThumbnail;
    }

    public String getmBookInfoLink() {
        return mBookInfoLink;
    }
}

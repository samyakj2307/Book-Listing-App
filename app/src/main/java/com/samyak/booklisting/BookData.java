package com.samyak.booklisting;

import android.graphics.Bitmap;

public class BookData {

    private final String mBookTitle;
    private final String mBookAuthor;
    private Bitmap mBookThumbnail;

    public BookData(String Title,String Author,Bitmap PhotoBitmap){
        mBookTitle = Title;
        mBookAuthor = Author;
        mBookThumbnail = PhotoBitmap;
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
}

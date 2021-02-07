package com.samyak.booklisting;

public class BookData {

    private final String mBookTitle;
    private final String mBookAuthor;

    public BookData(String Title,String Author){
        mBookTitle = Title;
        mBookAuthor = Author;
    }

    public String getmBookAuthor() {
        return mBookAuthor;
    }

    public String getmBookTitle() {
        return mBookTitle;
    }
}

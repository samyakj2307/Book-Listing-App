package com.samyak.booklisting;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookDataAdapter extends ArrayAdapter<BookData> {

    public BookDataAdapter(Activity context, ArrayList<BookData> bookData){
        super(context,0,bookData);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listViewItem = convertView;
        if(listViewItem==null){
            listViewItem = LayoutInflater.from(getContext()).inflate(R.layout.booklist_item,parent,false);
        }


        BookData currentBookData = getItem(position);

        TextView bookTitle =listViewItem.findViewById(R.id.title_of_book);
        bookTitle.setText(currentBookData.getmBookTitle());

        TextView bookAuthor =listViewItem.findViewById(R.id.author_of_book);
        bookAuthor.setText(currentBookData.getmBookAuthor());

        if(currentBookData.getmBookThumbnail()!=null) {
            ImageView bookThumbnail = listViewItem.findViewById(R.id.bookthumbnail);
            bookThumbnail.setImageBitmap(currentBookData.getmBookThumbnail());
        }

        return listViewItem;
    }
}

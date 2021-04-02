package com.samyak.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class BookDataRecyclerAdapter extends RecyclerView.Adapter<BookDataRecyclerAdapter.BookDataViewHolder> {

    private final BookDataRecyclerAdapterOnClickHandler mClickHandler;
    Context context;
    private ArrayList<BookData> bookData = new ArrayList<BookData>();

    public BookDataRecyclerAdapter(Context context, BookDataRecyclerAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
        this.context = context;
    }

    @NonNull
    @Override
    public BookDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booklist_item, parent, false);
        return new BookDataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookDataViewHolder holder, int position) {
        holder.getBookTitle().setText(bookData.get(position).getmBookTitle());
        holder.getBookAuthor().setText(bookData.get(position).getmBookAuthor());

        Glide.with(context)
                .load(bookData.get(position).getmBookThumbnailUri())
                .into(holder.getBookThumbnail());

    }

    @Override
    public int getItemCount() {
        return bookData.size();
    }

    public void setBookData(ArrayList<BookData> bookDataArrayList) {
        this.bookData = bookDataArrayList;
        notifyDataSetChanged();
    }

    public interface BookDataRecyclerAdapterOnClickHandler {
        void OnClick(String Url);
    }

    public class BookDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView bookTitle;
        TextView bookAuthor;
        ImageView bookThumbnail;

        public BookDataViewHolder(@NonNull View itemView) {
            super(itemView);

            bookTitle = itemView.findViewById(R.id.title_of_book);
            bookAuthor = itemView.findViewById(R.id.author_of_book);
            bookThumbnail = itemView.findViewById(R.id.bookthumbnail);

            itemView.setOnClickListener(this);
        }

        public TextView getBookTitle() {
            return bookTitle;
        }

        public TextView getBookAuthor() {
            return bookAuthor;
        }

        public ImageView getBookThumbnail() {
            return bookThumbnail;
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            String bookUrl = bookData.get(position).getmBookInfoLink();
            mClickHandler.OnClick(bookUrl);
        }
    }

}

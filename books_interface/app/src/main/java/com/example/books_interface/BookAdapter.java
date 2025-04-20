package com.example.books_interface;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

class BookAdapter extends ArrayAdapter<Book> {
    private final LayoutInflater inflater;
    private final int layout;
    private final ArrayList<Book> bookList;

    BookAdapter(Context context, int resource, ArrayList<Book> books) {
        super(context, resource, books);
        this.bookList = books;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Book book = bookList.get(position);

        viewHolder.nameView.setText(book.getName());
        viewHolder.authorView.setText(book.getAuthor());

        return convertView;
    }
    private static class ViewHolder {
        final TextView nameView, authorView;
        ViewHolder(View view){
            nameView = view.findViewById(R.id.book_name);
            authorView = view.findViewById(R.id.book_author);
        }
    }
}

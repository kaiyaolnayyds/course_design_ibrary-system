package com.example.booktracker.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.booktracker.R;
import com.example.booktracker.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    private final List<Book> list = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = list.get(position);
        Glide.with(holder.itemView).load(book.getUrl()).into(holder.ivBookImg);
        holder.tvBookName.setText(book.getName());
        holder.tvBookAuthor.setText(book.getAuthor());
        holder.tvBookDesc.setText(book.getDesc());
        holder.tvBookNum.setText(String.format("剩余:%d      总数:%d", book.getRemain(), book.getTotal()));
        holder.tvBookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemDetailClick(holder.getAdapterPosition(), book);
                }
            }
        });
        holder.tvBookBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemBorrowClick(holder.getAdapterPosition(), book);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Book> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {

        void onItemDetailClick(int position, Book book);

        void onItemBorrowClick(int position, Book book);

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivBookImg;

        public TextView tvBookName, tvBookAuthor, tvBookDesc, tvBookNum, tvBookDetail, tvBookBorrow;

        public ViewHolder(View itemView) {
            super(itemView);
            ivBookImg = itemView.findViewById(R.id.iv_book_img);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            tvBookAuthor = itemView.findViewById(R.id.tv_book_author);
            tvBookDesc = itemView.findViewById(R.id.tv_book_desc);
            tvBookNum = itemView.findViewById(R.id.tv_book_num);
            tvBookDetail = itemView.findViewById(R.id.tv_book_detail);
            tvBookBorrow = itemView.findViewById(R.id.tv_book_borrow);
        }
    }
}

package com.example.booktracker.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booktracker.R;
import com.example.booktracker.entity.Borrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BorrowAdapter extends RecyclerView.Adapter<BorrowAdapter.ViewHolder> {

    private final List<Borrow> list = new ArrayList<>();

    private OnItemClickListener onItemClickListener;

    @NonNull
    @Override
    public BorrowAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_borrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BorrowAdapter.ViewHolder holder, int position) {
        Borrow borrow = list.get(position);
        Context context = holder.itemView.getContext();
        holder.tvBookName.setText(borrow.getBookName());
        holder.tvBorrowDate.setText(borrow.getBorrowDate());
        if (TextUtils.isEmpty(borrow.getReturnDate())) {
            holder.tvReturnDate.setText("归还");
            holder.tvReturnDate.setTextColor(ContextCompat.getColor(context, R.color.main_light));
        } else {
            holder.tvReturnDate.setText(borrow.getReturnDate());
            holder.tvReturnDate.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        holder.tvReturnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemBorrowClick(holder.getAdapterPosition(), borrow);
                }
            }
        });
    }

    public void setList(List<Borrow> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public List<Borrow> getList() {
        return list;
    }

    public interface OnItemClickListener {
        void onItemBorrowClick(int position, Borrow borrow);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvBookName, tvBorrowDate, tvReturnDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            tvBorrowDate = itemView.findViewById(R.id.tv_borrow_date);
            tvReturnDate = itemView.findViewById(R.id.tv_return_date);
        }
    }
}

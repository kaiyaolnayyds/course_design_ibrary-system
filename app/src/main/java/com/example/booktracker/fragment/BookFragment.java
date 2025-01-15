package com.example.booktracker.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booktracker.R;
import com.example.booktracker.activity.BookDetailActivity;
import com.example.booktracker.adapter.BookAdapter;
import com.example.booktracker.db.BookDB;
import com.example.booktracker.db.BorrowDB;
import com.example.booktracker.db.BusinessResult;
import com.example.booktracker.entity.Book;
import com.example.booktracker.entity.User;
import com.example.booktracker.utils.CurrentUserUtils;

public class BookFragment extends Fragment {

    private RecyclerView rvBook;

    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        bindView(view);
        initView();
        return view;
    }

    private void bindView(View view) {
        rvBook = view.findViewById(R.id.rv_book);
    }

    private void initView() {
        currentUser = CurrentUserUtils.getCurrentUser();
        BookAdapter bookAdapter = new BookAdapter();
        bookAdapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemDetailClick(int position, Book book) {
                Intent intent = new Intent(getContext(), BookDetailActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }

            @Override
            public void onItemBorrowClick(int position, Book book) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("确定借阅该书籍吗?");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //借阅
                        BusinessResult<Void> result = BorrowDB.borrowBook(currentUser.getId(), book.getId());
                        Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                        if(!result.isSuccess()){
                            return;
                        }
                        //更新剩余数量
                        book.setRemain(book.getRemain() - 1);
                        bookAdapter.notifyItemChanged(position);
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
        rvBook.setAdapter(bookAdapter);
        rvBook.setLayoutManager(new LinearLayoutManager(getContext()));

        bookAdapter.setList(BookDB.getAllBooks().getData());
    }
}

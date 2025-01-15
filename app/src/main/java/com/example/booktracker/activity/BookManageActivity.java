package com.example.booktracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booktracker.R;
import com.example.booktracker.adapter.BookManageAdapter;
import com.example.booktracker.db.BookDB;
import com.example.booktracker.entity.Book;

public class BookManageActivity extends AppCompatActivity {

    private RecyclerView rvBookManage;

    private TextView tvAdd;

    private BookManageAdapter adapter;

    private ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_manage);
        //绑定控件
        bindView();
        //初始化控件
        initView();
    }

    private void bindView() {
        rvBookManage = findViewById(R.id.rv_book_manage);
        tvAdd = findViewById(R.id.tv_add);
        ivBack = findViewById(R.id.iv_back);
    }

    private void initView() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookManageActivity.this, BookEditActivity.class);
                startActivity(intent);
            }
        });

         adapter = new BookManageAdapter();
        adapter.setOnItemClickListener(new BookManageAdapter.OnItemClickListener() {
            @Override
            public void onItemDetailClick(int position, Book book) {
                Intent intent = new Intent(BookManageActivity.this, BookDetailActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }

            @Override
            public void onItemEditClick(int position, Book book) {
                Intent intent = new Intent(BookManageActivity.this, BookEditActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });
        rvBookManage.setAdapter(adapter);
        rvBookManage.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.setList(BookDB.getAllBooks().getData());
    }
}

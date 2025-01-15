package com.example.booktracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.booktracker.R;
import com.example.booktracker.db.BookDB;
import com.example.booktracker.db.BusinessResult;
import com.example.booktracker.entity.Book;
import com.example.booktracker.utils.AlbumUtils;

public class BookEditActivity extends AppCompatActivity {

    private ImageView ivBack, ivBookImg;

    private TextView tvTitle, tvSelect, tvDelete;

    private EditText etBookName, etBookAuthor, etBookDesc, etBookTotal;

    private Button btnSubmit;

    private Book book;

    private boolean isEdit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_edit);
        //绑定控件
        bindView();
        //初始化数据
        initData();
        //初始化控件
        initView();
    }

    private void bindView() {
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        ivBookImg = findViewById(R.id.iv_book_img);
        tvSelect = findViewById(R.id.tv_select);
        etBookName = findViewById(R.id.et_book_name);
        etBookAuthor = findViewById(R.id.et_book_author);
        etBookDesc = findViewById(R.id.et_book_desc);
        etBookTotal = findViewById(R.id.et_book_total);
        btnSubmit = findViewById(R.id.btn_submit);
        tvDelete = findViewById(R.id.tv_delete);
    }

    private void initData() {
        book = (Book) getIntent().getSerializableExtra("book");
        if (book != null) {
            Glide.with(this).load(book.getUrl()).into(ivBookImg);
            etBookName.setText(book.getName());
            etBookAuthor.setText(book.getAuthor());
            etBookDesc.setText(book.getDesc());
            etBookTotal.setText(String.valueOf(book.getTotal()));
            tvDelete.setVisibility(View.VISIBLE);
            isEdit = true;
        } else {
            book = new Book();
            tvDelete.setVisibility(View.GONE);
            isEdit = false;
        }
    }

    private void initView() {
        tvTitle.setText(isEdit ? "编辑图书" : "添加图书");
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivBookImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择图片
                AlbumUtils.openAlbum(BookEditActivity.this);
            }
        });
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择图片
                AlbumUtils.openAlbum(BookEditActivity.this);
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                book.setName(etBookName.getText().toString());
                book.setAuthor(etBookAuthor.getText().toString());
                book.setDesc(etBookDesc.getText().toString());
                book.setTotal(Integer.parseInt(etBookTotal.getText().toString()));
                BusinessResult<Book> result;
                if (isEdit) {
                    //编辑图书
                    result = BookDB.updateBook(book);
                } else {
                    //添加图书
                    result = BookDB.addBook(book);
                }
                Toast.makeText(BookEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.isSuccess()) {
                    finish();
                }
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BusinessResult<Void> result = BookDB.deleteBook(book.getId());
                Toast.makeText(BookEditActivity.this, result.getMessage(), Toast.LENGTH_SHORT).show();
                if (result.isSuccess()) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //获取图片路径，显示图片
            if (requestCode == AlbumUtils.OPEN_ALBUM_REQUEST_CODE) {
                String path = AlbumUtils.getImagePath(data);
                Glide.with(this).load(path).into(ivBookImg);
                book.setUrl(path);
            }
        }
    }
}

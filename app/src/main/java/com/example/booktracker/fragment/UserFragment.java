package com.example.booktracker.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booktracker.R;
import com.example.booktracker.adapter.BorrowAdapter;
import com.example.booktracker.db.BorrowDB;
import com.example.booktracker.db.BusinessResult;
import com.example.booktracker.entity.Borrow;
import com.example.booktracker.entity.User;
import com.example.booktracker.utils.CurrentUserUtils;

import java.util.List;

public class UserFragment extends Fragment {
    private Button btnSearch;
    private EditText etBookName;

    private RecyclerView rvBorrow;

    private TextView tvUsername, tvLogout;
    private RadioGroup rgType;

    private BorrowAdapter borrowAdapter;

    private User currentUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        bindView(view);
        return view;
    }

    private void bindView(View view) {
        tvUsername = view.findViewById(R.id.tv_username);

        rvBorrow = view.findViewById(R.id.rv_borrow);
        btnSearch = view.findViewById(R.id.btn_search);
        etBookName = view.findViewById(R.id.et_book_name);
        rgType = view.findViewById(R.id.rg_type);
        tvLogout = view.findViewById(R.id.tv_logout);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 获取当前用户
        currentUser = CurrentUserUtils.getCurrentUser();
        tvUsername.setText(currentUser.getUsername());

        borrowAdapter = new BorrowAdapter();
        borrowAdapter.setOnItemClickListener(new BorrowAdapter.OnItemClickListener() {
            @Override
            public void onItemBorrowClick(int position, Borrow borrow) {
                if (TextUtils.isEmpty(borrow.getReturnDate())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("提示");
                    builder.setMessage("确定要归还吗？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // 归还
                            BusinessResult<String> result = BorrowDB.returnBook(borrow.getId(), borrow.getBookId());
                            if (result.isSuccess()) {
                                borrowAdapter.getList().get(position).setReturnDate(result.getData());
                                borrowAdapter.notifyItemChanged(position);
                            } else {
                                Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();
                }
            }
        });
        rvBorrow.setAdapter(borrowAdapter);
        rvBorrow.setLayoutManager(new LinearLayoutManager(getContext()));
        // 退出登录
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("提示");
                builder.setMessage("确定要退出登录吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.show();
            }
        });
        // 查询
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
        search();
    }

    private void search() {
        int checkedRadioButtonId = rgType.getCheckedRadioButtonId();
        // 0 全部 1 借阅中 2 已归还
        int type = 0;
        if (checkedRadioButtonId == R.id.rb_borrowing) {
            type = 1;
        } else if (checkedRadioButtonId == R.id.rb_returned) {
            type = 2;
        }
        // 查询借阅列表
        BusinessResult<List<Borrow>> result = BorrowDB.queryBorrowList(currentUser.getId(), etBookName.getText().toString(), type);
        if (result.isSuccess()) {
            List<Borrow> list = result.getData();
            borrowAdapter.setList(list);
        } else {
            Toast.makeText(getContext(), result.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}

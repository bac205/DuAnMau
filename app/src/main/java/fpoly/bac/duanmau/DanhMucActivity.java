package fpoly.bac.duanmau;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.DanhMucAdapter;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.DanhMuc;

public class DanhMucActivity extends AppCompatActivity {
    private RecyclerView rcDanhMuc;
    private DanhMucAdapter adapter;
    private ArrayList<DanhMuc> listDM;
    private ArrayList<DanhMuc> fullListDM;
    private DbHelper dbHelper;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhmuc);
        
        Toolbar toolbar = findViewById(R.id.toolbarDanhMuc);
        setSupportActionBar(toolbar);

        rcDanhMuc = findViewById(R.id.rcDanhMuc);
        edtSearch = findViewById(R.id.edtSearch);
        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        dbHelper = new DbHelper(this);

        // Load danh mục từ database
        fullListDM = dbHelper.getAllDanhMucQL();
        listDM = new ArrayList<>(fullListDM);
        
        // Tạo adapter và set layout
        adapter = new DanhMucAdapter(this, listDM);
        rcDanhMuc.setLayoutManager(new LinearLayoutManager(this));
        rcDanhMuc.setAdapter(adapter);

        // Thêm mới
        btnAdd.setOnClickListener(v -> showAddDialog());

        // Tìm kiếm theo tên danh mục
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().toLowerCase().trim();
                filterDanhMuc(query);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void filterDanhMuc(String query) {
        listDM.clear();
        if (query.isEmpty()) {
            listDM.addAll(fullListDM);
        } else {
            for (DanhMuc dm : fullListDM) {
                if (dm.getTen().toLowerCase().contains(query)) {
                    listDM.add(dm);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void showAddDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_danhmuc, null);
        
        EditText edtMaDM = dialogView.findViewById(R.id.edtMaDM);
        EditText edtTenDM = dialogView.findViewById(R.id.edtTenDM);
        EditText edtHinhAnh = dialogView.findViewById(R.id.edtHinhAnh);

        builder.setView(dialogView)
                .setTitle("Thêm Danh Mục Mới")
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String maDM = edtMaDM.getText().toString().trim();
                    String tenDM = edtTenDM.getText().toString().trim();
                    String hinhAnh = edtHinhAnh.getText().toString().trim();

                    if (maDM.isEmpty() || tenDM.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DanhMuc newDM = new DanhMuc(maDM, tenDM, hinhAnh);
                    if (dbHelper.insertDanhMucQL(newDM) > 0) {
                        Toast.makeText(this, "Thêm danh mục thành công", Toast.LENGTH_SHORT).show();
                        refreshData();
                    } else {
                        Toast.makeText(this, "Thêm danh mục thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void refreshData() {
        fullListDM = dbHelper.getAllDanhMucQL();
        listDM.clear();
        listDM.addAll(fullListDM);
        adapter.notifyDataSetChanged();
    }
}

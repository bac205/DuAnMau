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

import fpoly.bac.duanmau.adapter.SanPhamAdapter;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.Sanpham;

public class SanPhamActivity extends AppCompatActivity {
    private RecyclerView rcSanPham;
    private SanPhamAdapter adapter;
    private ArrayList<Sanpham> listSP;
    private ArrayList<Sanpham> fullListSP;
    private DbHelper dbHelper;
    private EditText edtSearch;
    private String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham);
        Toolbar toolbar = findViewById(R.id.toolbarSanPham);
        setSupportActionBar(toolbar);

        rcSanPham = findViewById(R.id.rcSanPham);
        edtSearch = findViewById(R.id.edtSearch);
        FloatingActionButton btnAdd = findViewById(R.id.btnAdd);
        dbHelper = new DbHelper(this);

        // Debug database
        dbHelper.debugDatabase();
        
        // Load sản phẩm từ database
        fullListSP = dbHelper.getAllSanPham();
        listSP = new ArrayList<>(fullListSP);
        
        // Log chi tiết
        android.util.Log.d("SanPhamActivity", "=== LOAD SẢN PHẨM ===");
        android.util.Log.d("SanPhamActivity", "fullListSP size: " + fullListSP.size());
        android.util.Log.d("SanPhamActivity", "listSP size: " + listSP.size());
        
        // Log tên các sản phẩm đầu tiên
        for (int i = 0; i < Math.min(fullListSP.size(), 3); i++) {
            android.util.Log.d("SanPhamActivity", "SP " + i + ": " + fullListSP.get(i).getTenSP() + " - " + fullListSP.get(i).getGia());
        }
        
        // Nếu không có sản phẩm, thêm sản phẩm mẫu
        if (fullListSP.isEmpty()) {
            android.util.Log.d("SanPhamActivity", "Không có sản phẩm, thêm sản phẩm mẫu...");
            dbHelper.addSampleProductsIfEmpty();
            fullListSP = dbHelper.getAllSanPham();
            listSP = new ArrayList<>(fullListSP);
            android.util.Log.d("SanPhamActivity", "Sau khi thêm mẫu, số sản phẩm: " + fullListSP.size());
        }
        
        // Tạo adapter và set layout
        adapter = new SanPhamAdapter(this, listSP);
        rcSanPham.setLayoutManager(new LinearLayoutManager(this));
        rcSanPham.setAdapter(adapter);
        
        // Tạo adapter và set layout
        adapter = new SanPhamAdapter(this, listSP);
        rcSanPham.setLayoutManager(new LinearLayoutManager(this));
        rcSanPham.setAdapter(adapter);
        
        android.util.Log.d("SanPhamActivity", "=== HOÀN THÀNH SETUP ===");
        android.util.Log.d("SanPhamActivity", "Adapter item count: " + adapter.getItemCount());

        // Thêm mới
        btnAdd.setOnClickListener(v -> showAddDialog());





        // Tìm kiếm theo tên sản phẩm
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                currentQuery = s == null ? "" : s.toString();
                applyFilter(currentQuery);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Sửa/Xoá từ adapter
        adapter.setOnItemActionListener(new SanPhamAdapter.OnItemActionListener() {
            @Override
            public void onDelete(Sanpham sp, int position) {
                confirmDelete(sp, position);
            }

            @Override
            public void onEdit(Sanpham sp, int position) {
                showEditDialog(sp, position);
            }


        });
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sanpham, null);
        EditText edtTen = view.findViewById(R.id.edtTenSanPham);
        EditText edtGia = view.findViewById(R.id.edtGiaSanPham);

        new AlertDialog.Builder(this)
                .setTitle("Thêm sản phẩm")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    String giaStr = edtGia.getText().toString().trim();
                    if (ten.isEmpty() || giaStr.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double gia;
                    try {
                        gia = Double.parseDouble(giaStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Sanpham sp = new Sanpham(0, ten, gia, "img_product");
                    long id = dbHelper.insertSanPham(sp);
                    if (id > 0) {
                        sp.setMaSP((int) id);
                        fullListSP.add(0, sp);
                        applyFilter(currentQuery);
                        rcSanPham.scrollToPosition(0);
                        Toast.makeText(this, "Đã thêm sản phẩm", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog(Sanpham sp, int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_sanpham, null);
        EditText edtTen = view.findViewById(R.id.edtTenSanPham);
        EditText edtGia = view.findViewById(R.id.edtGiaSanPham);
        edtTen.setText(sp.getTenSP());
        edtGia.setText(String.valueOf(sp.getGia()));

        new AlertDialog.Builder(this)
                .setTitle("Sửa sản phẩm")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    String giaStr = edtGia.getText().toString().trim();
                    if (ten.isEmpty() || giaStr.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double gia;
                    try {
                        gia = Double.parseDouble(giaStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    sp.setTenSP(ten);
                    sp.setGia(gia);
                    sp.setHinhAnh("img_product");
                    int rows = dbHelper.updateSanPham(sp);
                    if (rows > 0) {
                        // cập nhật trong danh sách đầy đủ
                        for (int i = 0; i < fullListSP.size(); i++) {
                            if (fullListSP.get(i).getMaSP() == sp.getMaSP()) {
                                fullListSP.set(i, sp);
                                break;
                            }
                        }
                        applyFilter(currentQuery);
                        Toast.makeText(this, "Đã cập nhật", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void confirmDelete(Sanpham sp, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xoá sản phẩm")
                .setMessage("Bạn có chắc muốn xoá sản phẩm này?")
                .setPositiveButton("Xoá", (dialog, which) -> {
                    int rows = dbHelper.deleteSanPham(sp.getMaSP());
                    if (rows > 0) {
                        // xoá trong danh sách đầy đủ theo id
                        for (int i = 0; i < fullListSP.size(); i++) {
                            if (fullListSP.get(i).getMaSP() == sp.getMaSP()) {
                                fullListSP.remove(i);
                                break;
                            }
                        }
                        applyFilter(currentQuery);
                        Toast.makeText(this, "Đã xoá", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void applyFilter(String query) {
        String q = query == null ? "" : query.trim().toLowerCase();
        listSP.clear();
        if (q.isEmpty()) {
            listSP.addAll(fullListSP);
        } else {
            for (Sanpham item : fullListSP) {
                String name = item.getTenSP() == null ? "" : item.getTenSP();
                if (name.toLowerCase().contains(q)) {
                    listSP.add(item);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }


}

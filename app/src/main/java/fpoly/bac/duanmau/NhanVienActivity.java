package fpoly.bac.duanmau;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.NhanVienAdapter;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.NhanVien;

public class NhanVienActivity extends AppCompatActivity {
    private RecyclerView rcNhanVien;
    private NhanVienAdapter adapter;
    private ArrayList<NhanVien> list;
    private int nextIndex = 5; // tiếp theo sau NV004
    private com.google.android.material.floatingactionbutton.FloatingActionButton btnAddNV;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhanvien);

        Toolbar toolbar = findViewById(R.id.toolbarNhanVien);
        setSupportActionBar(toolbar);

        rcNhanVien = findViewById(R.id.rcNhanVien);
        rcNhanVien.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DbHelper(this);

        // Tạo dữ liệu mẫu theo yêu cầu: 4 nhân viên với ảnh chung img_nhanvien
        list = dbHelper.getAllNhanVienQL();
        // Xác định nextIndex dựa trên danh sách hiện có
        nextIndex = 1;
        for (NhanVien nv : list) {
            try {
                if (nv.getMaNV() != null && nv.getMaNV().startsWith("NV")) {
                    int num = Integer.parseInt(nv.getMaNV().substring(2));
                    if (num >= nextIndex) nextIndex = num + 1;
                }
            } catch (Exception ignored) {}
        }

        adapter = new NhanVienAdapter(this, list);
        rcNhanVien.setAdapter(adapter);

        btnAddNV = findViewById(R.id.btnAddNV);
        btnAddNV.setOnClickListener(v -> showAddDialog());

        adapter.setOnItemActionListener(new NhanVienAdapter.OnItemActionListener() {
            @Override
            public void onEdit(NhanVien nv, int position) {
                showEditDialog(nv, position);
            }

            @Override
            public void onDelete(NhanVien nv, int position) {
                confirmDelete(nv, position);
            }
        });
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_nhanvien, null);
        EditText edtTen = view.findViewById(R.id.edtTenNV);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChiNV);
        EditText edtLuong = view.findViewById(R.id.edtLuongNV);
        EditText edtChucVu = view.findViewById(R.id.edtChucVuNV);

        new AlertDialog.Builder(this)
                .setTitle("Thêm nhân viên")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    String diaChi = edtDiaChi.getText().toString().trim();
                    String luongStr = edtLuong.getText().toString().trim();
                    String chucVu = edtChucVu.getText().toString().trim();
                    if (ten.isEmpty() || diaChi.isEmpty() || luongStr.isEmpty() || chucVu.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double luong;
                    try {
                        luong = Double.parseDouble(luongStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Lương không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String ma = String.format("NV%03d", nextIndex++);
                    NhanVien nv = new NhanVien(ma, ten, diaChi, luong, chucVu, "img_nhanvien");
                    long row = dbHelper.insertNhanVienQL(nv);
                    if (row > 0) {
                        list.add(0, nv);
                        adapter.notifyItemInserted(0);
                        rcNhanVien.scrollToPosition(0);
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog(NhanVien nv, int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_nhanvien, null);
        EditText edtTen = view.findViewById(R.id.edtTenNV);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChiNV);
        EditText edtLuong = view.findViewById(R.id.edtLuongNV);
        EditText edtChucVu = view.findViewById(R.id.edtChucVuNV);

        edtTen.setText(nv.getTen());
        edtDiaChi.setText(nv.getDiaChi());
        edtLuong.setText(String.valueOf(nv.getLuong()));
        edtChucVu.setText(nv.getChucVu());

        new AlertDialog.Builder(this)
                .setTitle("Sửa nhân viên " + nv.getMaNV())
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    String diaChi = edtDiaChi.getText().toString().trim();
                    String luongStr = edtLuong.getText().toString().trim();
                    String chucVu = edtChucVu.getText().toString().trim();
                    if (ten.isEmpty() || diaChi.isEmpty() || luongStr.isEmpty() || chucVu.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    double luong;
                    try {
                        luong = Double.parseDouble(luongStr);
                    } catch (NumberFormatException e) {
                        Toast.makeText(this, "Lương không hợp lệ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    nv.setTen(ten);
                    nv.setDiaChi(diaChi);
                    nv.setLuong(luong);
                    nv.setChucVu(chucVu);
                    nv.setHinhAnh("img_nhanvien");
                    int rows = dbHelper.updateNhanVienQL(nv);
                    if (rows > 0) {
                        list.set(position, nv);
                        adapter.notifyItemChanged(position);
                    } else {
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void confirmDelete(NhanVien nv, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xoá nhân viên")
                .setMessage("Bạn có chắc muốn xoá " + nv.getMaNV() + "?")
                .setPositiveButton("Xoá", (dialog, which) -> {
                    int rows = dbHelper.deleteNhanVienQL(nv.getMaNV());
                    if (rows > 0) {
                        list.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, list.size() - position);
                    } else {
                        Toast.makeText(this, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}



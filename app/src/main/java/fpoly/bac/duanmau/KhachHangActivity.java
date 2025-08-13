package fpoly.bac.duanmau;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.KhachHangAdapter;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.KhachHang;

public class KhachHangActivity extends AppCompatActivity {
    private RecyclerView rcKhachHang;
    private KhachHangAdapter adapter;
    private ArrayList<KhachHang> list;
    private com.google.android.material.floatingactionbutton.FloatingActionButton btnAddKH;
    private DbHelper dbHelper;
    private int nextIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);
        Toolbar toolbar = findViewById(R.id.toolbarKhachHang);
        setSupportActionBar(toolbar);

        rcKhachHang = findViewById(R.id.rcKhachHang);
        rcKhachHang.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DbHelper(this);

        list = dbHelper.getAllKhachHangQL();
        for (KhachHang kh : list) {
            try {
                if (kh.getMaKH() != null && kh.getMaKH().startsWith("KH")) {
                    int num = Integer.parseInt(kh.getMaKH().substring(2));
                    if (num >= nextIndex) nextIndex = num + 1;
                }
            } catch (Exception ignored) {}
        }

        adapter = new KhachHangAdapter(this, list);
        rcKhachHang.setAdapter(adapter);

        btnAddKH = findViewById(R.id.btnAddKH);
        btnAddKH.setOnClickListener(v -> showAddDialog());

        adapter.setOnItemActionListener(new KhachHangAdapter.OnItemActionListener() {
            @Override
            public void onEdit(KhachHang kh, int position) {
                showEditDialog(kh, position);
            }

            @Override
            public void onDelete(KhachHang kh, int position) {
                confirmDelete(kh, position);
            }
        });
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_khachhang, null);
        EditText edtTen = view.findViewById(R.id.edtTenKH);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChiKH);
        EditText edtEmail = view.findViewById(R.id.edtEmailKH);
        EditText edtSdt = view.findViewById(R.id.edtSdtKH);

        new AlertDialog.Builder(this)
                .setTitle("Thêm khách hàng")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    String diaChi = edtDiaChi.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String sdt = edtSdt.getText().toString().trim();
                    if (ten.isEmpty() || diaChi.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String ma = String.format("KH%03d", nextIndex++);
                    KhachHang kh = new KhachHang(ma, ten, diaChi, email, sdt, "img_kh");
                    long row = dbHelper.insertKhachHangQL(kh);
                    if (row > 0) {
                        list.add(0, kh);
                        adapter.notifyItemInserted(0);
                        rcKhachHang.scrollToPosition(0);
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog(KhachHang kh, int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_khachhang, null);
        EditText edtTen = view.findViewById(R.id.edtTenKH);
        EditText edtDiaChi = view.findViewById(R.id.edtDiaChiKH);
        EditText edtEmail = view.findViewById(R.id.edtEmailKH);
        EditText edtSdt = view.findViewById(R.id.edtSdtKH);
        edtTen.setText(kh.getTen());
        edtDiaChi.setText(kh.getDiaChi());
        edtEmail.setText(kh.getEmail());
        edtSdt.setText(kh.getSdt());

        new AlertDialog.Builder(this)
                .setTitle("Sửa khách hàng " + kh.getMaKH())
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    String diaChi = edtDiaChi.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();
                    String sdt = edtSdt.getText().toString().trim();
                    if (ten.isEmpty() || diaChi.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    kh.setTen(ten);
                    kh.setDiaChi(diaChi);
                    kh.setEmail(email);
                    kh.setSdt(sdt);
                    kh.setHinhAnh("img_kh");
                    int rows = dbHelper.updateKhachHangQL(kh);
                    if (rows > 0) {
                        list.set(position, kh);
                        adapter.notifyItemChanged(position);
                    } else {
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void confirmDelete(KhachHang kh, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xoá khách hàng")
                .setMessage("Bạn có chắc muốn xoá " + kh.getMaKH() + "?")
                .setPositiveButton("Xoá", (dialog, which) -> {
                    int rows = dbHelper.deleteKhachHangQL(kh.getMaKH());
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









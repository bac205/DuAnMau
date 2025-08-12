package fpoly.bac.duanmau;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.GioHangAdapter;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.GioHang;
import fpoly.bac.duanmau.model.HoaDon;
import fpoly.bac.duanmau.model.KhachHang;

public class GioHangActivity extends AppCompatActivity {
    private RecyclerView rcGioHang;
    private GioHangAdapter adapter;
    private ArrayList<GioHang> listGioHang;
    private TextView tvKhachHang, tvTongTien;
    private Button btnChonKH, btnThanhToan;
    private DbHelper dbHelper;
    private KhachHang khachHangSelected = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        Toolbar toolbar = findViewById(R.id.toolbarGioHang);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
        listGioHang = dbHelper.getAllCartItems();

        initViews();
        setupRecyclerView();
        updateTongTien();
        setupListeners();
    }

    private void initViews() {
        rcGioHang = findViewById(R.id.rcGioHang);
        tvKhachHang = findViewById(R.id.tvKhachHang);
        tvTongTien = findViewById(R.id.tvTongTien);
        btnChonKH = findViewById(R.id.btnChonKH);
        btnThanhToan = findViewById(R.id.btnThanhToan);
    }

    private void setupRecyclerView() {
        adapter = new GioHangAdapter(this, listGioHang);
        rcGioHang.setLayoutManager(new LinearLayoutManager(this));
        rcGioHang.setAdapter(adapter);

        adapter.setOnQuantityChangeListener((gh, newQuantity) -> {
            gh.setSoLuong(newQuantity);
            dbHelper.updateCartItemQuantity(gh.getId(), newQuantity);
            adapter.notifyDataSetChanged();
            updateTongTien();
        });

        adapter.setOnDeleteListener((gh, position) -> {
            new AlertDialog.Builder(this)
                    .setTitle("Xóa sản phẩm")
                    .setMessage("Bạn có chắc muốn xóa " + gh.getTenSP() + "?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        dbHelper.removeFromCart(gh.getId());
                        listGioHang.remove(position);
                        adapter.notifyItemRemoved(position);
                        updateTongTien();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });
    }

    private void setupListeners() {
        btnChonKH.setOnClickListener(v -> showChonKhachHangDialog());
        btnThanhToan.setOnClickListener(v -> thanhToan());
    }

    private void showChonKhachHangDialog() {
        ArrayList<KhachHang> listKH = dbHelper.getAllKhachHangQL();
        String[] tenKH = new String[listKH.size()];
        for (int i = 0; i < listKH.size(); i++) {
            tenKH[i] = listKH.get(i).getMaKH() + " - " + listKH.get(i).getTen();
        }

        new AlertDialog.Builder(this)
                .setTitle("Chọn khách hàng")
                .setItems(tenKH, (dialog, which) -> {
                    khachHangSelected = listKH.get(which);
                    tvKhachHang.setText(khachHangSelected.getMaKH() + " - " + khachHangSelected.getTen());
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void thanhToan() {
        if (listGioHang.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (khachHangSelected == null) {
            Toast.makeText(this, "Vui lòng chọn khách hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        new AlertDialog.Builder(this)
                .setTitle("Xác nhận thanh toán")
                .setMessage("Tổng tiền: " + String.format("%,d VNĐ", (int) getTongTien()) + "\nKhách hàng: " + khachHangSelected.getTen())
                .setPositiveButton("Thanh toán", (dialog, which) -> {
                    // Tạo hóa đơn
                    HoaDon hoaDon = new HoaDon(khachHangSelected.getMaKH(), khachHangSelected.getTen());
                    hoaDon.setTongTien(getTongTien());
                    
                    long maHD = dbHelper.insertHoaDon(hoaDon);
                    if (maHD > 0) {
                        // Lưu chi tiết hóa đơn
                        for (GioHang gh : listGioHang) {
                            dbHelper.insertChiTietHD((int) maHD, gh);
                        }
                        
                        // Xóa giỏ hàng
                        dbHelper.clearCart();
                        listGioHang.clear();
                        adapter.notifyDataSetChanged();
                        updateTongTien();
                        
                        Toast.makeText(this, "Thanh toán thành công! Mã HD: " + maHD, Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Thanh toán thất bại!", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private double getTongTien() {
        double tong = 0;
        for (GioHang gh : listGioHang) {
            tong += gh.getThanhTien();
        }
        return tong;
    }

    private void updateTongTien() {
        double tong = getTongTien();
        tvTongTien.setText(String.format("%,d VNĐ", (int) tong));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh giỏ hàng khi quay lại
        listGioHang.clear();
        listGioHang.addAll(dbHelper.getAllCartItems());
        adapter.notifyDataSetChanged();
        updateTongTien();
    }
}



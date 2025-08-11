package fpoly.bac.duanmau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageView imgSanPham;
    TextView tvWelcome;
    LinearLayout layoutQuanLyNV, layoutThongKe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        tvWelcome = findViewById(R.id.tvWelcome);
        layoutQuanLyNV = findViewById(R.id.layoutQuanLyNV);
        layoutThongKe = findViewById(R.id.layoutThongKe);

        // Nhận role
        Intent intent = getIntent();
        String role = intent.getStringExtra("role");

        // Chào mừng
        tvWelcome.setText("Xin chào, " + role);

        // Ẩn nếu là nhân viên
        if ("nhanvien".equalsIgnoreCase(role)) {
            layoutQuanLyNV.setVisibility(View.GONE);
            layoutThongKe.setVisibility(View.GONE);
        }
        imgSanPham = findViewById(R.id.imgSanPham);
        ImageView imgNhanVien = findViewById(R.id.imgNhanVien);
        ImageView imgDanhMuc = findViewById(R.id.imgDanhMuc);
        ImageView imgKhachHang = findViewById(R.id.imgKhachHang);
        ImageView imgHoaDon = findViewById(R.id.imgHoaDon);

        imgSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SanPhamActivity.class);
                startActivity(intent);
            }
        });

        imgNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NhanVienActivity.class);
                startActivity(intent);
            }
        });

        imgDanhMuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DanhMucActivity.class);
                startActivity(intent);
            }
        });

        imgKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KhachHangActivity.class);
                startActivity(intent);
            }
        });

        imgHoaDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HoaDonActivity.class);
                startActivity(intent);
            }
        });
    }
}


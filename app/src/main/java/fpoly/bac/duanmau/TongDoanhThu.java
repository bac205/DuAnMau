package fpoly.bac.duanmau;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import fpoly.bac.duanmau.database.DbHelper;

public class TongDoanhThu extends AppCompatActivity {
    TextView tvDoanhthu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tong_doanh_thu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tvDoanhthu = findViewById(R.id.tvDoanhthuthongke);
        DbHelper dbHelper = new DbHelper(this);
        double doanhThu = dbHelper.getTongDoanhThu();
        tvDoanhthu.setText("Tổng doanh thu: " + doanhThu + " VNĐ");

    }
}
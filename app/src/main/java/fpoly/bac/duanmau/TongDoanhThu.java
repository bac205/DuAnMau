package fpoly.bac.duanmau;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.Locale;

import fpoly.bac.duanmau.database.DbHelper;

public class TongDoanhThu extends AppCompatActivity {
    TextView tvDoanhthu;
    Button btnXemDoanhThu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tong_doanh_thu);

        tvDoanhthu = findViewById(R.id.tvDoanhthuthongke);
        btnXemDoanhThu = findViewById(R.id.btnXemDoanhThu);

        DbHelper dbHelper = new DbHelper(this);

        btnXemDoanhThu.setOnClickListener(v -> {
            double doanhThu = dbHelper.getTongDoanhThu();

            // Format số tiền
            NumberFormat formatter = NumberFormat.getInstance(new Locale("vi", "VN"));
            String doanhThuFormatted = formatter.format(doanhThu);

            tvDoanhthu.setText("Tổng doanh thu: " + doanhThuFormatted + " VNĐ");
            tvDoanhthu.setVisibility(View.VISIBLE); // Hiện TextView
        });
    }
}
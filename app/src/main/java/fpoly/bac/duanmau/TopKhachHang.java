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

import java.util.List;

import fpoly.bac.duanmau.database.DbHelper;

public class TopKhachHang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_top_khach_hang);

        TextView tvTopKH = findViewById(R.id.tvTopKH);
        Button btnXemTopKH = findViewById(R.id.btnXemTopKH);
        DbHelper dbHelper = new DbHelper(this);

        btnXemTopKH.setOnClickListener(v -> {
            List<String> top3 = dbHelper.getTop3KhachHang();
            StringBuilder sb = new StringBuilder("Top 3 khách hàng:\n");
            for (int i = 0; i < top3.size(); i++) {
                sb.append((i + 1)).append(". ").append(top3.get(i)).append("\n");
            }
            tvTopKH.setText(sb.toString());
            tvTopKH.setVisibility(View.VISIBLE); // Hiện danh sách
        });
    }
}
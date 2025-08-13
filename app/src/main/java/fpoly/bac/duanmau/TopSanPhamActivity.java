package fpoly.bac.duanmau;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import fpoly.bac.duanmau.database.DbHelper;

public class TopSanPhamActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_san_pham);

        TextView tvTopSP = findViewById(R.id.tvTopSP);
        DbHelper dbHelper = new DbHelper(this);
        List<String> top3 = dbHelper.getTop3SanPhamBanChay();

        StringBuilder sb = new StringBuilder("Top 3 sản phẩm bán chạy:\n");
        for (int i = 0; i < top3.size(); i++) {
            sb.append((i + 1)).append(". ").append(top3.get(i)).append("\n");
        }
        tvTopSP.setText(sb.toString());
    }
}



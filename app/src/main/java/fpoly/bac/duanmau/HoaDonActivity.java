package fpoly.bac.duanmau;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.database.DbHelper;

public class HoaDonActivity extends AppCompatActivity {
    private RecyclerView rcHoaDon;
    private TextView tvEmpty;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);

        Toolbar toolbar = findViewById(R.id.toolbarHoaDon);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
        
        initViews();
        loadHoaDon();
    }

    private void initViews() {
        rcHoaDon = findViewById(R.id.rcHoaDon);
        tvEmpty = findViewById(R.id.tvEmpty);
    }

    private void loadHoaDon() {
        // Lấy danh sách hóa đơn từ database
        // TODO: Implement adapter và RecyclerView cho hóa đơn
        // Hiện tại chỉ hiển thị thông báo
        tvEmpty.setVisibility(android.view.View.VISIBLE);
        tvEmpty.setText("Chưa có hóa đơn nào.\nHãy mua hàng để tạo hóa đơn!");
    }
}

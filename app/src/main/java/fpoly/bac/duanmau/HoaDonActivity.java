package fpoly.bac.duanmau;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.HoaDonAdapter;
import fpoly.bac.duanmau.database.DbHelper;

public class HoaDonActivity extends AppCompatActivity {
    private RecyclerView rcHoaDon;
    private TextView tvEmpty;
    private DbHelper dbHelper;
    private HoaDonAdapter adapter;
    private ArrayList<fpoly.bac.duanmau.model.HoaDon> listHoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon);

        Toolbar toolbar = findViewById(R.id.toolbarHoaDon);
        setSupportActionBar(toolbar);

        dbHelper = new DbHelper(this);
        listHoaDon = new ArrayList<>();
        
        initViews();
        setupRecyclerView();
        loadHoaDon();
    }

    private void initViews() {
        rcHoaDon = findViewById(R.id.rcHoaDon);
        tvEmpty = findViewById(R.id.tvEmpty);
    }

    private void setupRecyclerView() {
        adapter = new HoaDonAdapter(this, listHoaDon);
        rcHoaDon.setLayoutManager(new LinearLayoutManager(this));
        rcHoaDon.setAdapter(adapter);
    }

    private void loadHoaDon() {
        // Lấy danh sách hóa đơn từ database
        listHoaDon.clear();
        listHoaDon.addAll(dbHelper.getAllHoaDon());
        
        if (listHoaDon.isEmpty()) {
            tvEmpty.setVisibility(android.view.View.VISIBLE);
            rcHoaDon.setVisibility(android.view.View.GONE);
            tvEmpty.setText("Chưa có hóa đơn nào.\nHãy mua hàng để tạo hóa đơn!");
        } else {
            tvEmpty.setVisibility(android.view.View.GONE);
            rcHoaDon.setVisibility(android.view.View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh danh sách hóa đơn khi quay lại
        loadHoaDon();
    }
}



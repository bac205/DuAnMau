package fpoly.bac.duanmau;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.HoaDonChiTietAdapter;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.ChiTietHD;

public class HoaDonChiTietActivity extends AppCompatActivity {
    private RecyclerView rcChiTiet;
    private TextView tvTitle;
    private HoaDonChiTietAdapter adapter;
    private ArrayList<ChiTietHD> list;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoadon_chitiet);

        rcChiTiet = findViewById(R.id.rcChiTiet);
        tvTitle = findViewById(R.id.tvTitle);

        int maHD = getIntent().getIntExtra("maHD", -1);
        tvTitle.setText("Hóa đơn #" + maHD);

        dbHelper = new DbHelper(this);
        list = dbHelper.getChiTietHoaDonByMaHD(maHD);

        adapter = new HoaDonChiTietAdapter(this, list);
        rcChiTiet.setLayoutManager(new LinearLayoutManager(this));
        rcChiTiet.setAdapter(adapter);
    }
}



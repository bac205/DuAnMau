package fpoly.bac.duanmau;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.SanPhamAdapter;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.Sanpham;

public class SanPhamActivity extends AppCompatActivity {
    RecyclerView rcSanPham;
    SanPhamAdapter adapter;
    ArrayList<Sanpham> listSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham);
        Toolbar toolbar = findViewById(R.id.toolbarSanPham);
        setSupportActionBar(toolbar);
        rcSanPham = findViewById(R.id.rcSanPham);
        DbHelper dbHelper = new DbHelper(this);
        listSP = dbHelper.getAllSanPham();
        adapter = new SanPhamAdapter(this, listSP);
        rcSanPham.setLayoutManager(new LinearLayoutManager(this));
        rcSanPham.setAdapter(adapter);
    }
}

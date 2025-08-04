package fpoly.bac.duanmau;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.SanPhamAdapter;
import fpoly.bac.duanmau.model.Sanpham;

public class SanPhamActivity extends AppCompatActivity {
    RecyclerView rcSanPham;
    SanPhamAdapter adapter;
    ArrayList<Sanpham> listSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar toolbar = findViewById(R.id.toolbarSanPham);
        setSupportActionBar(toolbar);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanpham);

        rcSanPham = findViewById(R.id.rcSanPham);

        // Khởi tạo danh sách sản phẩm mẫu
        listSP = new ArrayList<>();
        listSP.add(new Sanpham(1, "Whey Protein", 750000, "img_2"));
        listSP.add(new Sanpham(2, "Creatine", 420000, "img_3"));
        listSP.add(new Sanpham(3, "Mass Gainer", 650000, "img_4"));
        listSP.add(new Sanpham(4, "BCAA", 350000, "img_5"));

        // Gắn adapter
        adapter = new SanPhamAdapter(this, listSP);
        rcSanPham.setLayoutManager(new LinearLayoutManager(this));
        rcSanPham.setAdapter(adapter);
    }
}

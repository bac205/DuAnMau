package fpoly.bac.duanmau;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.adapter.DanhMucAdapter;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.DanhMuc;

public class DanhMucActivity extends AppCompatActivity {
    private RecyclerView rcDanhMuc;
    private DanhMucAdapter adapter;
    private ArrayList<DanhMuc> list;
    private com.google.android.material.floatingactionbutton.FloatingActionButton btnAddDM;
    private DbHelper dbHelper;
    private int nextIndex = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhmuc);
        Toolbar toolbar = findViewById(R.id.toolbarDanhMuc);
        setSupportActionBar(toolbar);

        rcDanhMuc = findViewById(R.id.rcDanhMuc);
        rcDanhMuc.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new DbHelper(this);

        list = dbHelper.getAllDanhMucQL();
        // Xác định nextIndex từ dữ liệu hiện có
        for (DanhMuc dm : list) {
            try {
                if (dm.getMaDM() != null && dm.getMaDM().startsWith("DM")) {
                    int num = Integer.parseInt(dm.getMaDM().substring(2));
                    if (num >= nextIndex) nextIndex = num + 1;
                }
            } catch (Exception ignored) {}
        }

        adapter = new DanhMucAdapter(this, list);
        rcDanhMuc.setAdapter(adapter);

        btnAddDM = findViewById(R.id.btnAddDM);
        btnAddDM.setOnClickListener(v -> showAddDialog());

        adapter.setOnItemActionListener(new DanhMucAdapter.OnItemActionListener() {
            @Override
            public void onEdit(DanhMuc dm, int position) {
                showEditDialog(dm, position);
            }

            @Override
            public void onDelete(DanhMuc dm, int position) {
                confirmDelete(dm, position);
            }
        });
    }

    private void showAddDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_danhmuc, null);
        EditText edtTen = view.findViewById(R.id.edtTenDM);
        new AlertDialog.Builder(this)
                .setTitle("Thêm danh mục")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    if (ten.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String ma = String.format("DM%03d", nextIndex++);
                    DanhMuc dm = new DanhMuc(ma, ten, "img_danhmuc");
                    long row = dbHelper.insertDanhMucQL(dm);
                    if (row > 0) {
                        list.add(0, dm);
                        adapter.notifyItemInserted(0);
                        rcDanhMuc.scrollToPosition(0);
                    } else {
                        Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showEditDialog(DanhMuc dm, int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_danhmuc, null);
        EditText edtTen = view.findViewById(R.id.edtTenDM);
        edtTen.setText(dm.getTen());
        new AlertDialog.Builder(this)
                .setTitle("Sửa danh mục " + dm.getMaDM())
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    String ten = edtTen.getText().toString().trim();
                    if (ten.isEmpty()) {
                        Toast.makeText(this, "Vui lòng nhập tên danh mục", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dm.setTen(ten);
                    dm.setHinhAnh("img_danhmuc");
                    int rows = dbHelper.updateDanhMucQL(dm);
                    if (rows > 0) {
                        list.set(position, dm);
                        adapter.notifyItemChanged(position);
                    } else {
                        Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void confirmDelete(DanhMuc dm, int position) {
        new AlertDialog.Builder(this)
                .setTitle("Xoá danh mục")
                .setMessage("Bạn có chắc muốn xoá " + dm.getMaDM() + "?")
                .setPositiveButton("Xoá", (dialog, which) -> {
                    int rows = dbHelper.deleteDanhMucQL(dm.getMaDM());
                    if (rows > 0) {
                        list.remove(position);
                        adapter.notifyItemRemoved(position);
                        adapter.notifyItemRangeChanged(position, list.size() - position);
                    } else {
                        Toast.makeText(this, "Xoá thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}





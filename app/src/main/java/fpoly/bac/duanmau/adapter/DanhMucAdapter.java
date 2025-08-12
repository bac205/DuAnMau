package fpoly.bac.duanmau.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.R;
import fpoly.bac.duanmau.database.DbHelper;
import fpoly.bac.duanmau.model.DanhMuc;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.DanhMucViewHolder> {
    private Context context;
    private ArrayList<DanhMuc> listDM;
    private DbHelper dbHelper;

    public DanhMucAdapter(Context context, ArrayList<DanhMuc> listDM) {
        this.context = context;
        this.listDM = listDM;
        this.dbHelper = new DbHelper(context);
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc, parent, false);
        return new DanhMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        DanhMuc danhMuc = listDM.get(position);
        holder.tvMaDM.setText("Mã DM: " + danhMuc.getMaDM());
        holder.tvTenDM.setText(danhMuc.getTen());
        
        // Xử lý hình ảnh nếu có
        if (danhMuc.getHinhAnh() != null && !danhMuc.getHinhAnh().isEmpty()) {
            // Có thể thêm logic load hình ảnh ở đây
            holder.imgDanhMuc.setImageResource(R.drawable.img_danhmuc);
        } else {
            holder.imgDanhMuc.setImageResource(R.drawable.img_danhmuc);
        }

        holder.imgEdit.setOnClickListener(v -> showEditDialog(danhMuc, position));
        holder.imgDelete.setOnClickListener(v -> showDeleteDialog(danhMuc, position));
    }

    @Override
    public int getItemCount() {
        return listDM.size();
    }

    private void showEditDialog(DanhMuc danhMuc, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_danhmuc, null);
        
        EditText edtMaDM = dialogView.findViewById(R.id.edtMaDM);
        EditText edtTenDM = dialogView.findViewById(R.id.edtTenDM);
        EditText edtHinhAnh = dialogView.findViewById(R.id.edtHinhAnh);

        edtMaDM.setText(danhMuc.getMaDM());
        edtTenDM.setText(danhMuc.getTen());
        edtHinhAnh.setText(danhMuc.getHinhAnh());

        builder.setView(dialogView)
                .setTitle("Sửa Danh Mục")
                .setPositiveButton("Cập nhật", (dialog, which) -> {
                    String maDM = edtMaDM.getText().toString().trim();
                    String tenDM = edtTenDM.getText().toString().trim();
                    String hinhAnh = edtHinhAnh.getText().toString().trim();

                    if (maDM.isEmpty() || tenDM.isEmpty()) {
                        Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DanhMuc updatedDM = new DanhMuc(maDM, tenDM, hinhAnh);
                    if (dbHelper.updateDanhMucQL(updatedDM) > 0) {
                        Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        listDM.set(position, updatedDM);
                        notifyItemChanged(position);
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void showDeleteDialog(DanhMuc danhMuc, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa danh mục '" + danhMuc.getTen() + "'?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    if (dbHelper.deleteDanhMucQL(danhMuc.getMaDM()) > 0) {
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        listDM.remove(position);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    public static class DanhMucViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDanhMuc, imgEdit, imgDelete;
        TextView tvMaDM, tvTenDM;

        public DanhMucViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDanhMuc = itemView.findViewById(R.id.imgDanhMuc);
            imgEdit = itemView.findViewById(R.id.imgEdit);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            tvMaDM = itemView.findViewById(R.id.tvMaDM);
            tvTenDM = itemView.findViewById(R.id.tvTenDM);
        }
    }
}

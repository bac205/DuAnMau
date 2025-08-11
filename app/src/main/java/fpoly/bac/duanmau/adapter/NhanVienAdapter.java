package fpoly.bac.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.R;
import fpoly.bac.duanmau.model.NhanVien;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<NhanVien> list;
    public interface OnItemActionListener {
        void onEdit(NhanVien nv, int position);
        void onDelete(NhanVien nv, int position);
    }
    private OnItemActionListener actionListener;
    public void setOnItemActionListener(OnItemActionListener listener) {
        this.actionListener = listener;
    }

    public NhanVienAdapter(Context context, ArrayList<NhanVien> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNhanVien;
        TextView tvTen, tvDiaChi, tvLuong, tvChucVu, tvMa;
        ImageView btnSua, btnXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNhanVien = itemView.findViewById(R.id.imgNhanVien);
            tvTen = itemView.findViewById(R.id.tvTenNhanVien);
            tvMa = itemView.findViewById(R.id.tvMaNhanVien);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvLuong = itemView.findViewById(R.id.tvLuong);
            tvChucVu = itemView.findViewById(R.id.tvChucVu);
            btnSua = itemView.findViewById(R.id.btnSuaNV);
            btnXoa = itemView.findViewById(R.id.btnXoaNV);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nhanvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NhanVien nv = list.get(position);
        holder.tvTen.setText(nv.getTen());
        holder.tvMa.setText(nv.getMaNV());
        holder.tvDiaChi.setText(nv.getDiaChi());
        holder.tvLuong.setText(String.format("%,.0f", nv.getLuong()));
        holder.tvChucVu.setText(nv.getChucVu());

        int resId = context.getResources().getIdentifier(nv.getHinhAnh(), "drawable", context.getPackageName());
        if (resId == 0) resId = R.drawable.ic_employee; // fallback
        holder.imgNhanVien.setImageResource(resId);

        holder.btnSua.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onEdit(nv, position);
        });
        holder.btnXoa.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onDelete(nv, position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}



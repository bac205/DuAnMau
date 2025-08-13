package fpoly.bac.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.R;
import fpoly.bac.duanmau.model.GioHang;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<GioHang> list;
    private OnQuantityChangeListener quantityListener;
    private OnDeleteListener deleteListener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged(GioHang gh, int newQuantity);
    }

    public interface OnDeleteListener {
        void onDelete(GioHang gh, int position);
    }

    public GioHangAdapter(Context context, ArrayList<GioHang> list) {
        this.context = context;
        this.list = list;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityListener = listener;
    }

    public void setOnDeleteListener(OnDeleteListener listener) {
        this.deleteListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView tvTenSP, tvGiaSP, tvSoLuong, tvThanhTien;
        Button btnTang, btnGiam, btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.imgSanPham);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSP);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            tvThanhTien = itemView.findViewById(R.id.tvThanhTien);
            btnTang = itemView.findViewById(R.id.btnTang);
            btnGiam = itemView.findViewById(R.id.btnGiam);
            btnXoa = itemView.findViewById(R.id.btnXoa);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GioHang gh = list.get(position);
        holder.tvTenSP.setText(gh.getTenSP());
        holder.tvGiaSP.setText(String.format("Giá: %,d VNĐ", (int) gh.getGia()));
        holder.tvSoLuong.setText(String.valueOf(gh.getSoLuong()));
        holder.tvThanhTien.setText(String.format("Thành tiền: %,d VNĐ", (int) gh.getThanhTien()));

        // Load ảnh sản phẩm
        int resId = context.getResources().getIdentifier(gh.getHinhAnh(), "drawable", context.getPackageName());
        if (resId == 0) resId = R.drawable.img_product;
        holder.imgSanPham.setImageResource(resId);

        // Xử lý tăng số lượng
        holder.btnTang.setOnClickListener(v -> {
            int newQuantity = gh.getSoLuong() + 1;
            if (quantityListener != null) {
                quantityListener.onQuantityChanged(gh, newQuantity);
            }
        });

        // Xử lý giảm số lượng
        holder.btnGiam.setOnClickListener(v -> {
            int newQuantity = gh.getSoLuong() - 1;
            if (newQuantity > 0 && quantityListener != null) {
                quantityListener.onQuantityChanged(gh, newQuantity);
            }
        });

        // Xử lý xóa sản phẩm
        holder.btnXoa.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(gh, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}





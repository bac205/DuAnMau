package fpoly.bac.duanmau.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import fpoly.bac.duanmau.R;
import fpoly.bac.duanmau.model.HoaDon;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    private Context context;
    private ArrayList<HoaDon> listHoaDon;

    public interface OnItemActionListener {
        void onDelete(HoaDon hoaDon, int position);
        void onClick(HoaDon hoaDon, int position);
    }
    private OnItemActionListener actionListener;
    public void setOnItemActionListener(OnItemActionListener listener) { this.actionListener = listener; }

    public HoaDonAdapter(Context context, ArrayList<HoaDon> listHoaDon) {
        this.context = context;
        this.listHoaDon = listHoaDon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HoaDon hoaDon = listHoaDon.get(position);
        
        holder.tvMaHD.setText("Mã HD: " + hoaDon.getMaHD());
        holder.tvKhachHang.setText("Khách hàng: " + hoaDon.getTenKH());
        holder.tvNgayTao.setText("Ngày: " + formatDate(hoaDon.getNgayTao()));
        holder.tvTongTien.setText(String.format("%,d VNĐ", (int) hoaDon.getTongTien()));
        holder.btnXoa.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onDelete(hoaDon, position);
        });

        holder.itemView.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onClick(hoaDon, position);
        });
    }

    @Override
    public int getItemCount() {
        return listHoaDon.size();
    }

    private String formatDate(java.util.Date date) {
        if (date == null) return "N/A";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", new Locale("vi", "VN"));
        return sdf.format(date);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMaHD, tvKhachHang, tvNgayTao, tvTongTien;
        ImageView btnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMaHD = itemView.findViewById(R.id.tvMaHD);
            tvKhachHang = itemView.findViewById(R.id.tvKhachHang);
            tvNgayTao = itemView.findViewById(R.id.tvNgayTao);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            btnXoa = itemView.findViewById(R.id.btnXoaHD);
        }
    }
}


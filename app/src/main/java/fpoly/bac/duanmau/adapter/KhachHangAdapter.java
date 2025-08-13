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
import fpoly.bac.duanmau.model.KhachHang;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<KhachHang> list;
    public interface OnItemActionListener {
        void onEdit(KhachHang kh, int position);
        void onDelete(KhachHang kh, int position);
    }
    private OnItemActionListener actionListener;
    public void setOnItemActionListener(OnItemActionListener listener) { this.actionListener = listener; }

    public KhachHangAdapter(Context context, ArrayList<KhachHang> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgKH, btnSua, btnXoa;
        TextView tvTen, tvMa, tvDiaChi, tvEmail, tvSdt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgKH = itemView.findViewById(R.id.imgKhachHang);
            tvTen = itemView.findViewById(R.id.tvTenKH);
            tvMa = itemView.findViewById(R.id.tvMaKH);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChiKH);
            tvEmail = itemView.findViewById(R.id.tvEmailKH);
            tvSdt = itemView.findViewById(R.id.tvSdtKH);
            btnSua = itemView.findViewById(R.id.btnSuaKH);
            btnXoa = itemView.findViewById(R.id.btnXoaKH);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_khachhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KhachHang kh = list.get(position);
        holder.tvTen.setText(kh.getTen());
        holder.tvMa.setText(kh.getMaKH());
        holder.tvDiaChi.setText(kh.getDiaChi());
        holder.tvEmail.setText(kh.getEmail());
        holder.tvSdt.setText(kh.getSdt());
        int resId = context.getResources().getIdentifier(kh.getHinhAnh(), "drawable", context.getPackageName());
        if (resId == 0) resId = R.drawable.ic_customer;
        holder.imgKH.setImageResource(resId);

        holder.btnSua.setOnClickListener(v -> { if (actionListener != null) actionListener.onEdit(kh, position); });
        holder.btnXoa.setOnClickListener(v -> { if (actionListener != null) actionListener.onDelete(kh, position); });
    }

    @Override
    public int getItemCount() { return list.size(); }
}









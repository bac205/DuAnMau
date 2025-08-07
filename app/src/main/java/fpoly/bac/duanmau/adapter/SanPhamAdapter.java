package fpoly.bac.duanmau.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.bac.duanmau.R;
import fpoly.bac.duanmau.model.Sanpham;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Sanpham> list;

    public SanPhamAdapter(Context context, ArrayList<Sanpham> list) {
        this.context = context;
        this.list = list;
    }

    // Interface callback cho sự kiện xóa và sửa
    public interface OnItemActionListener {
        void onDelete(Sanpham sp, int position);
        void onEdit(Sanpham sp, int position);
    }
    private OnItemActionListener actionListener;
    public void setOnItemActionListener(OnItemActionListener listener) {
        this.actionListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSP;
        TextView tvTenSP, tvGia;
        ImageView btnXoa, btnSua;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.imgSanPham);
            tvTenSP = itemView.findViewById(R.id.tvTenSanPham);
            tvGia = itemView.findViewById(R.id.tvGiaSanPham);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnSua = itemView.findViewById(R.id.btnSua);
        }
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sanpham, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        Sanpham sp = list.get(position);
        holder.tvTenSP.setText(sp.getTenSP());
        holder.tvGia.setText("Giá: " + sp.getGia() + " VNĐ");
        int resID = context.getResources().getIdentifier(sp.getHinhAnh(), "drawable", context.getPackageName());
        holder.imgSP.setImageResource(resID);
        holder.imgSP.setOnClickListener(v -> {
            // TODO: mở màn chi tiết (tí mình sẽ làm sau)
        });
        // Sự kiện xóa
        holder.btnXoa.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onDelete(sp, position);
        });
        // Sự kiện sửa
        holder.btnSua.setOnClickListener(v -> {
            if (actionListener != null) actionListener.onEdit(sp, position);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

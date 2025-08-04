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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSP;
        TextView tvTenSP, tvGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.imgSanPham);
            tvTenSP = itemView.findViewById(R.id.tvTenSanPham);
            tvGia = itemView.findViewById(R.id.tvGiaSanPham);
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

        // Load hình từ tên file drawable
        int resID = context.getResources().getIdentifier(sp.getHinhAnh(), "drawable", context.getPackageName());
        holder.imgSP.setImageResource(resID);

        // Sự kiện click vào ảnh sản phẩm
        holder.imgSP.setOnClickListener(v -> {
            // TODO: mở màn chi tiết (tí mình sẽ làm sau)
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

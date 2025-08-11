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
import fpoly.bac.duanmau.model.DanhMuc;

public class DanhMucAdapter extends RecyclerView.Adapter<DanhMucAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<DanhMuc> list;
    public interface OnItemActionListener {
        void onEdit(DanhMuc dm, int position);
        void onDelete(DanhMuc dm, int position);
    }
    private OnItemActionListener actionListener;
    public void setOnItemActionListener(OnItemActionListener listener) { this.actionListener = listener; }

    public DanhMucAdapter(Context context, ArrayList<DanhMuc> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgDanhMuc, btnSua, btnXoa;
        TextView tvTen, tvMa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDanhMuc = itemView.findViewById(R.id.imgDanhMuc);
            tvTen = itemView.findViewById(R.id.tvTenDanhMuc);
            tvMa = itemView.findViewById(R.id.tvMaDanhMuc);
            btnSua = itemView.findViewById(R.id.btnSuaDM);
            btnXoa = itemView.findViewById(R.id.btnXoaDM);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DanhMuc dm = list.get(position);
        holder.tvTen.setText(dm.getTen());
        holder.tvMa.setText(dm.getMaDM());
        int resId = context.getResources().getIdentifier(dm.getHinhAnh(), "drawable", context.getPackageName());
        if (resId == 0) resId = R.drawable.ic_category;
        holder.imgDanhMuc.setImageResource(resId);

        holder.btnSua.setOnClickListener(v -> { if (actionListener != null) actionListener.onEdit(dm, position); });
        holder.btnXoa.setOnClickListener(v -> { if (actionListener != null) actionListener.onDelete(dm, position); });
    }

    @Override
    public int getItemCount() { return list.size(); }
}





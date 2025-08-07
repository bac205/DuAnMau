package fpoly.bac.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Supermarket.db";
    public static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNhanVien = "CREATE TABLE NhanVien (" +
                "maNV TEXT PRIMARY KEY," +
                "hoTen TEXT NOT NULL," +
                "matKhau TEXT NOT NULL," +
                "vaiTro TEXT NOT NULL)";
        db.execSQL(createNhanVien);

        // Thêm dữ liệu mẫu
        db.execSQL("INSERT INTO NhanVien VALUES " +
                "('admin', 'Quản trị viên', 'admin123', 'admin')," +
                "('nv01', 'Nguyễn Văn A', '123456', 'nhanvien')");

        // Tạo bảng sản phẩm
        String createSanPham = "CREATE TABLE SanPham (" +
                "maSP INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tenSP TEXT NOT NULL," +
                "gia REAL NOT NULL," +
                "hinhAnh TEXT)";
        db.execSQL(createSanPham);

        // Thêm dữ liệu mẫu cho sản phẩm
        db.execSQL("INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES " +
                "('Táo Mỹ', 30000, 'img_taomi')," +
                "('Nho Hàn', 45000, 'img_nhohan')," +
                "('Chuối Việt Nam', 20000, 'img_chuoivn')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        db.execSQL("DROP TABLE IF EXISTS SanPham");
        onCreate(db);
    }

    public boolean checkLogin(String maNV, String matKhau) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM NhanVien WHERE maNV = ? AND matKhau = ?";
        String[] args = {maNV, matKhau};
        return db.rawQuery(sql, args).getCount() > 0;
    }

    // Lấy tất cả sản phẩm từ SQLite
    public java.util.ArrayList<fpoly.bac.duanmau.model.Sanpham> getAllSanPham() {
        java.util.ArrayList<fpoly.bac.duanmau.model.Sanpham> list = new java.util.ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM SanPham", null);
        if (cursor.moveToFirst()) {
            do {
                int maSP = cursor.getInt(cursor.getColumnIndexOrThrow("maSP"));
                String tenSP = cursor.getString(cursor.getColumnIndexOrThrow("tenSP"));
                double gia = cursor.getDouble(cursor.getColumnIndexOrThrow("gia"));
                String hinhAnh = cursor.getString(cursor.getColumnIndexOrThrow("hinhAnh"));
                list.add(new fpoly.bac.duanmau.model.Sanpham(maSP, tenSP, gia, hinhAnh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Thêm sản phẩm mới
    public long insertSanPham(fpoly.bac.duanmau.model.Sanpham sp) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("tenSP", sp.getTenSP());
        values.put("gia", sp.getGia());
        values.put("hinhAnh", sp.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("SanPham", null, values);
    }

    // Sửa sản phẩm
    public int updateSanPham(fpoly.bac.duanmau.model.Sanpham sp) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("tenSP", sp.getTenSP());
        values.put("gia", sp.getGia());
        values.put("hinhAnh", sp.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.update("SanPham", values, "maSP=?", new String[]{String.valueOf(sp.getMaSP())});
    }

    // Xóa sản phẩm
    public int deleteSanPham(int maSP) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("SanPham", "maSP=?", new String[]{String.valueOf(maSP)});
    }
}

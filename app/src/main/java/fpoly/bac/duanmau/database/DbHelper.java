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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        onCreate(db);
    }

    public boolean checkLogin(String maNV, String matKhau) {
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM NhanVien WHERE maNV = ? AND matKhau = ?";
        String[] args = {maNV, matKhau};
        return db.rawQuery(sql, args).getCount() > 0;
    }
}

package fpoly.bac.duanmau.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "Supermarket.db";
    public static final int DB_VERSION = 8;

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
                "('Táo Mỹ', 30000, 'img_product')," +
                "('Nho Hàn', 45000, 'img_product')," +
                "('Chuối Việt Nam', 20000, 'img_product')," +
                "('Cam Sành', 25000, 'img_product')," +
                "('Xoài Cát Hòa Lộc', 35000, 'img_product')," +
                "('Dưa Hấu', 15000, 'img_product')," +
                "('Mít Thái', 40000, 'img_product')," +
                "('Sầu Riêng', 80000, 'img_product')");

        // Tạo bảng quản lý nhân viên (khác với bảng NhanVien dùng đăng nhập)
        String createNhanVienQL = "CREATE TABLE NhanVienQL (" +
                "maNV TEXT PRIMARY KEY," +
                "ten TEXT NOT NULL," +
                "diaChi TEXT," +
                "luong REAL NOT NULL," +
                "chucVu TEXT," +
                "hinhAnh TEXT)";
        db.execSQL(createNhanVienQL);

        // Dữ liệu mẫu nhân viên theo yêu cầu
        db.execSQL("INSERT INTO NhanVienQL (maNV, ten, diaChi, luong, chucVu, hinhAnh) VALUES " +
                "('NV001','Nguyễn Văn A','Hà Nội',6000000,'Nhân viên','img_nhanvien')," +
                "('NV002','Nguyễn Văn B','Hà Nội',6000000,'Nhân viên','img_nhanvien')," +
                "('NV003','Nguyễn Văn C','Hải Phòng',6000000,'Nhân viên','img_nhanvien')," +
                "('NV004','Nguyễn Văn D','Thanh Hoá',10000000,'Quản lí','img_nhanvien')");

        // Tạo bảng danh mục
        String createDanhMuc = "CREATE TABLE DanhMucQL (" +
                "maDM TEXT PRIMARY KEY," +
                "ten TEXT NOT NULL," +
                "hinhAnh TEXT)";
        db.execSQL(createDanhMuc);
        // Dữ liệu mẫu danh mục
        db.execSQL("INSERT INTO DanhMucQL (maDM, ten, hinhAnh) VALUES " +
                "('DM001','Sữa','img_danhmuc')," +
                "('DM002','Kẹo','img_danhmuc')," +
                "('DM003','Đồ Uống','img_danhmuc')");

        // Tạo bảng khách hàng
        String createKhachHang = "CREATE TABLE KhachHangQL (" +
                "maKH TEXT PRIMARY KEY," +
                "ten TEXT NOT NULL," +
                "diaChi TEXT," +
                "email TEXT," +
                "sdt TEXT," +
                "hinhAnh TEXT)";
        db.execSQL(createKhachHang);
        // Dữ liệu mẫu khách hàng theo yêu cầu
        db.execSQL("INSERT INTO KhachHangQL (maKH, ten, diaChi, email, sdt, hinhAnh) VALUES " +
                "('KH001','Lê Văn Hậu','101 Quang Trung Hà Nội','kh1@gmail.com','0968472059','img_kh')," +
                "('KH002','Nguyễn Thuỳ Linh','29 Hàng Buồm Hà Nội','linh@gmail.com','09578285938','img_kh')," +
                "('KH003','Nguyễn Thuỳ Tiên','37 Quận 6 TP Hồ Chí Minh','trung12@gmail.com','0385948573','img_kh')," +
                "('KH004','Nguyễn Trung Kiên','101 Hải Phòng','th@gmail.com','0957284956','img_kh')");

        // Tạo bảng giỏ hàng
        String createGioHang = "CREATE TABLE GioHang (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maSP INTEGER NOT NULL," +
                "tenSP TEXT NOT NULL," +
                "gia REAL NOT NULL," +
                "soLuong INTEGER NOT NULL," +
                "hinhAnh TEXT)";
        db.execSQL(createGioHang);

        // Tạo bảng hóa đơn
        String createHoaDon = "CREATE TABLE HoaDon (" +
                "maHD INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maKH TEXT NOT NULL," +
                "tenKH TEXT NOT NULL," +
                "ngayTao TEXT NOT NULL," +
                "tongTien REAL NOT NULL)";
        db.execSQL(createHoaDon);

        // Tạo bảng chi tiết hóa đơn
        String createChiTietHD = "CREATE TABLE ChiTietHD (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "maHD INTEGER NOT NULL," +
                "maSP INTEGER NOT NULL," +
                "tenSP TEXT NOT NULL," +
                "gia REAL NOT NULL," +
                "soLuong INTEGER NOT NULL," +
                "thanhTien REAL NOT NULL)";
        db.execSQL(createChiTietHD);

    }
    // Lấy tổng doanh thu từ bảng HoaDon
    public double getTongDoanhThu() {
        double tongDoanhThu = 0;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT SUM(tongTien) FROM HoaDon", null);
            if (cursor.moveToFirst()) {
                tongDoanhThu = cursor.isNull(0) ? 0 : cursor.getDouble(0);
            }
        } catch (Exception e) {
            android.util.Log.e("DbHelper", "Lỗi khi tính tổng doanh thu: " + e.getMessage());
        } finally {
            if (cursor != null) cursor.close();
        }
        return tongDoanhThu;
    }
    public List<String> getTop3KhachHang() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT KH.ten, SUM(HD.tongTien) AS tongChiTieu " +
                "FROM HoaDon HD " +
                "JOIN KhachHangQL KH ON KH.maKH = HD.maKH " +
                "GROUP BY KH.maKH " +
                "ORDER BY tongChiTieu DESC " +
                "LIMIT 3";

        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                String ten = cursor.getString(0);
                double tong = cursor.getDouble(1);
                list.add(ten + " - " + tong + " VNĐ");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }





    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        db.execSQL("DROP TABLE IF EXISTS SanPham");
        db.execSQL("DROP TABLE IF EXISTS NhanVienQL");
        db.execSQL("DROP TABLE IF EXISTS DanhMucQL");
        db.execSQL("DROP TABLE IF EXISTS KhachHangQL");
        db.execSQL("DROP TABLE IF EXISTS GioHang");
        db.execSQL("DROP TABLE IF EXISTS HoaDon");
        db.execSQL("DROP TABLE IF EXISTS ChiTietHD");
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

    // ================= NhanVienQL CRUD =================
    public java.util.ArrayList<fpoly.bac.duanmau.model.NhanVien> getAllNhanVienQL() {
        java.util.ArrayList<fpoly.bac.duanmau.model.NhanVien> list = new java.util.ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM NhanVienQL ORDER BY maNV", null);
        if (cursor.moveToFirst()) {
            do {
                String maNV = cursor.getString(cursor.getColumnIndexOrThrow("maNV"));
                String ten = cursor.getString(cursor.getColumnIndexOrThrow("ten"));
                String diaChi = cursor.getString(cursor.getColumnIndexOrThrow("diaChi"));
                double luong = cursor.getDouble(cursor.getColumnIndexOrThrow("luong"));
                String chucVu = cursor.getString(cursor.getColumnIndexOrThrow("chucVu"));
                String hinhAnh = cursor.getString(cursor.getColumnIndexOrThrow("hinhAnh"));
                list.add(new fpoly.bac.duanmau.model.NhanVien(maNV, ten, diaChi, luong, chucVu, hinhAnh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertNhanVienQL(fpoly.bac.duanmau.model.NhanVien nv) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("maNV", nv.getMaNV());
        values.put("ten", nv.getTen());
        values.put("diaChi", nv.getDiaChi());
        values.put("luong", nv.getLuong());
        values.put("chucVu", nv.getChucVu());
        values.put("hinhAnh", nv.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("NhanVienQL", null, values);
    }

    public int updateNhanVienQL(fpoly.bac.duanmau.model.NhanVien nv) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("ten", nv.getTen());
        values.put("diaChi", nv.getDiaChi());
        values.put("luong", nv.getLuong());
        values.put("chucVu", nv.getChucVu());
        values.put("hinhAnh", nv.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.update("NhanVienQL", values, "maNV=?", new String[]{nv.getMaNV()});
    }

    public int deleteNhanVienQL(String maNV) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("NhanVienQL", "maNV=?", new String[]{maNV});
    }

    // ================= DanhMucQL CRUD =================
    public java.util.ArrayList<fpoly.bac.duanmau.model.DanhMuc> getAllDanhMucQL() {
        java.util.ArrayList<fpoly.bac.duanmau.model.DanhMuc> list = new java.util.ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM DanhMucQL ORDER BY maDM", null);
        if (cursor.moveToFirst()) {
            do {
                String maDM = cursor.getString(cursor.getColumnIndexOrThrow("maDM"));
                String ten = cursor.getString(cursor.getColumnIndexOrThrow("ten"));
                String hinhAnh = cursor.getString(cursor.getColumnIndexOrThrow("hinhAnh"));
                list.add(new fpoly.bac.duanmau.model.DanhMuc(maDM, ten, hinhAnh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertDanhMucQL(fpoly.bac.duanmau.model.DanhMuc dm) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("maDM", dm.getMaDM());
        values.put("ten", dm.getTen());
        values.put("hinhAnh", dm.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("DanhMucQL", null, values);
    }

    public int updateDanhMucQL(fpoly.bac.duanmau.model.DanhMuc dm) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("ten", dm.getTen());
        values.put("hinhAnh", dm.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.update("DanhMucQL", values, "maDM=?", new String[]{dm.getMaDM()});
    }

    public int deleteDanhMucQL(String maDM) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("DanhMucQL", "maDM=?", new String[]{maDM});
    }

    // ================= KhachHangQL CRUD =================
    public java.util.ArrayList<fpoly.bac.duanmau.model.KhachHang> getAllKhachHangQL() {
        java.util.ArrayList<fpoly.bac.duanmau.model.KhachHang> list = new java.util.ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM KhachHangQL ORDER BY maKH", null);
        if (cursor.moveToFirst()) {
            do {
                String maKH = cursor.getString(cursor.getColumnIndexOrThrow("maKH"));
                String ten = cursor.getString(cursor.getColumnIndexOrThrow("ten"));
                String diaChi = cursor.getString(cursor.getColumnIndexOrThrow("diaChi"));
                String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
                String sdt = cursor.getString(cursor.getColumnIndexOrThrow("sdt"));
                String hinhAnh = cursor.getString(cursor.getColumnIndexOrThrow("hinhAnh"));
                list.add(new fpoly.bac.duanmau.model.KhachHang(maKH, ten, diaChi, email, sdt, hinhAnh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertKhachHangQL(fpoly.bac.duanmau.model.KhachHang kh) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("maKH", kh.getMaKH());
        values.put("ten", kh.getTen());
        values.put("diaChi", kh.getDiaChi());
        values.put("email", kh.getEmail());
        values.put("sdt", kh.getSdt());
        values.put("hinhAnh", kh.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("KhachHangQL", null, values);
    }

    public int updateKhachHangQL(fpoly.bac.duanmau.model.KhachHang kh) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("ten", kh.getTen());
        values.put("diaChi", kh.getDiaChi());
        values.put("email", kh.getEmail());
        values.put("sdt", kh.getSdt());
        values.put("hinhAnh", kh.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.update("KhachHangQL", values, "maKH=?", new String[]{kh.getMaKH()});
    }

    public int deleteKhachHangQL(String maKH) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("KhachHangQL", "maKH=?", new String[]{maKH});
    }

    // ================= GioHang CRUD =================
    public java.util.ArrayList<fpoly.bac.duanmau.model.GioHang> getAllGioHang() {
        java.util.ArrayList<fpoly.bac.duanmau.model.GioHang> list = new java.util.ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM GioHang", null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                int maSP = cursor.getInt(cursor.getColumnIndexOrThrow("maSP"));
                String tenSP = cursor.getString(cursor.getColumnIndexOrThrow("tenSP"));
                double gia = cursor.getDouble(cursor.getColumnIndexOrThrow("gia"));
                int soLuong = cursor.getInt(cursor.getColumnIndexOrThrow("soLuong"));
                String hinhAnh = cursor.getString(cursor.getColumnIndexOrThrow("hinhAnh"));
                list.add(new fpoly.bac.duanmau.model.GioHang(id, maSP, tenSP, gia, soLuong, hinhAnh));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public long insertGioHang(fpoly.bac.duanmau.model.GioHang gh) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("maSP", gh.getMaSP());
        values.put("tenSP", gh.getTenSP());
        values.put("gia", gh.getGia());
        values.put("soLuong", gh.getSoLuong());
        values.put("hinhAnh", gh.getHinhAnh());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("GioHang", null, values);
    }

    public int updateGioHang(fpoly.bac.duanmau.model.GioHang gh) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("soLuong", gh.getSoLuong());
        SQLiteDatabase db = getWritableDatabase();
        return db.update("GioHang", values, "id=?", new String[]{String.valueOf(gh.getId())});
    }

    public int deleteGioHang(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("GioHang", "id=?", new String[]{String.valueOf(id)});
    }

    public void clearGioHang() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("GioHang", null, null);
    }

    // ================= HoaDon CRUD =================
    public long insertHoaDon(fpoly.bac.duanmau.model.HoaDon hd) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("maKH", hd.getMaKH());
        values.put("tenKH", hd.getTenKH());
        values.put("ngayTao", hd.getNgayTao().toString());
        values.put("tongTien", hd.getTongTien());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("HoaDon", null, values);
    }

    public long insertChiTietHD(int maHD, fpoly.bac.duanmau.model.GioHang gh) {
        android.content.ContentValues values = new android.content.ContentValues();
        values.put("maHD", maHD);
        values.put("maSP", gh.getMaSP());
        values.put("tenSP", gh.getTenSP());
        values.put("gia", gh.getGia());
        values.put("soLuong", gh.getSoLuong());
        values.put("thanhTien", gh.getThanhTien());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("ChiTietHD", null, values);
    }

    public java.util.ArrayList<fpoly.bac.duanmau.model.HoaDon> getAllHoaDon() {
        java.util.ArrayList<fpoly.bac.duanmau.model.HoaDon> listHoaDon = new java.util.ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM HoaDon ORDER BY maHD DESC", null);
        
        if (cursor.moveToFirst()) {
            do {
                int maHD = cursor.getInt(0);
                String maKH = cursor.getString(1);
                String tenKH = cursor.getString(2);
                String ngayTao = cursor.getString(3);
                double tongTien = cursor.getDouble(4);
                
                fpoly.bac.duanmau.model.HoaDon hoaDon = new fpoly.bac.duanmau.model.HoaDon(maKH, tenKH);
                hoaDon.setMaHD(maHD);
                hoaDon.setTongTien(tongTien);
                // TODO: Parse ngayTao string thành Date nếu cần
                
                listHoaDon.add(hoaDon);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listHoaDon;
    }

    /**
     * Xóa hóa đơn và toàn bộ chi tiết hóa đơn liên quan theo mã hóa đơn
     * Trả về số bản ghi bị xóa ở bảng HoaDon (0 hoặc 1)
     */
    public int deleteHoaDon(int maHD) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        int affected = 0;
        try {
            db.delete("ChiTietHD", "maHD=?", new String[]{String.valueOf(maHD)});
            affected = db.delete("HoaDon", "maHD=?", new String[]{String.valueOf(maHD)});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return affected;
    }

    // ================= Giỏ hàng CRUD =================
    public boolean isProductInCart(int maSP) {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM GioHang WHERE maSP = ?", new String[]{String.valueOf(maSP)});
        boolean exists = false;
        if (cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0;
        }
        cursor.close();
        return exists;
    }

    public boolean addToCart(int maSP, String tenSP, double gia, int soLuong, String hinhAnh) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put("maSP", maSP);
            values.put("tenSP", tenSP);
            values.put("gia", gia);
            values.put("soLuong", soLuong);
            values.put("hinhAnh", hinhAnh);
            
            long result = db.insert("GioHang", null, values);
            return result != -1;
        } catch (Exception e) {
            android.util.Log.e("DbHelper", "Lỗi khi thêm vào giỏ hàng: " + e.getMessage());
            return false;
        }
    }

    public boolean updateCartItemQuantity(int id, int soLuong) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            android.content.ContentValues values = new android.content.ContentValues();
            values.put("soLuong", soLuong);
            
            int result = db.update("GioHang", values, "id = ?", new String[]{String.valueOf(id)});
            return result > 0;
        } catch (Exception e) {
            android.util.Log.e("DbHelper", "Lỗi khi cập nhật số lượng: " + e.getMessage());
            return false;
        }
    }

    public boolean removeFromCart(int id) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            int result = db.delete("GioHang", "id = ?", new String[]{String.valueOf(id)});
            return result > 0;
        } catch (Exception e) {
            android.util.Log.e("DbHelper", "Lỗi khi xóa khỏi giỏ hàng: " + e.getMessage());
            return false;
        }
    }

    public java.util.ArrayList<fpoly.bac.duanmau.model.GioHang> getAllCartItems() {
        java.util.ArrayList<fpoly.bac.duanmau.model.GioHang> cartItems = new java.util.ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM GioHang ORDER BY id", null);
        
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int maSP = cursor.getInt(1);
                String tenSP = cursor.getString(2);
                double gia = cursor.getDouble(3);
                int soLuong = cursor.getInt(4);
                String hinhAnh = cursor.getString(5);
                
                fpoly.bac.duanmau.model.GioHang item = new fpoly.bac.duanmau.model.GioHang(id, maSP, tenSP, gia, soLuong, hinhAnh);
                cartItems.add(item);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cartItems;
    }

    public void clearCart() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("GioHang", null, null);
    }

    // Method debug để kiểm tra database
    public void debugDatabase() {
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        android.util.Log.d("DbHelper", "=== TABLES IN DATABASE ===");
        if (cursor.moveToFirst()) {
            do {
                String tableName = cursor.getString(0);
                android.util.Log.d("DbHelper", "Table: " + tableName);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Kiểm tra số lượng sản phẩm
        try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM SanPham", null);
            if (cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                android.util.Log.d("DbHelper", "Số sản phẩm: " + count);
            }
            cursor.close();
        } catch (Exception e) {
            android.util.Log.e("DbHelper", "Lỗi khi đếm sản phẩm: " + e.getMessage());
        }
    }

    // Method thêm sản phẩm mẫu nếu database trống
    public void addSampleProductsIfEmpty() {
        android.util.Log.d("DbHelper", "Kiểm tra và thêm sản phẩm mẫu...");
        
        // Kiểm tra số lượng sản phẩm hiện tại
        SQLiteDatabase db = getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM SanPham", null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        
        android.util.Log.d("DbHelper", "Số sản phẩm hiện tại: " + count);
        
        // Nếu không có sản phẩm, thêm sản phẩm mẫu
        if (count == 0) {
            android.util.Log.d("DbHelper", "Thêm sản phẩm mẫu...");
            
            // Thêm các sản phẩm mẫu
            String[] products = {
                "INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES ('Táo Mỹ', 30000, 'img_product')",
                "INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES ('Nho Hàn', 45000, 'img_product')",
                "INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES ('Chuối Việt Nam', 20000, 'img_product')",
                "INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES ('Cam Sành', 25000, 'img_product')",
                "INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES ('Xoài Cát Hòa Lộc', 35000, 'img_product')",
                "INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES ('Dưa Hấu', 15000, 'img_product')",
                "INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES ('Mít Thái', 40000, 'img_product')",
                "INSERT INTO SanPham (tenSP, gia, hinhAnh) VALUES ('Sầu Riêng', 80000, 'img_product')"
            };
            
            db = getWritableDatabase();
            for (String sql : products) {
                try {
                    db.execSQL(sql);
                    android.util.Log.d("DbHelper", "Đã thêm: " + sql);
                } catch (Exception e) {
                    android.util.Log.e("DbHelper", "Lỗi khi thêm: " + e.getMessage());
                }
            }
            
            android.util.Log.d("DbHelper", "Hoàn thành thêm sản phẩm mẫu");
        } else {
            android.util.Log.d("DbHelper", "Đã có sản phẩm, không cần thêm");
        }
    }
}

package fpoly.bac.duanmau.model;

public class NhanVien {
    private String maNV;
    private String ten;
    private String diaChi;
    private double luong;
    private String chucVu;
    private String hinhAnh; // tên drawable, dùng chung img_nhanvien

    public NhanVien(String maNV, String ten, String diaChi, double luong, String chucVu, String hinhAnh) {
        this.maNV = maNV;
        this.ten = ten;
        this.diaChi = diaChi;
        this.luong = luong;
        this.chucVu = chucVu;
        this.hinhAnh = hinhAnh;
    }

    public String getMaNV() { return maNV; }
    public void setMaNV(String maNV) { this.maNV = maNV; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public double getLuong() { return luong; }
    public void setLuong(double luong) { this.luong = luong; }
    public String getChucVu() { return chucVu; }
    public void setChucVu(String chucVu) { this.chucVu = chucVu; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}



package fpoly.bac.duanmau.model;

public class GioHang {
    private int id;
    private int maSP;
    private String tenSP;
    private double gia;
    private int soLuong;
    private String hinhAnh;

    public GioHang(int id, int maSP, String tenSP, double gia, int soLuong, String hinhAnh) {
        this.id = id;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public GioHang(int maSP, String tenSP, double gia, int soLuong, String hinhAnh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
        this.hinhAnh = hinhAnh;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getMaSP() { return maSP; }
    public void setMaSP(int maSP) { this.maSP = maSP; }
    public String getTenSP() { return tenSP; }
    public void setTenSP(String tenSP) { this.tenSP = tenSP; }
    public double getGia() { return gia; }
    public void setGia(double gia) { this.gia = gia; }
    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }

    public double getThanhTien() {
        return gia * soLuong;
    }
}




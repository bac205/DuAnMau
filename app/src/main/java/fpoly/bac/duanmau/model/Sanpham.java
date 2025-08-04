package fpoly.bac.duanmau.model;

public class Sanpham {
    private int maSP;
    private String tenSP;
    private double gia;
    private String hinhAnh;
    public Sanpham() {

    }

    public Sanpham(int maSP, String tenSP, double gia, String hinhAnh) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.hinhAnh = hinhAnh;
    }

    public int getMaSP() {
        return maSP;
    }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}

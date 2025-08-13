package fpoly.bac.duanmau.model;

public class ChiTietHD {
    private int maSP;
    private String tenSP;
    private double gia;
    private int soLuong;
    private double thanhTien;

    public ChiTietHD(int maSP, String tenSP, double gia, int soLuong, double thanhTien) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.gia = gia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
    }

    public int getMaSP() { return maSP; }
    public String getTenSP() { return tenSP; }
    public double getGia() { return gia; }
    public int getSoLuong() { return soLuong; }
    public double getThanhTien() { return thanhTien; }
}



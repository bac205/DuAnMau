package fpoly.bac.duanmau.model;

import java.util.ArrayList;
import java.util.Date;

public class HoaDon {
    private int maHD;
    private String maKH;
    private String tenKH;
    private Date ngayTao;
    private double tongTien;
    private ArrayList<GioHang> danhSachSP;

    public HoaDon(int maHD, String maKH, String tenKH, Date ngayTao, double tongTien) {
        this.maHD = maHD;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.ngayTao = ngayTao;
        this.tongTien = tongTien;
        this.danhSachSP = new ArrayList<>();
    }

    public HoaDon(String maKH, String tenKH) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.ngayTao = new Date();
        this.danhSachSP = new ArrayList<>();
        this.tongTien = 0;
    }

    public int getMaHD() { return maHD; }
    public void setMaHD(int maHD) { this.maHD = maHD; }
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public String getTenKH() { return tenKH; }
    public void setTenKH(String tenKH) { this.tenKH = tenKH; }
    public Date getNgayTao() { return ngayTao; }
    public void setNgayTao(Date ngayTao) { this.ngayTao = ngayTao; }
    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public ArrayList<GioHang> getDanhSachSP() { return danhSachSP; }
    public void setDanhSachSP(ArrayList<GioHang> danhSachSP) { this.danhSachSP = danhSachSP; }

    public void addSanPham(GioHang sp) {
        danhSachSP.add(sp);
        tongTien += sp.getThanhTien();
    }

    public void removeSanPham(GioHang sp) {
        if (danhSachSP.remove(sp)) {
            tongTien -= sp.getThanhTien();
        }
    }
}





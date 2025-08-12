package fpoly.bac.duanmau.model;

public class KhachHang {
    private String maKH;
    private String ten;
    private String diaChi;
    private String email;
    private String sdt;
    private String hinhAnh;

    public KhachHang(String maKH, String ten, String diaChi, String email, String sdt, String hinhAnh) {
        this.maKH = maKH;
        this.ten = ten;
        this.diaChi = diaChi;
        this.email = email;
        this.sdt = sdt;
        this.hinhAnh = hinhAnh;
    }

    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}








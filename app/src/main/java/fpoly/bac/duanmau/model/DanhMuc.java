package fpoly.bac.duanmau.model;

public class DanhMuc {
    private String maDM;
    private String ten;
    private String hinhAnh;

    public DanhMuc(String maDM, String ten, String hinhAnh) {
        this.maDM = maDM;
        this.ten = ten;
        this.hinhAnh = hinhAnh;
    }

    public String getMaDM() { return maDM; }
    public void setMaDM(String maDM) { this.maDM = maDM; }
    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
}





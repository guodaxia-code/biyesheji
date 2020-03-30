package xyz.worldzhile.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {

    private String pid;
    private String pname;
    private Float store_price;

    private Float rel_price;
    private String ppicture;
    private String pdesc;

    private Date pdate;
    private Integer is_hot;
    private Integer pflag;


    private Category category;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Float getStore_price() {
        return store_price;
    }

    public void setStore_price(Float store_price) {
        this.store_price = store_price;
    }

    public Float getRel_price() {
        return rel_price;
    }

    public void setRel_price(Float rel_price) {
        this.rel_price = rel_price;
    }

    public String getPpicture() {
        return ppicture;
    }

    public void setPpicture(String ppicture) {
        this.ppicture = ppicture;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public String getPdate() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String time= sdf.format(this.pdate);//获取格式化日期，带有时分

        return time;
    }

    public void setPdate(Date pdate) {
        this.pdate = pdate;
    }

    public Integer getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(Integer is_hot) {
        this.is_hot = is_hot;
    }

    public Integer getPflag() {
        return pflag;
    }

    public void setPflag(Integer pflag) {
        this.pflag = pflag;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", store_price=" + store_price +
                ", rel_price=" + rel_price +
                ", ppicture='" + ppicture + '\'' +
                ", pdesc='" + pdesc + '\'' +
                ", pdate=" + pdate +
                ", is_hot=" + is_hot +
                ", pflag=" + pflag +
                ", category=" + category +
                '}';
    }
}

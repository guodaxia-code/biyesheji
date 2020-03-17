package xyz.worldzhile.domain;

public class OrderItem {


    /**
     * oiid : 123
     * number : 15
     * money : 15.0
     */

    private String oiid;
    private Integer number;
    private Double money;



    private String oid;

    private  String pid;




    private Order order;

    private Product product;


    public String getOiid() {
        return oiid;
    }

    public void setOiid(String oiid) {
        this.oiid = oiid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}

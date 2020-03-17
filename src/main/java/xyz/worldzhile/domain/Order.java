package xyz.worldzhile.domain;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Order {


    /**
     * oid : 123
     * address : 池州火车站
     * totalMoney : 12.5
     * states : 0
     * name : 郭大侠
     * phone : 15956609697
     */

    private String oid;
    private String address;
    private Date time;
    private double totalMoney;
    private Integer states;
    private String name;
    private String phone;


    private String uid;



    private User user;

    private List<OrderItem> orderItems;




    public String getTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String time= sdf.format(this.time);//获取格式化日期，带有时分
        return time;
    }


    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getStates() {
        return states;
    }

    public void setStates(Integer states) {
        this.states = states;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Order{" +
                "oid='" + oid + '\'' +
                ", address='" + address + '\'' +
                ", time=" + time +
                ", totalMoney=" + totalMoney +
                ", states=" + states +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", uid='" + uid + '\'' +
                ", user=" + user +
                ", orderItems=" + orderItems +
                '}';
    }
}

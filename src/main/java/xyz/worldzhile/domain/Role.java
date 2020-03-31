package xyz.worldzhile.domain;

import java.util.Date;

public class Role {


    /**
     * username : lisi
     * role_name : role1
     */


    private  String rid;
    private String role_name;

    private Date createtime;

    private String desc;

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "rid='" + rid + '\'' +
                ", role_name='" + role_name + '\'' +
                ", createtime=" + createtime +
                ", desc='" + desc + '\'' +
                '}';
    }
}

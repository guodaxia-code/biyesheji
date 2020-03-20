package xyz.worldzhile.domain;

public class Permission {

    /**
     * role_name : role1
     * permission : VIP:update
     */

    private String role_name;
    private String permission;

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}

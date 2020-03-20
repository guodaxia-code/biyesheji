package xyz.worldzhile.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.worldzhile.dao.PermissionDao;
import xyz.worldzhile.dao.RoleDao;
import xyz.worldzhile.dao.UserDao;
import xyz.worldzhile.domain.User;

import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    PermissionDao permissionDao;

    //授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();

        Set<String> roles = getRolesByUsername(username);
        Set<String> permissions = getPermissionsByUsername(username);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    private Set<String> getPermissionsByUsername(String username) {
       return permissionDao.findPermissionsByUsername(username);
    }

    private Set<String> getRolesByUsername(String username) {
        return  roleDao.findRolesByUsername(username);
    }

    //认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        String username = (String) authenticationToken.getPrincipal();
        String password = getPasswordByUsername(username);

        if (password == null)
            return null;

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "MyRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(username));
        return authenticationInfo;
    }

    /**
     * 数据库查到的密码
     *
     * @param username
     * @return
     */
    private String getPasswordByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        if (user == null)  return null;
        return user.getPassword();
    }


    public static void main(String[] args) {
        Md5Hash md5Hash = new Md5Hash("1234567", "Mark");
        System.out.println(md5Hash.toString());
    }
}

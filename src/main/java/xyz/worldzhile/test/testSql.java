package xyz.worldzhile.test;

import xyz.worldzhile.constant.Constant;
import xyz.worldzhile.domain.Product;
import xyz.worldzhile.util.PageBean;
import xyz.worldzhile.util.UuidUtil;

public class testSql {
    public static void main(String[] args) {


        for (int i = 0; i <30 ; i++) {
            String uuid = UuidUtil.getUuid();
            System.out.println(uuid);
        }


    }
}

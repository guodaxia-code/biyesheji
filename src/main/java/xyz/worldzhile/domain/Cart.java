package xyz.worldzhile.domain;

import com.sun.org.apache.regexp.internal.RE;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Cart implements Serializable {

    /**
     * 购物车 pid
     */
    private Map<String,CartItem> cart=new HashMap<String,CartItem>();

    /**
     * 总金额
     */
    private float total=0.0f;


    public Cart() {

    }

    public Cart(Map<String, CartItem> cart, float total) {
        this.cart = cart;
        this.total = total;
    }

    public Map<String, CartItem> getCart() {
        return cart;
    }

    public void setCart(Map<String, CartItem> cart) {
        this.cart = cart;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }


    /**
     * 放回购物车里的商品
     * @param pid
     * @return
     */
    public CartItem getcartItem(String pid){
        return cart.get(pid);

    }

    /**
     * 获取单列商品
     */
    public  Collection<CartItem> getCartItems(){
        return cart.values();
    }

    /**
     * 添加商品
     */

    public void addToCart(CartItem cartItem){
        String pid = cartItem.getProduct().getPid();
        if (cart.containsKey(pid)){
            CartItem hasProduct = cart.get(pid);
            hasProduct.setCount( cartItem.getCount()+hasProduct.getCount());
        }else {
            cart.put(pid,cartItem);

        }
        total+= cartItem.getSubtotal();
    }

    /**
     *修改商品的数量
     */
    public void updateNumberByPid(String pid,Integer count){

        if (count<=0){
            System.out.println("购物车里的商品数量不能小于等于0");
            return;
        }
        CartItem cartItem = cart.get(pid);
        if (cartItem==null){
            System.out.println("购物车里没有此商品");
            return;
        }
        float removeSubtotal = cartItem.getSubtotal();

        total-=removeSubtotal;

        cartItem.setCount(count);

        total+=cartItem.getSubtotal();

    }

    /**
     * 获取购物车商品总数
     */
   public int getCartSubCount(){
       int cartSubCount=0;
        for (String pid:cart.keySet()){
            CartItem cartItem = cart.get(pid);
            cartSubCount+=cartItem.getCount();
        }
        return cartSubCount;
    }


    /**
     * 移除商品
     *
     */
    public void removeFromCart(String pid){
        CartItem hasProduct = cart.remove(pid);
        total-=hasProduct.getSubtotal();
    }

    /**
     * 清空购物车
     */
    public void cleanCart(){
        cart.clear();
        total=0.0f;
    }


}

package xyz.worldzhile.domain;

/**
 * 购物项 一个商品的购买信息
 */
public class CartItem {

    /**
     * 商品信息
     */
    private Product product;

    /**
     * 商品数量
     */
    private Integer count;

    private float subtotal;

    public CartItem(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }

    public float getSubtotal() {
        return product.getRel_price()*count;
    }


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}

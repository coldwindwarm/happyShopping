package com.happyShopping.model;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.Dynamic;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
@Entity
@Table(name = "tb_item")
public class Item implements Serializable {

    @Field("id")
    private Long id;
    @Field("item_title")
    private String title;

    private String sellPoint;

    @Field("item_price")
    private BigDecimal price;

    private Integer stockCount;

    private Integer num;

    private String barcode;

    @Field("item_image")
    private String image;

    private Long categoryid;


    private String status;

    private Date createTime;

    @Field("item_updatetime")
    private Date updateTime;


    private String itemSn;


    private BigDecimal costPirce;


    private BigDecimal marketPrice;

    private String isDefault;

    @Field("item_goodsid")
    private Long goodsId;

    private String sellerId;


    private String cartThumbnail;
    @Field("item_category")
    private String category;

    @Field("item_brand")
    private String brand;


    private String spec;

    @Field("item_seller")
    private String seller;

    /**
     *  动态域  item_spec_*  可以创建item_spec_*的字段
     */
    @Dynamic
    @Field("item_spec_*")
    private Map<String,String> specMap;

    public Map<String, String> getSpecMap() {
        return specMap;
    }

    public void setSpecMap(Map<String, String> specMap) {
        this.specMap = specMap;
    }
    private static final long serialVersionUID = 1L;

    /**
     * 获取商品id，同时也是商品编号
     *
     * @return id - 商品id，同时也是商品编号
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置商品id，同时也是商品编号
     *
     * @param id 商品id，同时也是商品编号
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商品标题
     *
     * @return title - 商品标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置商品标题
     *
     * @param title 商品标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取商品卖点
     *
     * @return sell_point - 商品卖点
     */
    public String getSellPoint() {
        return sellPoint;
    }

    /**
     * 设置商品卖点
     *
     * @param sellPoint 商品卖点
     */
    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint == null ? null : sellPoint.trim();
    }

    /**
     * 获取商品价格，单位为：元
     *
     * @return price - 商品价格，单位为：元
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置商品价格，单位为：元
     *
     * @param price 商品价格，单位为：元
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return stock_count
     */
    public Integer getStockCount() {
        return stockCount;
    }

    /**
     * @param stockCount
     */
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    /**
     * 获取库存数量
     *
     * @return num - 库存数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置库存数量
     *
     * @param num 库存数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取商品条形码
     *
     * @return barcode - 商品条形码
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 设置商品条形码
     *
     * @param barcode 商品条形码
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode == null ? null : barcode.trim();
    }

    /**
     * 获取商品图片
     *
     * @return image - 商品图片
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置商品图片
     *
     * @param image 商品图片
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取所属类目，叶子类目
     *
     * @return categoryId - 所属类目，叶子类目
     */
    public Long getCategoryid() {
        return categoryid;
    }

    /**
     * 设置所属类目，叶子类目
     *
     * @param categoryid 所属类目，叶子类目
     */
    public void setCategoryid(Long categoryid) {
        this.categoryid = categoryid;
    }

    /**
     * 获取商品状态，1-正常，2-下架，3-删除
     *
     * @return status - 商品状态，1-正常，2-下架，3-删除
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置商品状态，1-正常，2-下架，3-删除
     *
     * @param status 商品状态，1-正常，2-下架，3-删除
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return item_sn
     */
    public String getItemSn() {
        return itemSn;
    }

    /**
     * @param itemSn
     */
    public void setItemSn(String itemSn) {
        this.itemSn = itemSn == null ? null : itemSn.trim();
    }

    /**
     * @return cost_pirce
     */
    public BigDecimal getCostPirce() {
        return costPirce;
    }

    /**
     * @param costPirce
     */
    public void setCostPirce(BigDecimal costPirce) {
        this.costPirce = costPirce;
    }

    /**
     * @return market_price
     */
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    /**
     * @param marketPrice
     */
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * @return is_default
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault
     */
    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    /**
     * @return goods_id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * @param goodsId
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * @return seller_id
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    /**
     * @return cart_thumbnail
     */
    public String getCartThumbnail() {
        return cartThumbnail;
    }

    /**
     * @param cartThumbnail
     */
    public void setCartThumbnail(String cartThumbnail) {
        this.cartThumbnail = cartThumbnail == null ? null : cartThumbnail.trim();
    }

    /**
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category == null ? null : category.trim();
    }

    /**
     * @return brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    /**
     * @return spec
     */
    public String getSpec() {
        return spec;
    }

    /**
     * @param spec
     */
    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    /**
     * @return seller
     */
    public String getSeller() {
        return seller;
    }

    /**
     * @param seller
     */
    public void setSeller(String seller) {
        this.seller = seller == null ? null : seller.trim();
    }
}
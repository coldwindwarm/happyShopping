/**
 * FileName: GoodsGroup
 * Author:   coldwind
 * Date:     2018/8/13 15:48
 * Description: 商品组合类
 * History:
 * <author>          <time>          <version>          <desc>
 */
package entity;

import com.happyShopping.model.Goods;
import com.happyShopping.model.GoodsDesc;
import com.happyShopping.model.Item;

import java.io.Serializable;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈商品组合类〉
 *
 * @author coldwind
 * @create 2018/8/13
 * @since 1.0.0
 */
public class GoodsGroup implements Serializable{
    //商品
    private Goods goods;
    //商品描述
    private GoodsDesc goodsDesc;
    //SKU列表
    private List<Item> itemList;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsDesc getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(GoodsDesc goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }
}

package com.happyShopping.mapper;

import com.happyShopping.model.Goods;
import tk.mybatis.mapper.common.Mapper;

public interface GoodsMapper extends Mapper<Goods> {
    /**
     * 自定义插入商品
     * @param goods
     */
    void CustomInsert(Goods goods);
}
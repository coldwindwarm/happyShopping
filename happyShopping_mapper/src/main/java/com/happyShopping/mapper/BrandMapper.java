package com.happyShopping.mapper;

import com.happyShopping.model.Brand;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<Brand> {
    /**
     * 得到所有的品牌
     * @return
     */
    List<Brand> getAllBrand();

    /**
     * 类型模板的品牌下拉列表的数据
     * @return
     */
    List<Map> selectOptionList();
}
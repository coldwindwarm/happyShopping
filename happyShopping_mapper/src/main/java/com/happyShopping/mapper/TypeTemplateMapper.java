package com.happyShopping.mapper;

import com.happyShopping.model.TypeTemplate;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface TypeTemplateMapper extends Mapper<TypeTemplate> {
    /**
     * 添加分类的类型模板下拉列表的数据
     *
     * @return
     */
    List<Map> selectOptionList();
}
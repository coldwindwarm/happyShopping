package com.happyShopping.mapper;

import com.happyShopping.model.Specification;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper extends Mapper<Specification> {

    /**
     * 类型模板的品牌下拉列表的数据
     * @return
     */
    List<Map<String,Object>> selectOptionList();

    /**
     * 自定义的插入方法,可以得到id值
     * @param specification
     */
    void customInsert( Specification specification);




}
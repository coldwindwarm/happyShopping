package com.happyShopping.sellergoods.service;

import com.happyShopping.model.Brand;
import entity.PageResult;

import java.util.List;
import java.util.Map;

public interface BrandService {
    /**
     * 返回所有品牌
     * @return
     */
    public List<Brand> getAll();

    /**
     * 分页显示查看品牌
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageResult getBrandsByPage(int pageNum, int pageSize);

    /**
     * 增加品牌
     * @param brand
     */
    public void add(Brand brand);

    /**
     * 修改品牌信息
     * @param brand
     */
    public void update(Brand brand);

    /**
     * 通过id得到品牌
     * @param id
     */
    public Brand findBrandById(Long id);

    /**
     * 通过id删除
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 条件查询
     * @param brand
     * @param pageNum
     * @param pageSize
     */
    public PageResult search(Brand brand,int pageNum,int pageSize);

    /**
     * 类型模板的品牌下拉列表的数据
     * @return
     */
    List<Map> selectOptionList();
}

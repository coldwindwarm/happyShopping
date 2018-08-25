package com.happyShopping.content.service;

import java.util.List;


import com.happyShopping.model.ContentCategory;
import entity.PageResult;

/**
 * 服务层接口
 *
 * @author Administrator
 */
public interface ContentCategoryService {

    /**
     * 返回全部列表
     *
     * @return
     */
    public List<ContentCategory> findAll();


    /**
     * 返回分页列表
     *
     * @return
     */
    public PageResult findPage(int pageNum, int pageSize);


    /**
     * 增加
     */
    public void add(ContentCategory contentCategory);


    /**
     * 修改
     */
    public void update(ContentCategory contentCategory);


    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    public ContentCategory findOne(Long id);


    /**
     * 批量删除
     *
     * @param ids
     */
    public void delete(Long[] ids);

    /**
     * 分页
     *
     * @param pageNum  当前页 码
     * @param pageSize 每页记录数
     * @return
     */
    public PageResult findPage(ContentCategory contentCategory, int pageNum, int pageSize);

}

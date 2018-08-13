/**
 * FileName: BrandServiceImpl
 * Author:   coldwind
 * Date:     2018/8/2 22:38
 * Description: 品牌服务
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.happyShopping.mapper.BrandMapper;
import com.happyShopping.model.Brand;
import com.happyShopping.model.BrandExample;
import com.happyShopping.sellergoods.service.BrandService;
import entity.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br> 
 * 〈品牌服务〉
 *
 * @author coldwind
 * @create 2018/8/2
 * @since 1.0.0
 */
@Service
public class BrandServiceImpl implements BrandService{
    @Autowired
    private BrandMapper brandMapper;

    @Override
    public List<Brand> getAll() {
        return brandMapper.getAllBrand();
    }

    /**
     * 分页显示查看品牌
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageResult getBrandsByPage(int pageNum, int pageSize) {
        //执行分页
        PageHelper.startPage(pageNum,pageSize);
        Page<Brand> page = (Page<Brand>) brandMapper.selectByExample(null);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 增加品牌
     * @param brand
     */
    @Override
    public void add(Brand brand) {
        brandMapper.insert(brand);
    }

    /**
     * 修改品牌信息
     *
     * @param brand
     */
    @Override
    public void update(Brand brand) {
        brandMapper.updateByPrimaryKey(brand);
    }

    /**
     * 通过id得到品牌
     *
     * @param id
     */
    @Override
    public Brand findBrandById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    /**
     * 删除brand
     * @param ids
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            brandMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 条件查询
     * @param brand
     * @param pageNum
     * @param pageSize
     */
    @Override
    public PageResult search(Brand brand, int pageNum, int pageSize) {
        //分页
        PageHelper.startPage(pageNum,pageSize);

        //创建条件
        BrandExample brandExample = new BrandExample();
        BrandExample.Criteria criteria = brandExample.createCriteria();
        if (brand != null){
            if (brand.getName() != null && brand.getName().length() > 0){
                criteria.andNameLike(brand.getName());
            }
            if (StringUtils.isNotBlank(brand.getFirstChar())){
                criteria.andFirstCharEqualTo(brand.getFirstChar());
            }
        }

        Page<Brand> brandPage = (Page<Brand>) brandMapper.selectByExample(brandExample);
        //返回pageResult对象
        return new PageResult(brandPage.getTotal(),brandPage.getResult());
    }

    /**
     * 类型模板的品牌下拉列表的数据
     * @return
     */
    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}

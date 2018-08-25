package com.happyShopping.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import com.alibaba.druid.support.json.JSONParser;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.happyShopping.mapper.SpecificationOptionMapper;
import com.happyShopping.mapper.TypeTemplateMapper;
import com.happyShopping.model.SpecificationOption;
import com.happyShopping.model.SpecificationOptionExample;
import com.happyShopping.model.TypeTemplate;
import com.happyShopping.model.TypeTemplateExample;
import com.happyShopping.sellergoods.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


import entity.PageResult;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

    @Autowired
    private TypeTemplateMapper typeTemplateMapper;
    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TypeTemplate> findAll() {
        return typeTemplateMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        Page<TypeTemplate> page = (Page<TypeTemplate>) typeTemplateMapper.selectByExample(null);

        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TypeTemplate typeTemplate) {
        typeTemplateMapper.insert(typeTemplate);
    }


    /**
     * 修改
     */
    @Override
    public void update(TypeTemplate typeTemplate) {
        typeTemplateMapper.updateByPrimaryKey(typeTemplate);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TypeTemplate findOne(Long id) {
        return typeTemplateMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            typeTemplateMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(TypeTemplate typeTemplate, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TypeTemplateExample example = new TypeTemplateExample();
        TypeTemplateExample.Criteria criteria = example.createCriteria();

        if (typeTemplate != null) {
            if (typeTemplate.getName() != null && typeTemplate.getName().length() > 0) {
                criteria.andNameLike("%" + typeTemplate.getName() + "%");
            }
            if (typeTemplate.getSpecIds() != null && typeTemplate.getSpecIds().length() > 0) {
                criteria.andSpecIdsLike("%" + typeTemplate.getSpecIds() + "%");
            }
            if (typeTemplate.getBrandIds() != null && typeTemplate.getBrandIds().length() > 0) {
                criteria.andBrandIdsLike("%" + typeTemplate.getBrandIds() + "%");
            }
            if (typeTemplate.getCustomAttributeItems() != null && typeTemplate.getCustomAttributeItems().length() > 0) {
                criteria.andCustomAttributeItemsLike("%" + typeTemplate.getCustomAttributeItems() + "%");
            }

        }

        Page<TypeTemplate> page = (Page<TypeTemplate>) typeTemplateMapper.selectByExample(example);
        //缓存到redis
        saveToRedis();
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 添加分类的类型模板下拉列表的数据
     *
     * @return
     */
    @Override
    public List<Map> selectOptionList() {
        return typeTemplateMapper.selectOptionList();
    }

    /**
     * 通过id获得规格选项列表
     *
     * @param id
     * @return
     */
    @Override
    public List<Map> findSpecList(Long id) {
        //查询模板
        TypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
        List<Map> maps = JSON.parseArray(typeTemplate.getSpecIds(), Map.class);
        for (Map map : maps) {
            //给map得到格式如下[{id:"",text:"",options:[]}]
            SpecificationOptionExample example = new SpecificationOptionExample();
            SpecificationOptionExample.Criteria criteria = example.createCriteria();
            //转换类型,查询模板下specIds中的id值对应的模板规格选项
            criteria.andSpecIdEqualTo(new Long((Integer) map.get("id")));
            List<SpecificationOption> options = specificationOptionMapper.selectByExample(example);
            map.put("options", options);
        }
        return maps;
    }

    /**
     * 将品牌和规格缓存存入redis
     */
    private void saveToRedis() {
        List<TypeTemplate> typeTemplates = findAll();
        for (TypeTemplate typeTemplate : typeTemplates) {
            //将品牌缓存到redis
            List<Map> brandList = JSON.parseArray(typeTemplate.getBrandIds(), Map.class);
            redisTemplate.boundHashOps("brandList").put(typeTemplate.getId(), brandList);
            //将规格缓存到redis
            List<Map> specList = findSpecList(typeTemplate.getId());
            redisTemplate.boundHashOps("specList").put(typeTemplate.getId(), specList);
        }
        System.out.println("将品牌和规格缓存存入redis");
    }
}

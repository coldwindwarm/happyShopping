package com.happyShopping.sellergoods.service.impl;

import java.util.List;
import java.util.Map;

import com.happyShopping.mapper.SpecificationMapper;
import com.happyShopping.mapper.SpecificationOptionMapper;
import com.happyShopping.model.Specification;
import com.happyShopping.model.SpecificationExample;
import com.happyShopping.model.SpecificationOption;
import com.happyShopping.model.SpecificationOptionExample;
import com.happyShopping.sellergoods.service.SpecificationService;
import entity.SpecificationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import entity.PageResult;
import tk.mybatis.mapper.entity.Example;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class SpecificationServiceImpl implements SpecificationService {

    @Autowired
    private SpecificationMapper specificationMapper;


    @Autowired
    private SpecificationOptionMapper specificationOptionMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Specification> findAll() {
        return specificationMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Specification> page = (Page<Specification>) specificationMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(SpecificationGroup specificationGroup) {

        //增加规格
        specificationMapper.customInsert(specificationGroup.getSpecification());

        //遍历出规格的规格选项,先设置其对应的规格id,再设置进去
        for (SpecificationOption specificationOption : specificationGroup.getSpecificationOptionList()) {
            specificationOption.setSpecId(specificationGroup.getSpecification().getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }


    /**
     * 修改
     */
    @Override
    public void update(SpecificationGroup specificationGroup) {
        //删除规格下的规格选项,重新添加从前端传过来的规格选项
        //先修改规格
        specificationMapper.updateByPrimaryKey(specificationGroup.getSpecification());

        //删除原有的规格选项
        SpecificationOptionExample example = new SpecificationOptionExample();
        SpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(specificationGroup.getSpecification().getId());
        specificationOptionMapper.deleteByExample(example);

        //添加从前端传过来的规格选项
        for (SpecificationOption specificationOption : specificationGroup.getSpecificationOptionList()) {
            specificationOption.setSpecId(specificationGroup.getSpecification().getId());
            specificationOptionMapper.insert(specificationOption);
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public SpecificationGroup findOne(Long id) {
        //得到规格
        Specification specification = specificationMapper.selectByPrimaryKey(id);

        //找出这个规格下的所有规格选项
        SpecificationOptionExample example = new SpecificationOptionExample();
        SpecificationOptionExample.Criteria criteria = example.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<SpecificationOption> specificationOptions = specificationOptionMapper.selectByExample(example);

        //将规格及其规格选项加入到组合的实体类中
        SpecificationGroup specificationGroup = new SpecificationGroup();
        specificationGroup.setSpecification(specification);
        specificationGroup.setSpecificationOptionList(specificationOptions);
        return specificationGroup;
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            specificationMapper.deleteByPrimaryKey(id);
            //删除规格下的规格选项
            SpecificationOptionExample example = new SpecificationOptionExample();
            SpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(id);
            specificationOptionMapper.deleteByExample(example);
        }
    }


    @Override
    public PageResult findPage(Specification specification, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        SpecificationExample example = new SpecificationExample();
        SpecificationExample.Criteria criteria = example.createCriteria();

        if (specification != null) {
            if (specification.getSpecName() != null && specification.getSpecName().length() > 0) {
                criteria.andSpecNameLike("%" + specification.getSpecName() + "%");
            }

        }

        Page<Specification> page = (Page<Specification>) specificationMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public List<Map<String, Object>> selectOptionList() {
        return specificationMapper.selectOptionList();
    }

}

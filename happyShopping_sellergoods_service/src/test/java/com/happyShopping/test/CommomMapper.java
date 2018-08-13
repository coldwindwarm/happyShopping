/**
 * FileName: CommomMapper
 * Author:   coldwind
 * Date:     2018/8/5 14:50
 * Description: 通用mapper测试
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.test;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.happyShopping.mapper.BrandMapper;
import com.happyShopping.model.Brand;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈通用mapper测试〉
 *
 * @author coldwind
 * @create 2018/8/5
 * @since 1.0.0
 */
public class CommomMapper {
    private ApplicationContext applicationContext;
    private BrandMapper brandMapper;

    @Before
    public void init() {
        applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/spring*.xml");
        //获取代理类
        brandMapper = applicationContext.getBean(BrandMapper.class);
    }

    /**
     * 测试插入值,不忽略空值
     */
    @Test
    public void testInsert() {
        Brand brand = new Brand();
        brand.setName("突破自己");
        int i = brandMapper.insert(brand);
        System.out.println(i);
    }

    /**
     * 测试插入值,忽略空值
     */
    @Test
    public void testInsertSelective() {
        Brand brand = new Brand();
        brand.setName("突破自己...耐心点");
        int i = brandMapper.insertSelective(brand);
        System.out.println(i);
    }

    /**
     * 测试更新值,不忽略空值
     */
    @Test
    public void testUpdateByPrimaryKey() {
        Brand brand = new Brand();
        brand.setId(20L);
        brand.setName("大厂");
        int i = brandMapper.updateByPrimaryKey(brand);
        System.out.println(i);
    }

    /**
     * 测试更新值,忽略空值
     */
    @Test
    public void testUpdateByPrimaryKeySelective() {
        Brand brand = new Brand();
        brand.setId(20L);
        brand.setName("东芝");
        int i = brandMapper.updateByPrimaryKeySelective(brand);
        System.out.println(i);
    }

    /**
     * 构造修改条件,不忽略空值
     */
    public void testUpdateByExample() {
        Brand brand = new Brand();
        brand.setName("七匹狼");
        //创建条件Example对象
        Example example = new Example(Brand.class);
        //criteria 构造修改条件
        Example.Criteria criteria = example.createCriteria();
        //第一个参数:Brand对应的属性值  第二个参数:属性约束值 相当于where firstChar='S'
        criteria.andEqualTo("firstChar", "S");

        //传入条件修改数据,i为被影响数据的条数
        int i = brandMapper.updateByExample(brand, example);
        System.out.println(i);
    }

    /**
     * 构造修改条件,忽略空值
     */
    public void testUpdateByExampleBySelective() {
        Brand brand = new Brand();
        brand.setName("八匹狼");
        brand.setFirstChar("B");
        //创建条件Example对象
        Example example = new Example(Brand.class);
        //criteria 构造修改条件
        Example.Criteria criteria = example.createCriteria();
        //第一个参数:Brand对应的属性值  第二个参数:属性约束值 相当于where firstChar='S'
        criteria.andEqualTo("firstChar", "S");

        //传入条件修改数据,i为被影响数据的条数
        int i = brandMapper.updateByExampleSelective(brand, example);
        System.out.println(i);
    }

    /**
     * 查询操作--根据主键查询
     */
    public void testSelectByPrimaryKey() {
        Long id = 5L;
        Brand brand = brandMapper.selectByPrimaryKey(id);
        System.out.println(brand);
    }

    /**
     * 查询单条记录
     */
    @Test
    public void testSelectOne() {
        Brand brand = new Brand();
        brand.setId(5L);
        brand.setName("八匹狼");
        Brand brand1 = brandMapper.selectOne(brand);
        System.out.println(brand1);
    }

    /**
     * 统计查询
     */
    @Test
    public void testSelectCount() {
        //查询总记录数
        int i = brandMapper.selectCount(null);
        System.out.println(i);
    }

    /**
     * 分页工具查询
     */
    @Test
    public void testPage() {
        //pageNum表示当前页,pageSize当前页显示的数据条数
        int pageNum = 1;
        int pageSize = 10;
        PageHelper.startPage(pageNum, pageSize);
        //查询所有品牌
        List<Brand> brands = brandMapper.selectAll();
        //分页处理,只需调用PageHelper.startPage静态方法即可
        PageInfo<Brand> pageInfo = new PageInfo<Brand>(brands);
        System.out.println(pageInfo);
    }


}

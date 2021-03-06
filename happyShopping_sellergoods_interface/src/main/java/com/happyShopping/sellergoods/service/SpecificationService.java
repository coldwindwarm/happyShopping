package com.happyShopping.sellergoods.service;
import java.util.List;
import java.util.Map;

import com.happyShopping.model.Specification;
import entity.PageResult;
import entity.SpecificationGroup;

/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SpecificationService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<Specification> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(SpecificationGroup specificationGroup);
	
	
	/**
	 * 修改
	 */
	public void update(SpecificationGroup specificationGroup);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public SpecificationGroup findOne(Long id);
	
	
	/**
	 * 批量删除
	 * @param ids
	 */
	public void delete(Long[] ids);

	/**
	 * 分页
	 * @param pageNum 当前页 码
	 * @param pageSize 每页记录数
	 * @return
	 */
	public PageResult findPage(Specification specification, int pageNum, int pageSize);


	/**
	 * 类型模板的品牌下拉列表的数据
	 * @return
	 */
	List<Map<String,Object>> selectOptionList();
}

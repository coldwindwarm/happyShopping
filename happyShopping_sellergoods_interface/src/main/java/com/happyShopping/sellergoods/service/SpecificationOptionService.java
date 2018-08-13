package com.happyShopping.sellergoods.service;
import java.util.List;

import com.happyShopping.model.SpecificationOption;
import entity.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface SpecificationOptionService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<SpecificationOption> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(SpecificationOption specificationOption);
	
	
	/**
	 * 修改
	 */
	public void update(SpecificationOption specificationOption);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public SpecificationOption findOne(Long id);
	
	
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
	public PageResult findPage(SpecificationOption specificationOption, int pageNum, int pageSize);
	
}

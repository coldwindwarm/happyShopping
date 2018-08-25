package com.happyShopping.sellergoods.service;
import java.util.List;

import com.happyShopping.model.Goods;
import com.happyShopping.model.Item;
import entity.GoodsGroup;
import entity.PageResult;
/**
 * 服务层接口
 * @author Administrator
 *
 */
public interface GoodsService {

	/**
	 * 返回全部列表
	 * @return
	 */
	public List<Goods> findAll();
	
	
	/**
	 * 返回分页列表
	 * @return
	 */
	public PageResult findPage(int pageNum, int pageSize);
	
	
	/**
	 * 增加
	*/
	public void add(Goods goods);
	
	
	/**
	 * 修改
	 */
	public void update(GoodsGroup goodsGroup);
	

	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	public GoodsGroup findOne(Long id);
	
	
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
	public PageResult findPage(Goods goods, int pageNum, int pageSize);
	/**
	 * 商品录入
	 */
	public void add(GoodsGroup goodsGroup);

	/**
	 * 修改状态
	 * @param ids
	 */
	public void updateStatus(Long[] ids,String status);


	/**
	 * 根据SPUid和状态获取SKU列表
	 * @param goodsIds
	 * @param status
	 * @return
	 */
	public List<Item> findItemListByGoodsIdAndStatus(Long[] goodsIds,String status);
}

package com.happyShopping.sellergoods.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.happyShopping.mapper.*;
import com.happyShopping.model.*;
import com.happyShopping.sellergoods.service.GoodsService;
import entity.GoodsGroup;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;


import entity.PageResult;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsDescMapper goodsDescMapper;
    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private SellerMapper sellerMapper;
    @Autowired
    private ItemMapper itemMapper;

    /**
     * 查询全部
     */
    @Override
    public List<Goods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Goods goods) {
        goodsMapper.insert(goods);
    }


    /**
     * 修改
     */
    @Override
    public void update(GoodsGroup goodsGroup) {

        //设置未申请状态:如果是经过修改的商品，需要重新设置状态
        goodsGroup.getGoods().setAuditStatus("0");
        //保存商品
        goodsMapper.updateByPrimaryKey(goodsGroup.getGoods());
        //保存商品描述
        goodsDescMapper.updateByPrimaryKey(goodsGroup.getGoodsDesc());

        //删除原来所有的SKU:itemList
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(goodsGroup.getGoods().getId());
        itemMapper.deleteByExample(example);


        //插入SKU
        saveItems(goodsGroup);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public GoodsGroup findOne(Long id) {
        //设置组合GoodsGroup的goods和goodDesc  基本信息
        GoodsGroup goodsGroup = new GoodsGroup();
        Goods goods = goodsMapper.selectByPrimaryKey(id);
        GoodsDesc goodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goodsGroup.setGoods(goods);
        goodsGroup.setGoodsDesc(goodsDesc);

        //设置SKU信息
        ItemExample example = new ItemExample();
        ItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<Item> items = itemMapper.selectByExample(example);
        goodsGroup.setItemList(items);


        return goodsGroup;
    }

    /**
     * 批量删除(逻辑删除,即修改状态isDelete为1)
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            Goods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setIsDelete("1");
            goodsMapper.updateByPrimaryKey(goods);
        }
    }


    @Override
    public PageResult findPage(Goods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        GoodsExample example = new GoodsExample();
        GoodsExample.Criteria criteria = example.createCriteria();
        //非删除状态
        criteria.andIsDeleteIsNull();
        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                //改为相等
                criteria.andSellerIdEqualTo(goods.getSellerId());
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<Goods> page = (Page<Goods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 商品录入
     *
     * @param goodsGroup
     */
    @Override
    public void add(GoodsGroup goodsGroup) {
        //插入商品和商品描述
        //设置商品未申请状态
        goodsGroup.getGoods().setAuditStatus("0");
        goodsMapper.CustomInsert(goodsGroup.getGoods());

        goodsGroup.getGoodsDesc().setGoodsId(goodsGroup.getGoods().getId());
        goodsDescMapper.insert(goodsGroup.getGoodsDesc());

        saveItems(goodsGroup);
    }

    /**
     * 修改状态
     *
     * @param ids
     */
    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            Goods goods = goodsMapper.selectByPrimaryKey(id);
            goods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKey(goods);
        }
    }

    /**
     * 添加SKU
     *
     * @param goodsGroup
     */
    private void saveItems(GoodsGroup goodsGroup) {
        //当开启规格的时候
        if ("1".equals(goodsGroup.getGoods().getIsEnableSpec())) {
            for (Item item : goodsGroup.getItemList()) {
                //构建标题 SPU名称+规格选项值
                String title = goodsGroup.getGoods().getGoodsName();
                //将item的spec字符串转换为一个map
                JSONObject map = JSON.parseObject(item.getSpec());
                for (String name : map.keySet()) {
                    title += "  " + map.get(name);
                }
                item.setTitle(title);
                setItemValues(item, goodsGroup);
                itemMapper.insert(item);
            }
        } else {
            //不开启规格,新建一个item,设置它的基本属性
            Item item = new Item();
            //库存
            item.setNum(99999);
            //是否默认
            item.setIsDefault("1");
            //状态
            item.setStatus("1");
            //规格选项
            item.setSpec("{}");
            //价格
            item.setPrice(goodsGroup.getGoods().getPrice());
            //标题
            item.setTitle(goodsGroup.getGoods().getGoodsName());
            setItemValues(item, goodsGroup);
            itemMapper.insert(item);
        }
    }

    private void setItemValues(Item item, GoodsGroup goodsGroup) {
        //设置图片,取第一张图片
        List<Map> maps = JSON.parseArray(goodsGroup.getGoodsDesc().getItemImages(), Map.class);
        if (maps.size() > 0) {
            item.setImage((String) maps.get(0).get("url"));
        }
        //设置分类名称和分类id
        ItemCat itemCat = itemCatMapper.selectByPrimaryKey(goodsGroup.getGoods().getCategory3Id());
        item.setCategory(itemCat.getName());
        item.setCategoryid(goodsGroup.getGoods().getCategory3Id());

        //设置商品ID
        item.setGoodsId(goodsGroup.getGoods().getId());

        //更新日期,创建日期
        item.setUpdateTime(new Date());
        item.setCreateTime(new Date());
        //品牌名称
        Brand brand = brandMapper.selectByPrimaryKey(goodsGroup.getGoods().getBrandId());
        item.setBrand(brand.getName());
        //商家名称(店铺名称)
        Seller seller = sellerMapper.selectByPrimaryKey(goodsGroup.getGoods().getSellerId());
        item.setSeller(seller.getNickName());
    }
}

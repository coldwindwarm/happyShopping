package com.happyShopping.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;


import com.happyShopping.model.Goods;
import com.happyShopping.sellergoods.service.GoodsService;
import entity.GoodsGroup;
import entity.PageResult;
import entity.Result;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Reference
    private GoodsService goodsService;

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<Goods> findAll() {
        return goodsService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return goodsService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param goodsGroup
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody GoodsGroup goodsGroup) {
        //给商品设置商家id
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        goodsGroup.getGoods().setSellerId(name);
        try {
            goodsService.add(goodsGroup);
            return new Result(true, "增加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "增加失败");
        }
    }

    /**
     * 修改
     *
     * @param goodsGroup
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody GoodsGroup goodsGroup) {
        //检验是否为当前商家的id
        GoodsGroup goodsGroup2 = goodsService.findOne(goodsGroup.getGoods().getId());
        //获取当前登陆的商家id
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        //1.检验当前商品的id是否为该商家   2.检验当前登陆商家是否为该商品的商家名称
        //如果传递过来的商家ID并不是当前登录的用户的ID,则属于非法操作
        if (!goodsGroup2.getGoods().getSellerId().equals(sellerId) || !goodsGroup.getGoods().getSellerId().equals(sellerId)) {
            return new Result(false,"非法操作");
        }
        try {
            goodsService.update(goodsGroup);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 获取实体
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public GoodsGroup findOne(Long id) {
        return goodsService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            goodsService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 查询+分页
     *
     * @param goods
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Goods goods, int page, int rows) {
        //只查询自己商家的商品
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        goods.setSellerId(sellerId);
        return goodsService.findPage(goods, page, rows);
    }

}

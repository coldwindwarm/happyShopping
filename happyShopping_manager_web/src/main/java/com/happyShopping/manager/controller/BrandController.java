/**
 * FileName: BrandController
 * Author:   coldwind
 * Date:     2018/8/3 8:50
 * Description: 品牌的controller
 * History:
 * <author>          <time>          <version>          <desc>
 */
package com.happyShopping.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.happyShopping.model.Brand;
import com.happyShopping.sellergoods.service.BrandService;
import entity.PageResult;
import entity.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈品牌的controller〉
 *
 * @author coldwind
 * @create 2018/8/3
 * @since 1.0.0
 */
@RestController
@RequestMapping("/brand")
public class BrandController {
    @Reference
    private BrandService brandService;

    /**
     * 获得所有的品牌
     *
     * @return
     */
    @RequestMapping("/list")
    public List<Brand> list() {
        return brandService.getAll();
    }

    /**
     * 分页展示品牌
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/listByPage")
    public PageResult listByPage(int pageNum, int pageSize) {
        return brandService.getBrandsByPage(pageNum, pageSize);
    }

    /**
     * 增加品牌
     *
     * @param brand
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody Brand brand) {
        try {
            brandService.add(brand);
            return new Result(true, "新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "新增失败");
        }
    }

    /**
     * 修改品牌
     *
     * @param brand
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Brand brand) {
        try {
            brandService.update(brand);
            return new Result(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "修改失败");
        }
    }

    /**
     * 通过id找到brand
     *
     * @param id
     * @return
     */
    @RequestMapping("/findBrandById")
    public Brand findBrandById(Long id) {
        return brandService.findBrandById(id);
    }

    /**
     * 批量删除品牌
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            brandService.delete(ids);
            return new Result(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "删除失败");
        }
    }

    /**
     * 条件查询
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody Brand brand,int pageNum,int pageSize){
        return brandService.search(brand,pageNum,pageSize);
    }
    /**
     * 类型模板的品牌下拉列表的数据
     * @return
     */
    @RequestMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return brandService.selectOptionList();
    }
}

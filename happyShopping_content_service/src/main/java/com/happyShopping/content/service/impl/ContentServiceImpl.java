package com.happyShopping.content.service.impl;

import java.util.List;

import com.happyShopping.content.service.ContentService;
import com.happyShopping.mapper.ContentMapper;
import com.happyShopping.model.Content;
import com.happyShopping.model.ContentExample;
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
public class ContentServiceImpl implements ContentService {

    @Autowired
    private ContentMapper contentMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<Content> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<Content> page = (Page<Content>) contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(Content content) {

        contentMapper.insert(content);
        //添加后要刷新缓存,和数据库同步
        redisTemplate.boundHashOps("content").delete(content.getCategoryId());
    }


    /**
     * 修改
     */
    @Override
    public void update(Content content) {
        //修改后要刷新缓存 考虑到可能会修改广告分类,所以要刷新原来分类的缓存和刷新后来分类的缓存
        Long categoryId = contentMapper.selectByPrimaryKey(content.getId()).getCategoryId();
        redisTemplate.boundHashOps("content").delete(categoryId);
        contentMapper.updateByPrimaryKey(content);
//        categoryId.longValue   将Long转为long的值  刷新后来分类的缓存
        if (categoryId.longValue() != content.getCategoryId().longValue()) {
            redisTemplate.boundHashOps("content").delete(content.getCategoryId());
        }
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public Content findOne(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            //删除后要刷新缓存,和数据库同步
            redisTemplate.boundHashOps("content").delete(contentMapper.selectByPrimaryKey(id).getCategoryId());
            contentMapper.deleteByPrimaryKey(id);
        }
    }


    @Override
    public PageResult findPage(Content content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        ContentExample example = new ContentExample();
        ContentExample.Criteria criteria = example.createCriteria();

        if (content != null) {
            if (content.getTitle() != null && content.getTitle().length() > 0) {
                criteria.andTitleLike("%" + content.getTitle() + "%");
            }
            if (content.getUrl() != null && content.getUrl().length() > 0) {
                criteria.andUrlLike("%" + content.getUrl() + "%");
            }
            if (content.getPic() != null && content.getPic().length() > 0) {
                criteria.andPicLike("%" + content.getPic() + "%");
            }
            if (content.getStatus() != null && content.getStatus().length() > 0) {
                criteria.andStatusLike("%" + content.getStatus() + "%");
            }

        }

        Page<Content> page = (Page<Content>) contentMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 通过广告分类的id得到广告
     *
     * @param categoryId
     * @return
     */
    @Override
    public List<Content> findByCatergoryId(Long categoryId) {
        //先通过redis缓存中获取对应的广告
        List<Content> contentList = (List<Content>) redisTemplate.boundHashOps("content").get(categoryId);
        if (contentList == null) {
            System.out.println("从数据库读出来数据并存入缓存中");
            ContentExample example = new ContentExample();
            ContentExample.Criteria criteria = example.createCriteria();
            //设置content的广告分类id和是有效状态
            criteria.andCategoryIdEqualTo(categoryId);
            criteria.andStatusEqualTo("1");
            contentList = contentMapper.selectByExample(example);
            //存入缓存中
            redisTemplate.boundHashOps("content").put(categoryId, contentList);
        } else {
            System.out.println("从缓存中获取");
        }
        return contentList;
    }

}

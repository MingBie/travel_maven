package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.CategoryDao;
import com.zzxx.travel.dao.impl.CategoryDaoImpl;
import com.zzxx.travel.domain.Category;
import com.zzxx.travel.service.CategoryService;
import com.zzxx.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao cd = new CategoryDaoImpl();

    // 查询导航栏信息(属于静态信息，可以存入缓存中)
    @Override
    public List<Category> findAll() {
        // 从缓存中查询数据
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> categorys = jedis.zrangeWithScores("category_travel", 0, -1);
        if (categorys == null || categorys.size() == 0) {
            // 缓存中不存在 去数据库中查找
            // 并把 导航栏信息存入缓存中
            List<Category> list = cd.findAll();
            for(Category category : list) {
                jedis.zadd("category_travel", category.getCid(), category.getCname());
            }
            return list;
        } else {
            // 缓存中存在 查找并返回
            // Set<Tuple> -> list<Category>
            List<Category> list = new ArrayList<>();
            for(Tuple tuple : categorys) {
                Category category = new Category();
                category.setCid((int) tuple.getScore());
                category.setCname(tuple.getElement());
                list.add(category);
            }
            jedis.close();
            return list;
        }
    }
}

package com.zhangbin.dao;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zhangbin.pojo.Blog;

/**
 * 认认真真敲代码，开开心心每一天
 *
 * @Date 2020/7/14-16:07
 */
public interface BlogMapper {
    //添加数据
    int addBlog(Blog blog);
}

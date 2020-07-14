package com.zhangbin.dao;


import com.zhangbin.pojo.Blog;
import com.zhangbin.utils.IdUtils;
import com.zhangbin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;

/**
 * 认认真真敲代码，开开心心每一天
 *
 * @Date 2020/6/21-15:57
 */
public class userDaoTest {
    @Test
    public void addBlog(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        Blog blog = new Blog();
        blog.setId(IdUtils.getId());
        blog.setTitle("MyBatis简单");
        blog.setAuthor("张斌");
        blog.setCreateTime(new Date());
        blog.setViews(999);

        mapper.addBlog(blog);

        blog.setId(IdUtils.getId());
        blog.setTitle("java简单");
        mapper.addBlog(blog);

        blog.setId(IdUtils.getId());
        blog.setTitle("spring");
        mapper.addBlog(blog);

        blog.setId(IdUtils.getId());
        blog.setTitle("springBoot简单");
        mapper.addBlog(blog);


        sqlSession.close();
    }
}

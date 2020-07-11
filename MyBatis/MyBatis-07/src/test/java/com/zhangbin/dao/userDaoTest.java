package com.zhangbin.dao;

import com.zhangbin.pojo.Student;
import com.zhangbin.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

/**
 * 认认真真敲代码，开开心心每一天
 *
 * @Date 2020/6/21-15:57
 */
public class userDaoTest {
    @Test
    public void getStudent(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        Student student = mapper.getStudent(1);
        System.out.println(student);
        sqlSession.close();
    }
}

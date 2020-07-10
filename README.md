# My-Batis
MyBatis-01:普通的增删改查，使用注解完成普通SQL

MyBatis-02:配置文件设置
    
     一：使用properties标签调用db.properties动态传入数据库连接的数据 这样就不用单独配置多个环境了
    
     二：配置设置，配置日志等
          <settings>
                 <!--使用默认日志-->
                 <setting name="logImpl" value="STDOUT_LOGGING"/>
           </settings>
           
     三：类型别名，定义好别名后直接在文件中使用别名即可 注意不区分大小写
         <typeAliases>
            <!--1.定义一个实体类别名-->
            <!--<typeAlias type="com.com.monkey.pojo.user" alias="User"/>-->
    
            <!--2.扫描包下所有的实体类名称，XML中的数据类型和返回值类型直接填写实体类名称即可-->
            <package name="com.zhangbin.pojo"/>
    
            <!--3.使用注解@Alias直接定义别名使用-->
         </typeAliases>
         
     四：配置开发的多种环境，default指定那个环境就是那个环境
          <environments default="development">
      
              <!--本地环境-->
              <environment id="development">
                  <transactionManager type="JDBC"/>
                  <dataSource type="POOLED">
                      <property name="driver" value="${driver}"/>
                      <property name="url" value="${url}"/>
                      <property name="username" value="${username}"/>
                      <property name="password" value="${password}"/>
                  </dataSource>
              </environment>
      
              <!--测试环境-->
              <environment id="test">
                  <transactionManager type="JDBC"/>
                  <dataSource type="POOLED">
                      <property name="driver" value="${driver}"/>
                      <property name="url" value="${url}"/>
                      <property name="username" value="${username}"/>
                      <property name="password" value="${password}"/>
                  </dataSource>
              </environment>
          </environments>  
     五：绑定接口
         <!--每一个mapper.xml都需要在mybatis核心配置文件中注册-->
         <mappers>
             <!--1.resource绑定的是XML文件    【建议使用，有一个写一个mapper】-->
             <mapper resource="com/zhangbin/dao/UserMapper.xml"></mapper>
             <!--2.class绑定的是接口-->
             <!--使用说明：接口和他的mapper配置文件必须同名-->
             <!--接口和他的mapper配置文件必须在同一个包下-->
             <!--使用注解需要配置接口，注意XML和接口不能配在一个mapper里，否则会报错-->
             <mapper class="com.zhangbin.dao.UserMapperInteFace"></mapper>
     
             <!--3.使用扫描包-->
             <!--使用说明：接口和他的mapper配置文件必须同名-->
             <!--接口和他的mapper配置文件必须在同一个包下-->
             <!--<package name="com.com.monkey.com.com.monkey.dao"/>-->
         </mappers>      
MyBatis-03:数据库字段和实体类字段不相等处理方法
    
    方式一：     
    <!--2、定义一个resultMap   其中id：SQL语句中定义的resultMap值，type：实体类类型；-->
    <resultMap id="userMap" type="user">
        <!--3、column：数据库列名  property:实体类名称-->
        <result column="name" property="username"></result>
    </resultMap>
    
    
    <!--1、使用resultMap解决数据库字段和实体类字段不统一的问题-->
    <select id="getUserList" resultMap="userMap">
      select * from dayu.userdata
    </select>
    
    方式二：
     <!--注意！！！！也可以使用修改SQL语句中的*，将不同的字段添加别名处理。不建议使用-->

MyBatis-04:两种分页方式
    
    方式一：
    <!--RowBounds分页-->
    
    方式二：
    <!--limit分页-->
    有时间看：MyBatis 分页插件 PageHelper
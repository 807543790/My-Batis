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
    
MyBatis-05:使用注解增删改查
        
    学习一：给openSession设置参数为true时，会自动提交事务，不需要自己提交。      
    public static SqlSession getSqlSession(){
        //参数传入true时，会自动提交事务
        return  sqlSessionFactory.openSession(true);
    }
    学习二：
    #{}和${}区别
    
    #{}可以防止SQL注入问题，但是${}不行。
    ${}一般是传入数据库对象使用的，比如数据库名称
MyBatis-06:lombok插件

    @Getter and @Setter
    @FieldNameConstants
    @ToString
    @EqualsAndHashCode
    @**AllArgsConstructor**：生成有参数的构造方法, @RequiredArgsConstructor and @N**oArgsConstructor**：生成无参数的构造方法
    @Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
    **@Data**：生成get，set，toString方法
    @Builder
    @SuperBuilder
    @Singular
    @Delegate
    @Value
    @Accessors
    @Wither
    @With
    @SneakyThrows
    @val
    @var
    experimental @var
    @UtilityClass
    Lombok config system
    
MyBatis-07:多对一
    
     1.创建实体类
     2.编写mapper接口或者XML文件
     3.在配置文件中绑定接口或者XML文件
     4.进行测试
       
     多对一的两种查询：
      
         <!--思路一：嵌套查询（子查询）-->
             <!--一：查询所有的学生数据-->
             <select id="getStudentTeacher" resultMap="Teacher">
                 SELECT * FROM student
             </select>
     
             <!--二：根据查询出来学生数据作为条件再查询对应的老师数据-->
             <select id="getTeacher" resultType="Teacher">
                 select * from teacher where id = #{id}
             </select>
             <!--三：定义resultMap标签  id对应select标签中的resultMap值-->
             <resultMap id="Teacher" type="Student">
                 <!--将对应的实体类字段和数据库字段做对应-->
                 <result property="id" column="id"></result>
                 <result property="name" column="name"></result>
                 <!--使用association标签，做实体类和数据库字段对应。 association（关联）标签(查询字段是实体类事使用的标签)
                 javaType为对应的实体类类型，select对应的是子查询select标签的id-->
                 <association property="teacher" column="id" javaType="Teacher" select="getTeacher"/>
             </resultMap>
     
         <!--思路二：结果嵌套查询-->
             <!--一：查询所有的学生和老师的数据-->
             <select id="getStudentTeacher2" resultMap="StudentMap">
                 select s.id sid,s.name sname, t.name tname,t.id tid from student s,teacher t where t.id =  s.tid;
             </select>
             <!--二：定义resultMap标签  id对应select标签中的resultMap值-->
             <resultMap id="StudentMap" type="Student">
                 <!--将对应的实体类字段和数据库字段做对应-->
                 <result property="id" column="sid"></result>
                 <result property="name" column="sname"></result>
                 <result property="id" column="sid"></result>
                 <!--使用association标签，做实体类和数据库字段对应。 association（关联）标签(查询字段是实体类事使用的标签)
                 javaType为对应的实体类类型，select对应的是子查询select标签的id-->
                 <association property="teacher" javaType="Teacher">
                     <result property="name" column="tname"></result>
                     <result property="id" column="tid"></result>
                 </association>
             </resultMap>
             
MyBatis-07:一对多   

            <!--   方式一   -->
                <!--一对多查询  -->
                <select id="getTeacher" resultMap="teacherMap">
                    select t.name tname,t.id tid,s.id sid,s.name sname  from teacher t, student s where t.id = s.tid and t.id = #{tid};
                </select>
        
                <!--id对应resultMap中的定义值 返回值类型为Teacher-->
                <resultMap id="teacherMap" type="Teacher">
                    <result property="name" column="tname"></result>
                    <result property="id" column="tid"></result>
        
                    <!--一对多需要遍历数据，所以使用的是collection标签 ofType标签替代多对一中的javaType标签，可以循环数据-->
                    <!--注意点！！！！！！！：一对多查询出来的数据是多条，所以property属性值后边需要加S，实体类需要是复数-->
                    <collection property="students" ofType="Student" >
                        <result property="id" column="sid"></result>
                        <result property="name" column="sname"></result>
                        <result property="tid" column="tid"></result>
                    </collection>
                </resultMap>
        
    
           <!--   方式二   -->
               <!--一:先查询出所有的老师数据-->
               <select id="getTeacher2" resultMap="teacherMap2">
                   select * from teacher where id = #{tid}
               </select>
               <!--二:使用resultMap标签关联另一个SQL进行子查询-->
               <resultMap id="teacherMap2" type="Teacher">
                   <!--property指的是子查询的结果集,column指的是子查询需要的查询条件，ofType指的是查询数据的实体类-->
                   <collection property="students" column="id" ofType="Student" select="getStudent">
                   </collection>
               </resultMap>
               <select id="getStudent" resultType="Student">
                   select * from student where tid = #{id}
               </select>
MyBatis-09:动态SQL标签

        1.插入数据时字段不统一可以在配置文件中的<settings>添加标签：        
            <!--是否开启驼峰命名自动映射，即从经典数据库列名 A_COLUMN 映射到经典 Java 属性名 aColumn。-->
            <setting name="mapUnderscoreToCamelCase" value="true"></setting> 
        
        2.插入语句注意字段表后边的字段是需要跟表字段一致的，values里边的字段是实体类设置好的驼峰标识字段        
             <insert id="addBlog" parameterType="Blog">
                    insert into blog(id, title, author,create_time, views) 
                    values (#{id}, #{title}, #{author}, #{createTime},#{views} )
             </insert>        
             
        3.<!--动态查询之if-->        
            <!--<where>标签替代SQL中的WHERE。
                where 元素只会在子元素返回任何内容的情况下才插入 “WHERE” 子句。
                而且，若子句的开头为 “AND” 或 “OR”，where 元素也会将它们去除。-->
            <select id="getBlogsIF" parameterType="map" resultType="Blog">
                select * from blog
                <where>
                    <if test="title != null">
                        and title = #{title}
                    </if>
                    <if test="author != null">
                        and author = #{author}
                    </if>
                </where>
            </select>
            
        4.<!--动态查询之Choose-->
            <!--<Choose>标签类似于java中的switch，
            只能有一个符合的条件进行查询，如果没有那就执行<otherwise>标签里的SQL,注意该标签没有其他属性-->
            <select id="getBlogsChoose" parameterType="map" resultType="Blog">
                select * from blog
                <where>
                    <choose>
                        <when test="title != null">
                            and title = #{title}
                        </when>
                        <when test="author != null">
                            and author = #{author}
                        </when>
                        <otherwise>
                            and views  =#{views}
                        </otherwise>
                    </choose>
                </where>
            </select>
        
        5.<!--动态查询之Set-->
            <!--set 元素会动态地在行首插入 SET 关键字，
                并会删掉额外的逗号（这些逗号是在使用条件语句给列赋值时引入的）。这些逗号是在使用条件语句给列赋值时引入的-->
            <update id="updateBlog" parameterType="map" >
                update blog
                <set>
                    <if test="title != null">
                         title = #{title},
                    </if>
                    <if test="author != null">
                         author = #{author},
                    </if>
                </set>
                where id = #{id}
            </update>     
        6.<sql>标签
                <!--SQL标签将公共的条件字段提取出来，定义唯一ID供其他SQL使用
                    在要使用SQL中加入<include>标签使用 refid对应的是<SQL>的id-->
                <sql id="sql-title">
                    <if test="title != null">
                        and title = #{title}
                    </if>
                    <if test="author != null">
                        and author = #{author}
                    </if>
                </sql>
                
                <select id="getBlogsIF" parameterType="map" resultType="Blog">
                        select * from blog
                        <where>
                            <include refid="sql-title"></include>
                        </where>
                    </select>
        7.ForEach标签
                <!--动态SQL之forEach-->
                <!--collection属性表示要循环的数据，open表示SQL前拼接的东西，close表示SQL拼接的东西，separator表示循环属性之间插入的东西-->
                <select id="getBlogForEach" parameterType="map" resultType="Blog">
                    select * from blog
                    <where>
                        <foreach collection="ids" open="and (" close=")" item="id" separator="or">
                            id = #{id}
                        </foreach>
                    </where>
                </select>      

MyBatis-09:动态SQL标签
                
                //    一级缓存
                        @Test
                        public void getUserById(){
                            SqlSession sqlSession = MyBatisUtils.getSqlSession();
                            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                        查询相同的SQL时,启动缓存.（参数SQL相同）
                            User userById = mapper.getUserById(91);
                            System.out.println(userById);
                        //手动关闭缓存,关闭后执行两次SQL，或者执行其他的增删改造作也会清除缓存
                            sqlSession.clearCache();
                            System.out.println("--------------------------------------------");
                            User userById1 = mapper.getUserById(91);
                            System.out.println(userById1);
                            sqlSession.close();
                        }      
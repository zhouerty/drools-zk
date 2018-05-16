package com.oigbuy.droolzk.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author qiang.zhou
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer {

    @Value("${spring.datasource.driverclassname}")
    private String driverclassname;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.maxWait}")
    private long maxWait;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private long timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private long minEvictableIdleTimeMillis;
    @Value("${spring.datasource.maxOpenPreparedStatements}")
    private int maxOpenPreparedStatements;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    /**
     * 配置dataSource1，使用druid连接池
     */
    @Bean(destroyMethod = "close")
    @Primary
    public DataSource dataSource1() {
        return createDataSource(driverclassname,url,username,password,maxActive,
                initialSize,maxWait,minIdle,timeBetweenEvictionRunsMillis,
                minEvictableIdleTimeMillis,maxOpenPreparedStatements,validationQuery,
                testWhileIdle,testOnBorrow,testOnReturn,poolPreparedStatements);
    }

    /**
     * 配置SqlSessionFactory：
     * - 创建SqlSessionFactoryBean，并指定一个dataSource；
     * - 设置这个分页插件：https://github.com/pagehelper/Mybatis-PageHelper；
     * - 指定mapper文件的路径；
     */
    @Bean(name="sqlSessionFactory1")
    @Primary
    public SqlSessionFactory sqlSessionFactory1() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource1());
        //分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("dialect", "mysql");
        properties.setProperty("reasonable", "false");
        properties.setProperty("pageSizeZero", "true");
        pageHelper.setProperties(properties);
        Interceptor[] plugins =  new Interceptor[]{pageHelper};
        bean.setPlugins(plugins);
        return specifiedDirectory(bean);
    }

    /**
     * Transaction 相关配置
     * 因为有两个数据源，所有使用ChainedTransactionManager把两个DataSourceTransactionManager包括在一起。
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager dtm1 = new DataSourceTransactionManager(dataSource1());
        ChainedTransactionManager ctm = new ChainedTransactionManager(dtm1);
        return ctm;
    }

    /**
     * 创建数据库连接
     * @return -
     */
    private synchronized DataSource createDataSource(String driverClassName, String url, String username, String password, int maxActive,
                                                     int initialSize, long maxWait, int minIdle, long timeBetweenEvictionRunsMillis,
                                                     long minEvictableIdleTimeMillis, int maxOpenPreparedStatements, String validationQuery,
                                                     boolean testWhileIdle, boolean testOnBorrow, boolean testOnReturn, boolean poolPreparedStatements) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driverClassName);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setMaxActive(maxActive);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMaxWait(maxWait);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        druidDataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        druidDataSource.setMaxOpenPreparedStatements(maxOpenPreparedStatements);
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(testWhileIdle);
        druidDataSource.setTestOnBorrow(testOnBorrow);
        druidDataSource.setTestOnReturn(testOnReturn);
        druidDataSource.setPoolPreparedStatements(poolPreparedStatements);
        try {
            druidDataSource.setFilters("stat, wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }

    /**
     * 指定mapper xml目录
     */
    private synchronized SqlSessionFactory specifiedDirectory(SqlSessionFactoryBean bean){
        try {
            //指定mapper xml目录
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            bean.setMapperLocations(resolver.getResources("classpath:mapper/**/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
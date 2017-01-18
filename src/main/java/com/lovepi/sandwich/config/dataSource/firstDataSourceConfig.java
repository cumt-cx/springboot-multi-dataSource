package com.lovepi.sandwich.config.dataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;


/**
 * Created by cumt_cx on 2017/1/18.
 */
@Configuration
@MapperScan(basePackages = "com.lovepi.sandwich.mapper.test1", sqlSessionTemplateRef = "firstSqlSessionTemplate")
public class firstDataSourceConfig {
    @Value("${spring.datasource.name}")
    private String dbName;
    @Value("${spring.datasource.url}") //第一步中配置文件中的数据库配置信息
    private String dbUrl;
    @Value("${spring.datasource.username}")//同理为配置文件中信息
    private String dbUser;
    @Value("${spring.datasource.password}")//同理为配置文件信息
    private String dbPassword;
    @Value("${spring.datasource.driver-class-name}")
    private String dbDriverClassName;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("${spring.datasource.maxActive}")
    private String maxActive;
    @Value("${spring.datasource.initialSize}")
    private String initialSize;
    @Value("${spring.datasource.maxWait}")
    private String maxWait;
    @Value("${spring.datasource.minIdle}")
    private String minIdle;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private String timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private String testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private String testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private String testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private String poolPreparedStatements;
    @Value("${spring.datasource.maxOpenPreparedStatements}")
    private String maxOpenPreparedStatements;

    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;

    @Value("${mybatis.mapper-locations}")
    private String mapperLocation;

    @Value("${mybatis.configuration.mapUnderscoreToCamelCase}")
    private  String mapUnderscoreToCamelCase;

    @Value("${mybatis.configuration.useColumnLabel}")
    private String useColumnLabel;

    @Value("${mybatis.configuration.useGeneratedKeys}")
    private String useGeneratedKeys;

    /**
     * druid数据源状态监控.
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        //白名单：
        servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin2");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * druid过滤器
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean(name = "firstDatasource")
    public DataSource firstDatasource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setName(dbName);
        druidDataSource.setUsername(dbUser);
        druidDataSource.setUrl(dbUrl);
        druidDataSource.setPassword(dbPassword);
        druidDataSource.setDriverClassName(dbDriverClassName);
        druidDataSource.setFilters(filters);
        druidDataSource.setMaxActive(Integer.parseInt(maxActive));
        druidDataSource.setInitialSize(Integer.parseInt(initialSize));
        druidDataSource.setMaxWait(Long.parseLong(maxWait));
        druidDataSource.setMinIdle(Integer.parseInt(minIdle));
        druidDataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
        druidDataSource.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
        druidDataSource.setValidationQuery(validationQuery);
        druidDataSource.setTestWhileIdle(Boolean.parseBoolean(testWhileIdle));
        druidDataSource.setTestOnBorrow(Boolean.parseBoolean(testOnBorrow));
        druidDataSource.setTestOnReturn(Boolean.parseBoolean(testOnReturn));
        druidDataSource.setPoolPreparedStatements(Boolean.parseBoolean(poolPreparedStatements));
        druidDataSource.setMaxOpenPreparedStatements(Integer.parseInt(maxOpenPreparedStatements));
        return druidDataSource;
    }

    @Bean(name = "firstTransactionManager")
    public DataSourceTransactionManager rdsTransactionManager(@Qualifier("firstDatasource") DataSource firstDatasource) throws SQLException {
        return new DataSourceTransactionManager(firstDatasource());
    }

    @Bean(name = "firstSessionFactory")
    public SqlSessionFactory firstSqlSessionFactory(@Qualifier("firstDatasource") DataSource firstDatasource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(firstDatasource);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(mapperLocation));
        sessionFactory.setConfiguration(configuration());
        return sessionFactory.getObject();
    }

    @Bean(name = "firstSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate testSqlSessionTemplate(@Qualifier("firstSessionFactory") SqlSessionFactory firstSqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(firstSqlSessionFactory);
    }

    private org.apache.ibatis.session.Configuration configuration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);
        configuration.setJdbcTypeForNull(JdbcType.NULL);
        configuration.setMapUnderscoreToCamelCase(Boolean.parseBoolean(mapUnderscoreToCamelCase));
        configuration.setUseColumnLabel(Boolean.parseBoolean(useColumnLabel));
        configuration.setUseGeneratedKeys(Boolean.parseBoolean(useGeneratedKeys));
        return configuration;
    }

}

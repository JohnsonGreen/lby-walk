package com.lby.walk.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@MapperScan(
        basePackages = FirstDataSourceConfig.PACKAGE,
        sqlSessionTemplateRef = "sqlSessionTemplate")
public class FirstDataSourceConfig {

    static final String PACKAGE = "com.lby.walk.dao.mapper";

    /**
     * 如果需要mapper.xml 记得指定
     */
    static final String MAPPER_LOCATION = "classpath:mapper/**/*.xml";


    /**
     * 数据源 - 主库
     */
    @Bean
    @Primary
    @Qualifier("dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 配置SqlSessionFactory
     * 1.创建SqlSessionFactoryBean
     * 2.配置数据源
     * 3.指定mapper文件的路径
     */
    @Bean(name = "sqlSessionFactory")
    @Primary
    public SqlSessionFactory firstSqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        // 设置数据源
        bean.setDataSource(dataSource);
        return bean.getObject();
    }

    /**
     * 配置DataSourceTransactionManager
     * MyBatis自动参与到spring事务管理中，无需额外配置
     */
    @Bean(name = "transactionManager")
    @Primary
    public DataSourceTransactionManager firstTransactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    /**
     * 配置SqlSessionTemplate
     */
    @Bean(name = "sqlSessionTemplate")
    @Primary
    public SqlSessionTemplate firstSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}

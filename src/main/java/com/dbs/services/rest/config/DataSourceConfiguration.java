package com.dbs.services.rest.config;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;


/**
 * 
 * Data source configuration using tomcat JDBC Connection pool
 *
 */
@Configuration
public class DataSourceConfiguration {

	@Value("${datasource.url}")
	private String datasourceUrl;
	
	@Value("${datasource.driverClassName}")
	private String datasourceDriverClassName;
	
	@Value("${datasource.username}")
	private String datasourceUserName;
	
	@Value("${datasource.password}")
	private String datasourceCredential;

	@Bean
	public DataSource dataSource() {
		
		PoolProperties poolProperties = new PoolProperties();
		
		poolProperties.setDriverClassName(datasourceDriverClassName);
		poolProperties.setMaxIdle(20);
		poolProperties.setMinIdle(10);
		poolProperties.setPassword(datasourceCredential);
		poolProperties.setUsername(datasourceUserName); 
		poolProperties.setUrl(datasourceUrl);
		poolProperties.setValidationQuery("SELECT 1");
		poolProperties.setValidationQueryTimeout(60);
		poolProperties.setTestOnBorrow(true);
		poolProperties.setRemoveAbandoned(true);
		poolProperties.setLogAbandoned(true);
		poolProperties.setInitSQL("SELECT 1");
		
	    return new org.apache.tomcat.jdbc.pool.DataSource(poolProperties);
	}
}

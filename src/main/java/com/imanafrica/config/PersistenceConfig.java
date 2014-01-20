package com.imanafrica.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaDialect;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jolbox.bonecp.BoneCPDataSource;

@Configuration
@EnableJpaRepositories("com.imanafrica.dao")
@EnableTransactionManagement
public class PersistenceConfig {
	@Autowired
	Environment env;
	
	@Bean
	public BoneCPDataSource datasource() {
		BoneCPDataSource ds = new BoneCPDataSource();
		ds.setDriverClass(env.getProperty("db.driver.class", "com.mysql.jdbc.Driver"));
		ds.setJdbcUrl(env.getProperty("db.url", "jdbc:mysql://mysql.yookosads.com:3306/smsflow?autoReconnect=true"));
		ds.setUsername(env.getProperty("db.user.name", "root"));
		ds.setPassword(env.getProperty("db.user.password", ""));
		ds.setIdleConnectionTestPeriodInMinutes(60);
		ds.setIdleMaxAgeInMinutes(420);
		ds.setMaxConnectionsPerPartition(30);
		ds.setMinConnectionsPerPartition(10);
		ds.setPartitionCount(3);
		ds.setAcquireIncrement(5);
		ds.setStatementsCacheSize(100);
		ds.setReleaseHelperThreads(3);

		return ds;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	@Autowired
	public EntityManagerFactory entityManagerFactory(BoneCPDataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);
		vendorAdapter.setShowSql(false);
		vendorAdapter
				.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
		vendorAdapter.setDatabase(Database.MYSQL);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.imanafrica.core.domain",
				"com.imanafrica.core");
		factory.setDataSource(dataSource);

		Properties properties = new Properties();
		properties
				.setProperty("hibernate.cache.use_second_level_cache", "true");
		properties.setProperty("hibernate.cache.region.factory_class",
				"org.hibernate.cache.ehcache.EhCacheRegionFactory");
		properties.setProperty("hibernate.cache.use_query_cache", "true");
		properties.setProperty("hibernate.generate_statistics", "true");

		factory.setJpaProperties(properties);

		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	@Autowired
	public JpaTransactionManager transactionManager(
			EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		JpaDialect jpaDialect = new HibernateJpaDialect();
		txManager.setEntityManagerFactory(entityManagerFactory);
		txManager.setJpaDialect(jpaDialect);
		return txManager;
	}
}

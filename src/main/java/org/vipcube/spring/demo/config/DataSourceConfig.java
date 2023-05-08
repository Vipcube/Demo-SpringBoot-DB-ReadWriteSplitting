package org.vipcube.spring.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Profile("direct")
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"org.vipcube.spring.demo.repository"})
@RequiredArgsConstructor
public class DataSourceConfig {
    private final Environment props;

    private HikariDataSource abstractDataSource() {
        var abstractDataSource = new HikariDataSource();
        abstractDataSource.setDriverClassName(props.getProperty("datasource.driver-class-name"));
        return abstractDataSource;
    }

    /**
     * master setting
     */
    @Bean(destroyMethod = "close", name="master")
    @Primary
    public HikariDataSource masterDataSource() {
        var masterDataSource = abstractDataSource();
        masterDataSource.setJdbcUrl(props.getProperty("datasource.master.url"));
        masterDataSource.setUsername(props.getProperty("datasource.master.username"));
        masterDataSource.setPassword(props.getProperty("datasource.master.password"));
        return masterDataSource;
    }

    /**
     * slave setting
     */
    @Bean(destroyMethod = "close", name="slave")
    public HikariDataSource slaveDataSource() {
        var slaveDataSource = abstractDataSource();
        slaveDataSource.setJdbcUrl(props.getProperty("datasource.slave.url"));
        slaveDataSource.setUsername(props.getProperty("datasource.slave.username"));
        slaveDataSource.setPassword(props.getProperty("datasource.slave.password"));
        return slaveDataSource;
    }

    @Bean(name="dynamicDataSource")
    public DataSource dynamicDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("master", masterDataSource());
        targetDataSources.put("slave", slaveDataSource());

        AbstractRoutingDataSource dynamicDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                String lookupKey = TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? "slave" : "master";
                log.info("connected DataSource :" + lookupKey);
                return lookupKey;
            }
        };

        dynamicDataSource.setDefaultTargetDataSource(targetDataSources.get("master"));
        dynamicDataSource.setTargetDataSources(targetDataSources);
        return dynamicDataSource;
    }

    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(dynamicDataSource());
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages("org.vipcube.spring.demo.entity")
                .build();
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(Objects.requireNonNull(entityManagerFactory(builder).getObject()));
    }
}

package com.gnaderi.interview.cro;

import com.gnaderi.interview.cro.repository.*;
import com.gnaderi.interview.cro.service.CroService;
import com.gnaderi.interview.cro.service.SimpleCroService;
import org.junit.Before;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.annotation.PostConstruct;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(basePackageClasses = {BeneficialOwnerRepository.class,
        CompanyRepository.class,
        StakeholderRepository.class,
        RoleRepository.class,
        UserRepository.class,
        UserAccessRepository.class})
public class TestConfiguration {
    @Bean(destroyMethod = "shutdown")
    public EmbeddedDatabase dataSource() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.H2).
                addScript("sql/CRO_DB_Schema.sql").
                build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException, PropertyVetoException {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setJpaProperties(new Properties());
        emf.setJpaVendorAdapter(jpaAdapter());
        emf.setPackagesToScan("com.gnaderi.interview");
        return emf;
    }

    private JpaVendorAdapter jpaAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    @Bean
    public TestRestTemplate restTemplateBuilder() {
        return new TestRestTemplate();
    }

    @Bean
    public CroService croService() {
        return new SimpleCroService();
    }

    @Bean
    public DBInitializerStartupRunner dBInitializerStartupRunner() {
        return new DBInitializerStartupRunner();
    }
}

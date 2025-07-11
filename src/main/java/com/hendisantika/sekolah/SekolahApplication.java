package com.hendisantika.sekolah;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.thymeleaf.dialect.springdata.SpringDataDialect;

import com.hendisantika.sekolah.repository.base.CustomJpaRepositoryFactoryBean;

@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class)
@EnableJpaAuditing
public class SekolahApplication {

  public static void main(String[] args) {
    SpringApplication.run(SekolahApplication.class, args);
  }

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }

  @Bean
  public SpringDataDialect springDataDialect() {
    return new SpringDataDialect();
  }

}

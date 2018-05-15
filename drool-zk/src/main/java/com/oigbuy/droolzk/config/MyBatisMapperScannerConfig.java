package com.oigbuy.droolzk.config;


import com.oigbuy.droolzk.annotation.MysqlRepository;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author qiang.zhou on 2017/09/13.
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig {

    /**
     * - 设置SqlSessionFactory；
     * - 设置dao所在的package路径；
     * - 关联注解在dao类上的Annotation名字；
     */
    @Bean(name="mapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory1");
        mapperScannerConfigurer.setBasePackage("com.oigbuy.*.dao");
        mapperScannerConfigurer.setAnnotationClass(MysqlRepository.class);
        return mapperScannerConfigurer;
    }
}

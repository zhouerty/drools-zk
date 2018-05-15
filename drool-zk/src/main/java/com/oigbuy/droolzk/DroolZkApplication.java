package com.oigbuy.droolzk;

import com.oigbuy.droolzk.config.DroolsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author qiang.zhou
 */
@SpringBootApplication
@EnableConfigurationProperties({DroolsConfig.class})
public class DroolZkApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroolZkApplication.class, args);
	}
}

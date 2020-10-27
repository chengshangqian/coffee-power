package com.fandou.coffeepower.backup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 启动器
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages={"com.fandou.coffeepower"})
public class BackupApp {

	public static void main(String[] args) {
		SpringApplication.run(BackupApp.class, args);
	}
}
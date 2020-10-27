package com.fandou.coffeepower.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

/**
 * 启动器
 * @author Louis
 * @date Jan 15, 2019
 */
@EnableAdminServer
@EnableDiscoveryClient
@SpringBootApplication
public class MonitorApp {

	public static void main(String[] args) {
		SpringApplication.run(MonitorApp.class, args);
	}
}
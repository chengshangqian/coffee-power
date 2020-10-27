package com.fandou.coffeepower.admin.service;

import java.util.List;

import com.fandou.coffeepower.admin.model.SysConfig;
import com.fandou.coffeepower.core.service.CurdService;

/**
 * 系统配置管理
 */
public interface SysConfigService extends CurdService<SysConfig> {

	/**
	 * 根据名称查询
	 * @param lable
	 * @return
	 */
	List<SysConfig> findByLable(String lable);
}

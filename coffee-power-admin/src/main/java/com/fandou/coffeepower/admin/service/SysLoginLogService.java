package com.fandou.coffeepower.admin.service;

import com.fandou.coffeepower.admin.model.SysLoginLog;
import com.fandou.coffeepower.core.service.CurdService;

/**
 * 登录日志
 */
public interface SysLoginLogService extends CurdService<SysLoginLog> {

	/**
	 * 记录登录日志
	 * @param userName
	 * @param ip
	 * @return
	 */
	int writeLoginLog(String userName, String ip);
}

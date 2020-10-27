package com.fandou.coffeepower.admin.service;

import java.util.List;

import com.fandou.coffeepower.admin.model.SysDept;
import com.fandou.coffeepower.core.service.CurdService;

/**
 * 机构管理
 * @author Louis
 * @date Jan 13, 2019
 */
public interface SysDeptService extends CurdService<SysDept> {

	/**
	 * 查询机构树
	 * @param userId 
	 * @return
	 */
	List<SysDept> findTree();
}

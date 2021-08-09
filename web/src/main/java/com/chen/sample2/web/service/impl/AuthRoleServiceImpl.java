package com.chen.sample2.web.service.impl;

import com.chen.sample2.web.base.BaseServiceImpl;
import com.chen.sample2.tool.message.ResponseMsg;
import com.chen.sample2.tool.message.RequestMsg;
import com.chen.sample2.tool.message.PageResult;
import com.chen.sample2.tool.persistence.SimpleCriteria;
import com.chen.sample2.tool.persistence.SimpleRestrictions;
import com.chen.sample2.web.entity.AuthRole;
import com.chen.sample2.web.service.IAuthRoleService;
import com.chen.sample2.web.dao.AuthRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

/**
 *  角色表 接口实现
 *
 * @author Chentian
 */ 
@Service
public class AuthRoleServiceImpl extends BaseServiceImpl<AuthRole> implements IAuthRoleService {

	@Autowired
	private AuthRoleDao authRoleDao;

	@Override
	public ResponseMsg queryPage(RequestMsg requestMsg){
		AuthRole authRole = requestMsg.getParams().toBean(AuthRole.class);
		SimpleCriteria<AuthRole> simpleCriteria = new SimpleCriteria.Builder<AuthRole>()
			.add(SimpleRestrictions.eq("name",authRole.getName()))
			.add(SimpleRestrictions.eq("memo",authRole.getMemo()))
			.builder();

		Pageable pageable = PageRequest.of(requestMsg.getPageNum(), requestMsg.getPageSize(),RequestMsg.DEFAULT_SORT);
		Page<AuthRole> page = authRoleDao.findAll(simpleCriteria,pageable);

		PageResult<AuthRole> pageResult = new PageResult<>(page);
		logger.info("pageResult:{}", pageResult.toString());
		return ResponseMsg.createSuccessResponse(pageResult);
	}
}


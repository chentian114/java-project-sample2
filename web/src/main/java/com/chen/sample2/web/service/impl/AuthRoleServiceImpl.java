package com.chen.sample2.web.service.impl;

import com.chen.sample2.web.base.BaseServiceImpl;
import com.chen.sample2.tool.message.ResponseMsg;
import com.chen.sample2.tool.message.RequestMsg;
import com.chen.sample2.tool.message.PageResult;
import com.chen.sample2.web.entity.AuthRole;
import com.chen.sample2.web.service.IAuthRoleService;
import com.chen.sample2.web.dao.AuthRoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
		Sort sort = new Sort(Sort.Direction.DESC, "createTime");
		Pageable pageable = PageRequest.of(requestMsg.getPageNumber(), requestMsg.getPageSize(), sort);
		Page<AuthRole> result = authRoleDao.findAll(pageable);
		PageResult<AuthRole> pageResult = new PageResult<>(result.getNumber(),result.getSize(),result.getTotalElements(),result.getTotalPages(),result.getContent());
		return ResponseMsg.createSuccessResponse(pageResult);
	}
}


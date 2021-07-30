package com.chen.sample2.web.service;

import com.chen.sample2.web.base.BaseService;
import com.chen.sample2.tool.message.ResponseMsg;
import com.chen.sample2.tool.message.RequestMsg;
import com.chen.sample2.web.entity.AuthRole;

/**
 *  角色表 接口
 *
 * @author Chentian
 */ 
public interface IAuthRoleService extends BaseService<AuthRole> {

	ResponseMsg queryPage(RequestMsg requestMsg);
}


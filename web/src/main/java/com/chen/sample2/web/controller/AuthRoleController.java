package com.chen.sample2.web.controller;

import com.chen.sample2.web.base.BaseController;
import com.chen.sample2.web.entity.AuthRole;
import com.chen.sample2.tool.message.RequestMsg;
import com.chen.sample2.tool.message.ResponseMsg;
import com.chen.sample2.web.service.IAuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import cn.hutool.json.JSONUtil;
import org.springframework.web.bind.annotation.*;

/**
 *  角色表 Controller
 *
 * @author Chentian
 */ 
@RestController
@RequestMapping("/authRole")
public class AuthRoleController extends BaseController {

	@Autowired
	private IAuthRoleService authRoleService;

	@GetMapping("/getById/{id}")
	public ResponseMsg getById(@PathVariable Integer id) {
		logger.info("params:{}", id);
		AuthRole result = authRoleService.findById(id);
		ResponseMsg responseMsg = new ResponseMsg();
		responseMsg.setData(result);
		return responseMsg;
	}

	@GetMapping("/queryPage")
	public ResponseMsg queryPage(AuthRole model,Integer pageNum,Integer pageSize) {
		RequestMsg requestMsg = new RequestMsg(model,pageNum,pageSize);
		logger.info("params:{}", JSONUtil.toJsonStr(requestMsg));
		return authRoleService.queryPage(requestMsg);
	}

	@PostMapping("/save")
	public ResponseMsg save(@RequestBody AuthRole model) {
		logger.info("params:{}", JSONUtil.toJsonStr(model));
		AuthRole result = authRoleService.save(model);
		ResponseMsg responseMsg = new ResponseMsg();
		responseMsg.setData(result);
		return responseMsg;
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseMsg deleteById(@PathVariable Integer id) {
		logger.info("params:{}", id);
		authRoleService.deleteById(id);
		ResponseMsg responseMsg = new ResponseMsg();
		return responseMsg;
	}

}

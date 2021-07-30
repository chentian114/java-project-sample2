package com.chen.sample2.web.controller;

import com.chen.sample2.web.base.BaseController;
import com.chen.sample2.web.entity.AuthRole;
import com.chen.sample2.tool.message.RequestMsg;
import com.chen.sample2.tool.message.ResponseMsg;
import com.chen.sample2.web.service.IAuthRoleService;
import org.springframework.beans.factory.annotation.Autowired;
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

	@PostMapping("/save")
	public ResponseMsg save(@RequestBody AuthRole model) {
		ResponseMsg responseMsg = new ResponseMsg();
		AuthRole result = authRoleService.save(model);
		responseMsg.setData(result);
		return responseMsg;
	}

	@DeleteMapping("/deleteById/{id}")
	public ResponseMsg deleteById(@PathVariable Integer id) {
		ResponseMsg responseMsg = new ResponseMsg();
		authRoleService.deleteById(id);
		return responseMsg;
	}

	@GetMapping("/selectById/{id}")
	public ResponseMsg selectById(@PathVariable Integer id) {
		ResponseMsg responseMsg = new ResponseMsg();
		AuthRole result = authRoleService.findById(id);
		responseMsg.setData(result);
		return responseMsg;
	}

	@GetMapping("/queryPage")
	public ResponseMsg queryPage(Integer pageNumber,Integer pageSize) {
		RequestMsg requestMsg = new RequestMsg(pageNumber,pageSize);
		return authRoleService.queryPage(requestMsg);
	}

}

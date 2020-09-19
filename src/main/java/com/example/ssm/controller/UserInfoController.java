package com.example.ssm.controller;

import javax.annotation.Resource;

import org.activiti.engine.repository.Deployment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ssm.impl.WorkFlowService;
import com.example.ssm.model.UserInfo;
import com.example.ssm.service.IUserInfoService;

@RestController

@RequestMapping("/userinfo")
public class UserInfoController {

	@Resource
	IUserInfoService userinfoService;
	@Resource
	WorkFlowService workFlowService;
	
	@RequestMapping("/getUserInfo")
    @ResponseBody
    public UserInfo getUserInfo(){
 
       return userinfoService.getUserInfoById(1);
 
    }
	
	@RequestMapping("/testWorkflow")
    public void testWorkflow(){
 
		workFlowService.deploymentProcessDefinition();
		workFlowService.startProcessInstance();
		workFlowService.findMyTask();
		workFlowService.findMyTask2();
 
    }

}

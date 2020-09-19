package com.example.ssm.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkFlowService {
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	private TaskService taskService;

	public void deploymentProcessDefinition() {
		//
		Deployment deployment = repositoryService.createDeployment()// 创建一个部署对象
				.name("hello审批")// 添加部署名称
				.addClasspathResource("diagrams/HelloProcess.bpmn") // 从classpath资源中加载，一次只加载一个文件
				.addClasspathResource("diagrams/HelloProcess.png").deploy();// 完成部署
		System.out.println("deploymentId：" + deployment.getId());
		System.out.println("deployment名称：" + deployment.getName());

	}

	public void startProcessInstance() {
		// 流程定义key
		String processDefinitionKey = "HelloProcess";
		Map<String,Object> map=new HashMap<String,Object>();
        map.put("inputUser","小王");
		// 与正在执行的流程实例和执行对象相关的Service
		ProcessInstance processInstance = runtimeService
				// 使用流程定义的key，key对应hello.bpmn文件中id值,默认按照最新版本的流程定义启动
				.startProcessInstanceByKey(processDefinitionKey,map);
		System.out.println("启动流程实例id:" + processInstance.getId());// 401
		System.out.println("流程定义id:" + processInstance.getProcessDefinitionId());// 304
		

	}

	public void testQueryProinsatanceState(String processInstanceId){
//		String processInstanceId = "40001";
		ProcessInstance singleResult = runtimeService
											.createProcessInstanceQuery()
											.processInstanceId(processInstanceId)
											.singleResult();
		if (singleResult != null) {
			System.out.println("流程执行到："+singleResult.getActivityId());
		}else{
			System.out.println("流程执行完毕");
		}
	}
	public void findMyTask() {
		String assignee = "小王";
		List<Task> list = taskService// 与个人任务相关
				.createTaskQuery() // 创建任务查询对象
				.taskAssignee(assignee) // 指定办理人
				.list(); // 列表集合

		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任务id" + task.getId());
				System.out.println("任务名称" + task.getName());
				System.out.println("任务办理人" + task.getAssignee());
				System.out.println("任务实例id" + task.getProcessInstanceId());
				//完成任务
				Map<String,Object> map=new HashMap<String,Object>();
		        map.put("user","小张");
		        taskService.complete(task.getId(),map);
			}
		}
	}
	public void findMyTask2() {
		String assignee = "小张";
		List<Task> list = taskService// 与个人任务相关
				.createTaskQuery() // 创建任务查询对象
				.taskAssignee(assignee) // 指定办理人
				.list(); // 列表集合

		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任务id" + task.getId());
				System.out.println("任务名称" + task.getName());
				System.out.println("任务办理人" + task.getAssignee());
				System.out.println("任务实例id" + task.getProcessInstanceId());
			}
		}
	}

	public void completeMyTask(String taskId) {
		
		taskService.complete(taskId);
		System.out.println("完成任务，id" + taskId);
	}

	/*认领任务*/
	public void claim() {

		// 任务ID

		String taskId = "122502";

		// 个人任务的办理人

		String userId = "a";
		taskService.claim(taskId, userId);

	}
}

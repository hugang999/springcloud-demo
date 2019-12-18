package com.hugang.test;

import org.activiti.engine.*;
import org.activiti.engine.identity.Group;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;

/**
 * @author: hg
 * @date: 2019/11/28 16:13
 */
public class FirstAct {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //存储服务
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //运行时服务
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //任务服务
        TaskService taskService = processEngine.getTaskService();
        //身份服务
        IdentityService identityService = processEngine.getIdentityService();

        //1、完整示例
        //firstAct(repositoryService, runtimeService, taskService);

        //2、获取用户组
        getGroups(identityService);

        processEngine.close();
        System.exit(0);
    }

    /**
     * 第一个完整实例
     * @param repositoryService
     * @param runtimeService
     * @param taskService
     */
    public static void firstAct(RepositoryService repositoryService, RuntimeService runtimeService, TaskService taskService){
        //部署流程定义
        repositoryService.createDeployment().addClasspathResource("first.bpmn").deploy();

        //启动流程
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("myProcess");

        //查看任务
        Task task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        System.out.println("当前流程节点：" + task.getName());

        //普通员工完成请假流程
        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        System.out.println("完成任务后，当前流程节点：" + task.getName());
        //领导完成审批请假流程
        taskService.complete(task.getId());

        task = taskService.createTaskQuery().processInstanceId(pi.getId()).singleResult();

        System.out.println("流程结束：" + task);

    }

    /**
     * 获取用户组
     * @param identityService
     */
    public static void getGroups(IdentityService identityService){
        //分页查询，第一个参数为id的值，默认以id升序排序，第二个参数为个数
        //List<Group> groups = identityService.createGroupQuery().listPage(2, 5);

        //排序
//        List<Group> groups = identityService.createGroupQuery().orderByGroupName().desc().orderByGroupId().asc().list();
//        for (Group group : groups){
//            System.out.println(group.getId() + "---" + group.getName() + "---" + group.getType());
//        }

        //条件查询
//        Group group = identityService.createGroupQuery().groupName("group_1").singleResult();
//        System.out.println(group.getId() + "---" + group.getName() + "---" + group.getType());

        //写sql查询
        List<Group> groups = identityService.createNativeGroupQuery().sql("select * from ACT_ID_GROUP WHERE NAME_ = #{name}").parameter("name", "group_1").list();
        for (Group group : groups){
            System.out.println(group.getId() + "---" + group.getName() + "---" + group.getType());
        }
    }
}

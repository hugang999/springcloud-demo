package com.hugang.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * activiti使用实例
 * 步骤：
 * 1、初始化数据库表
 * 2、画流程图
 * 3、配置处理人
 * 4、修改流程图id和name
 * 5、部署流程定义
 * <p>
 * 注：所有的模糊查询都要手动加%
 * <p>
 * 【20191219】：使用流程变量指定任务办理人，#{username}
 * <p>
 * 【20191220】：组任务
 *
 * @author: hg
 * @date: 2019/12/10 15:17
 */
@Slf4j
public class HelloActiviti {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义
     */
    @Test
    public void deployProcessDefine() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().name("请假流程001")
                .addClasspathResource("helloworld.bpmn")
                .addClasspathResource("helloworld.png")
                .deploy();
        System.out.println("部署成功：流程id为  " + deployment.getId());
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcess() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processDefineKey = "HelloWorld";

        Map<String, Object> variables = new HashMap<>();
//        variables.put("username", "张三");

        //流程变量设置任务候选人
//        variables.put("usernames", "小A,小B,小C,小D");
        runtimeService.startProcessInstanceByKey(processDefineKey, variables);
        System.out.println("流程启动成功");
    }

    /**
     * 查询组任务
     */
    @Test
    public void queryGroupTask() {
        TaskService taskService = processEngine.getTaskService();
//        String candidate = "小A";
        String candidate = "小B";
//        String candidate = "小C";
//        String candidate = "小D";
        List<Task> list = taskService.createTaskQuery().taskCandidateOrAssigned(candidate).list();
        if (null != list && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("流程实例ID:" + task.getProcessInstanceId());
                System.out.println("执行实例ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务办理人:" + task.getAssignee());
                System.out.println("################################");
            }
        }
    }

    /**
     * 拾取任务
     */
    @Test
    public void claimTask() {
        TaskService service = processEngine.getTaskService();
        String taskId = "120004";
        String userId = "小A";
        service.claim(taskId, userId);
        System.out.println("任务拾取完成");
    }

    /**
     * 回退任务（只有组任务才可以）
     */
    @Test
    public void pickupTask() {
        TaskService service = processEngine.getTaskService();
        String taskId = "95004";
        service.setAssignee(taskId, null);
        System.out.println("任务回退完成");
    }

    /**
     * 查询组任务成员列表
     */
    @Test
    public void queryGroupUser() {
        TaskService service = processEngine.getTaskService();
        String taskId = "95004";
        List<IdentityLink> identityLinks = service.getIdentityLinksForTask(taskId);
        for (IdentityLink identityLink : identityLinks) {
            System.out.println("userId=" + identityLink.getUserId());
            System.out.println("taskId=" + identityLink.getTaskId());
            System.out.println("piId=" + identityLink.getProcessInstanceId());
            System.out.println("TYPE=" + identityLink.getType());
            System.out.println("######################");
        }

    }

    /**
     * 查询任务
     */
    @Test
    public void taskQuery() {
        TaskService taskService = processEngine.getTaskService();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee("小A").list();

        if (!tasks.isEmpty()) {
            for (Task task : tasks) {
                log.info("任务ID:" + task.getId());
                log.info("任务名称:" + task.getName());
                log.info("任务的创建时间:" + task.getCreateTime());
                log.info("任务的办理人:" + task.getAssignee());
                log.info("流程实例ID：" + task.getProcessInstanceId());
                log.info("执行对象ID:" + task.getExecutionId());
                log.info("流程定义ID:" + task.getProcessDefinitionId());
                log.info("********************************************");
            }
        }
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        TaskService taskService = processEngine.getTaskService();

        String taskId = "120004";
        Map<String, Object> variables = new HashMap<>();
//        variables.put("username", "王五");
//        taskService.complete(taskId, variables);
        taskService.complete(taskId);
        System.out.println("任务完成");

        //可用此方法指定办理人
//        taskService.setAssignee(taskId, "王五");

    }


}

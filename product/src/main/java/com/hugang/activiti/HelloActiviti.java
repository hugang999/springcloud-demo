package com.hugang.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * activiti使用实例
 * 步骤：
 * 1、初始化数据库表
 * 2、画流程图
 * 3、配置处理人
 * 4、修改流程图id和name
 * 5、部署流程定义
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
        Deployment deployment = repositoryService.createDeployment().name("请假流程001").addClasspathResource("helloworld.bpmn")
                .addClasspathResource("helloworld.png").deploy();
        System.out.println("部署成功：流程id为  " + deployment.getId());
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcess() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processDefineKey = "HelloWorld";
        runtimeService.startProcessInstanceByKey(processDefineKey);
        System.out.println("流程启动成功");
    }

    /**
     * 查询任务
     */
    @Test
    public void taskQuery() {
        TaskService taskService = processEngine.getTaskService();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee("王五").list();

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
    public void completeTask(){
        TaskService taskService = processEngine.getTaskService();

        String taskId = "7502";
        taskService.complete(taskId);
        System.out.println("任务完成");
    }
}

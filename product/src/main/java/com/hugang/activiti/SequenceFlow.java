package com.hugang.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 连线
 *
 * @author: hg
 * @date: 2019/12/18 14:38
 */
@Slf4j
public class SequenceFlow {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义
     */
    @Test
    public void deployProcessDefine() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().name("请假流程002")
                .addClasspathResource("SequenceFlow/SequenceFlowBPMN.bpmn")
                .addClasspathResource("SequenceFlow/SequenceFlowBPMN.png")
                .deploy();
        System.out.println("部署成功：流程id为  " + deployment.getId());
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcess() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processDefineKey = "SequenceFlow";
        runtimeService.startProcessInstanceByKey(processDefineKey);
        System.out.println("流程启动成功");
    }

    /**
     * 查询任务
     */
    @Test
    public void taskQuery() {
        TaskService taskService = processEngine.getTaskService();

        List<Task> tasks = taskService.createTaskQuery().taskAssignee("张三").list();

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
     * 完成任务并使用流程变量指定流程走向
     */
    @Test
    public void completeTask() {
        TaskService taskService = processEngine.getTaskService();

        String taskId = "25002";
        Map<String, Object> variables = new HashMap<>();
        variables.put("outcome", "重要");
        taskService.complete(taskId, variables);
        System.out.println("任务完成");
    }
}

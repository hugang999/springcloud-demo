package com.hugang.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

/**
 * 管理流程实例
 *
 * @author: hg
 * @date: 2019/12/11 09:38
 */
@Slf4j
public class ProcessInstanceManage {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();


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
        //根据流程定义key启动流程，默认启动最新版本的流程定义
        String processDefineKey = "HelloWorld";
        runtimeService.startProcessInstanceByKey(processDefineKey);

        /**
         * 根据流程定义id启动流程
         * 参数1：流程定义id
         * 参数2：流程变量 Map<String, Object> variables ,可改变流程的走向
         */
//        String processDefId = "HelloWorld:1:5004";
//        runtimeService.startProcessInstanceById(processDefId, variables);

        //根据流程定义id和业务id
//        String businessId = "";
        /**
         * 根据流程定义id启动流程
         * 参数1：流程定义id
         * 参数2：业务id
         */
//        runtimeService.startProcessInstanceById(processDefId, businessId);

        /**
         * 根据流程定义id启动流程
         * 参数1：流程定义id
         * 参数2：业务id
         * 参数3：流程变量 Map<String, Object> variables ,可改变流程的走向
         */
//        runtimeService.startProcessInstanceById(processDefId, businessId, variables);

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
    public void completeTask() {
        TaskService taskService = processEngine.getTaskService();

        String taskId = "7504";
        taskService.complete(taskId);
//        Map<String, Object> variables = new HashMap<>();
//        taskService.complete(taskId, variables);
        System.out.println("任务完成");
    }

    /**
     * 判断流程是否结束
     */
    @Test
    public void judegComplete() {
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //流程实例id
        String processInstanceId = "7501";
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        if (null == processInstance) {
            log.info("流程已结束");
        } else {
            log.info("流程未结束");
        }
    }

    /**
     * 查询历史任务
     */
    @Test
    public void queryHisTask(){
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery().list();

        if (!tasks.isEmpty()) {
            for (HistoricTaskInstance task : tasks) {
                log.info("任务ID:" + task.getId());
                log.info("任务名称:" + task.getName());
                log.info("任务的创建时间:" + task.getCreateTime());
                log.info("任务的办理人:" + task.getAssignee());
                log.info("流程实例ID：" + task.getProcessInstanceId());
                log.info("执行对象ID:" + task.getExecutionId());
                log.info("流程定义ID:" + task.getProcessDefinitionId());
                log.info("任务开始时间:" + task.getStartTime());
                log.info("任务持续时间:" + task.getDurationInMillis());
                log.info("任务结束时间:" + task.getEndTime());
                log.info("********************************************");
            }
        }

    }

    /**
     * 查询历史流程实例
     */
    @Test
    public void queryHisProcessInstance(){
        HistoryService service = processEngine.getHistoryService();
        List<HistoricProcessInstance> list = service.createHistoricProcessInstanceQuery().list();

        if (null != list && !list.isEmpty()){
            for (HistoricProcessInstance hs : list){
                log.info("执行实例id：" + hs.getId());
                log.info("流程定义id：" + hs.getProcessDefinitionId());
                log.info("流程启动时间：" + hs.getStartTime());
                log.info("************************************");
            }
        }
    }
}

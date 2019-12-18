package com.hugang.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 流程变量测试
 * @author: hg
 * @date: 2019/12/11 15:17
 */
@Slf4j
public class ProcessVariables {
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
        //添加流程变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("请假天数", 10);
        variables.put("请假原因", "生病");
        variables.put("请假时间", new Date());
        runtimeService.startProcessInstanceByKey(processDefineKey, variables);

        System.out.println("流程启动成功");
    }


    /**
     * 设置流程变量-运行时service设置
     */
    @Test
    public void setVariables(){
        RuntimeService runtimeService = processEngine.getRuntimeService();

        //执行实例id
        String executionId = "2501";
        Map<String, Object> variables = new HashMap<>();
        variables.put("请假天数", 12);
        variables.put("请假原因", "生病");
        variables.put("请假时间", new Date());
        variables.put("用户对象", new TestUser(1, "小明"));

        runtimeService.setVariables(executionId, variables);
//        runtimeService.setVariable(executionId, variableKey, value);

        log.info("流程变量设置成功");

    }
    /**
     * 设置流程变量-任务service设置
     */
    @Test
    public void setVariables2(){
        TaskService service = processEngine.getTaskService();

        //执行实例id
        String taskId = "2507";
        Map<String, Object> variables = new HashMap<>();
        variables.put("请假天数-任务service设置", 12);


        service.setVariables(taskId, variables);
//        runtimeService.setVariable(executionId, variableKey, value);

        log.info("流程变量设置成功");

    }

    /**
     * 获取流程变量
     */
    @Test
    public void getVariables(){
        RuntimeService service = processEngine.getRuntimeService();

        String executionId = "2501";
        Map<String, Object> variables = service.getVariables(executionId);
        Date date = (Date) service.getVariable(executionId, "请假时间");
        System.out.println("请假时间：" + date.toLocaleString() + date.toString());

    }

    /**
     * 获取历史流程变量
     */
    @Test
    public void getHisVariables(){
        HistoryService service = processEngine.getHistoryService();

//        HistoricVariableInstance singleResult = service.createHistoricVariableInstanceQuery().id("7501").singleResult();

//        System.out.println("id:" + singleResult.getId());
//        System.out.println("流程实例id:" + singleResult.getProcessInstanceId());
//        System.out.println("任务id:" + singleResult.getTaskId());
//        System.out.println("流程变量名称:" + singleResult.getVariableName());
//        System.out.println("流程变量类型:" + singleResult.getVariableTypeName());
        List<HistoricVariableInstance> list = service.createHistoricVariableInstanceQuery().processInstanceId("2501").list();

        if (list != null && !list.isEmpty()){
            for (HistoricVariableInstance singleResult : list){
                System.out.println("id:" + singleResult.getId());
                System.out.println("流程实例id:" + singleResult.getProcessInstanceId());
                System.out.println("任务id:" + singleResult.getTaskId());
                System.out.println("流程变量名称:" + singleResult.getVariableName());
                System.out.println("流程变量类型:" + singleResult.getVariableTypeName());
                System.out.println("******************************");
            }
        }
    }
}

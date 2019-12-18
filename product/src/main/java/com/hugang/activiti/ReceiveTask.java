package com.hugang.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 接受活动
 */
@Slf4j
public class ReceiveTask {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 部署流程定义
     */
    @Test
    public void deployProcessDefine() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().name("接受活动流程")
                .addClasspathResource("receiveTask/receiveTask.bpmn")
                .addClasspathResource("receiveTask/receiveTask.png")
                .deploy();
        System.out.println("部署成功：流程id为  " + deployment.getId());
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcess() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
//        String processDefineKey = "receiveTask";
//        ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefineKey);
//        System.out.println("流程启动成功");
        System.out.println("***********当日销售额汇总***********");

        Execution execution = runtimeService.createExecutionQuery()
                .processInstanceId("45001")
                .activityId("receivetask1")
                .singleResult();

        Integer money = 0;
        int tryNum = 0;
        while (true){
            tryNum ++;
            try {
                money = hzxse();
                break;
            } catch (Exception e){
                e.printStackTrace();
                if (tryNum == 10){
                    System.out.println("尝试10次汇总全部失败，退出汇总，流程终止");

                    return;
                }
            }
        }
        System.out.println("当日销售额汇总完成：" + 10000);
        Map<String, Object> variables = new HashMap<>();
        variables.put("当日销售额汇总", 10000);
        runtimeService.setVariables(execution.getId(), variables);

        //执行一步到下一个接受活动
        runtimeService.signal(execution.getId());
        System.out.println("***********向领导发短信***********");

        Execution execution2 = runtimeService.createExecutionQuery()
                .processInstanceId("45001")
                .activityId("receivetask2")
                .singleResult();

        Integer money2 = (Integer) runtimeService.getVariable(execution2.getId(), "当日销售额汇总");
        System.out.println("当日销售额汇总：" + money2);
        Boolean flag = false;
        int tryNum2 = 0;
        do {
            if (tryNum2 == 10){
                System.out.println("尝试10次发送短信全部失败，退出发送短信，流程结束");
                break;
            }
            tryNum2 ++;
            flag = sendMessage();

        } while (!flag);



        runtimeService.signal(execution2.getId());
        System.out.println("流程结束");


    }



    private Integer hzxse(){
        return 10000;
    }

    private Boolean sendMessage(){
        return true;
    }

}

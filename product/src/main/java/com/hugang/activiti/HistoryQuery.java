package com.hugang.activiti;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 查询历史记录
 *
 * @author: hg
 * @date: 2019/12/18 11:38
 */
public class HistoryQuery {

    private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Test
    public void historyQuery(){
        HistoryService service = processEngine.getHistoryService();

        List<HistoricProcessInstance> list = service.createHistoricProcessInstanceQuery().list();

        if (null != list && !list.isEmpty()){
            for (HistoricProcessInstance processInstance : list){
                System.out.println("流程开始时间：" + simpleDateFormat.format(processInstance.getStartTime()));
                System.out.println("流程定义id：" + processInstance.getProcessDefinitionId());
                System.out.println("业务id：" + processInstance.getBusinessKey());
                System.out.println("部署id：" + processInstance.getDeploymentId());
                System.out.println("流程定义key：" + processInstance.getProcessDefinitionKey());
                System.out.println("流程结束时间：" + processInstance.getEndTime());
            }
        }
    }
}

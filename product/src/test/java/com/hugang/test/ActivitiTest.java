package com.hugang.test;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: hg
 * @date: 2019/11/22 10:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(locations = {
        "classpath:activiti.cfg.xml"})
@Slf4j
public class ActivitiTest {

    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    /**
     * 发布流程
     *
     * @throws Exception
     */
    @Test
    public void testTask() throws Exception {

        //1、发布流程
        InputStream inputStreamBpmn = this.getClass().getResourceAsStream("/diagrams/test.bpmn");
        InputStream inputStreamPng = this.getClass().getResourceAsStream("/diagrams/test.png");
        processEngine.getRepositoryService().createDeployment()
                .addInputStream("test.bpmn", inputStreamBpmn)
                .addInputStream("test.png", inputStreamPng)
                .deploy();
        ProcessInstance processInstance = processEngine.getRuntimeService().startProcessInstanceByKey("test");
        System.out.println("#################  pid:" + processInstance.getId() + ", pname:" + processInstance.getName());
    }

    /**
     * 部署流程定义（从classpath）
     */
    @Test
    public void deploymentProcessDefinition_classpath() throws IOException {
        Deployment deployment = processEngine.getRepositoryService() //与流程定义和部署相关的service
                .createDeployment() //创建一个部署对象
                .name("工单流程") //添加部署的名称
                .addClasspathResource("diagrams/callWorkflow.bpmn") //从类路径加载资源，一次智能加载一个文件
//                .addClasspathResource("diagrams/test.png")
                .deploy(); //完成部署
        System.out.println("部署ID：" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());
        String deploymentId = deployment.getId();
        //获取该流程的所有资源名称列表
        List<String> list = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if (list != null) {
            for (String s : list) {
                if (s.contains(".png")) {
                    resourceName = s;
                    break;
                }
            }
        }

        //获取图片输入流
        InputStream inputStream = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);

        //将图片生成到电脑中
        File file = new File("D:/test/images/" + resourceName);

        FileUtils.copyInputStreamToFile(inputStream, file);

    }

    /**
     * 查询流程定义
     */
    @Test
    public void findProcessDefinition() {
        List<ProcessDefinition> list = processEngine.getRepositoryService()
                .createProcessDefinitionQuery() //创建一个流程定义的查询
                /*指定查询条件,where条件*/
//                .deploymentId("305003") //使用部署对象ID查询
//                .processDefinitionId("_3") //使用流程定义ID查询
                .processDefinitionKey("test") //使用流程定义的key查询
//                .processDefinitionNameLike("test") //使用流程定义的名称模糊查询
                /*排序*/
//                .orderByProcessDefinitionVersion().asc() //按照版本的升序排列
                .orderByProcessDefinitionName().desc() //按照流程定义的名称降序排列
                /*返回的结果集*/
                .list(); //返回一个集合列表，封装流程定义
//                .singleResult(); //返回惟一结果集
//                .count(); //返回结果集数量
//                .listPage(firstResult, maxResults); //分页查询
        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                System.out.println("流程定义ID:" + pd.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:" + pd.getName());//对应hello.bpmn文件中的name属性值
                System.out.println("流程定义的key:" + pd.getKey());//对应hello.bpmn文件中的id属性值
                System.out.println("流程定义的版本:" + pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:" + pd.getResourceName());
                System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
                System.out.println("部署对象ID：" + pd.getDeploymentId());
                System.out.println("*********************************************");
            }

        }
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDefinition() {
        //使用部署id完成删除
        String deploymentId = "317501";
        //不带级联的删除，只能删除没有启动的流程，否则报异常
        //processEngine.getRepositoryService().deleteDeployment(deploymentId);
        //级联删除，能删除启动的流程
        processEngine.getRepositoryService().deleteDeployment(deploymentId, true);
        System.out.println("删除成功!");

    }

    /**
     * 查看流程图
     *
     * @throws IOException
     */
    @Test
    public void viewPic() throws IOException {
        String deploymentId = "412501";
        //获取该流程的所有资源名称列表
        List<String> list = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
        String resourceName = "";
        if (list != null) {
            for (String s : list) {
                if (s.contains(".png")) {
                    resourceName = s;
                    break;
                }
            }
        }

        //获取图片输入流
        InputStream inputStream = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);

        //将图片生成到电脑中
        File file = new File("D:/test/images/" + resourceName);

        FileUtils.copyInputStreamToFile(inputStream, file);


    }

    /**
     * 查询最新版本的流程定义
     */
    @Test
    public void findLastVersionProcessDefinition() {
        List<ProcessDefinition> list = processEngine.getRepositoryService().createProcessDefinitionQuery().orderByProcessDefinitionVersion().asc().list();
        Map<String, ProcessDefinition> map = new HashMap<>();
        if (list != null && !list.isEmpty()) {
            for (ProcessDefinition processDefinition : list) {
                map.put(processDefinition.getKey(), processDefinition);
            }
        }

        List<ProcessDefinition> pdList = new ArrayList(map.values());
        if (pdList != null && !pdList.isEmpty()) {
            for (ProcessDefinition pd : pdList) {
                System.out.println("流程定义ID:" + pd.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:" + pd.getName());//对应hello.bpmn文件中的name属性值
                System.out.println("流程定义的key:" + pd.getKey());//对应hello.bpmn文件中的id属性值
                System.out.println("流程定义的版本:" + pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:" + pd.getResourceName());
                System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
                System.out.println("部署对象ID：" + pd.getDeploymentId());
                System.out.println("*********************************************");
            }
        }

    }

    /**
     * 使用流程定义key启动流程实例
     */
    @Test
    public void startProcessInstance() {
        //流程定义key
        String processDefinitionKey = "test1126_17";

        ProcessInstance processInstance = processEngine.getRuntimeService() //与正在执行的流程实例和执行对象相关的service
                .startProcessInstanceByKey(processDefinitionKey, "20191126170758"); //使用流程定义的key与业务id启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

        log.info("流程实例ID:" + processInstance.getId());//流程实例ID
        log.info("流程定义ID:" + processInstance.getProcessDefinitionId());//流程定义ID
        log.info("流程业务id：" + processInstance.getBusinessKey());//业务id
    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findPersonalTask() {
        String assignee = "superadmin";
        List<Task> list = processEngine.getTaskService() //与正在执行的任务管理相关的service
                .createTaskQuery() //创建任务查询对象
                //查询条件
                .taskAssignee(assignee) //指定个人任务查询，指定办理人
//                .taskCandidateUser("candidateUser") //组任务的办理人查询
//                .processDefinitionId("processDefinitionId") //流程定义id查询
//                .processInstanceId("processInstanceId") //流程实例id查询
//                .executionId("executionId") //执行对象id查询
                .orderByTaskCreateTime().asc() //按照创建时间排序
                /*返回结果集*/
//                      .singleResult()//返回惟一结果集
//                      .count()//返回结果集的数量
//                      .listPage(firstResult, maxResults);//分页查询
                .list();
        if (list != null && list.size() > 0) {
            for (Task task : list) {
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
     * 完成我的任务
     */
    @Test
    public void completePersonalTask() {
        //任务id
        String taskId = "335004";
        processEngine.getTaskService().complete(taskId);
        log.info("完成任务：任务id：" + taskId);
    }

    /**
     * 查询流程状态（判断流程走到哪一个节点）
     */
    @Test
    public void isProcessActive() {
        String processInstanceId = "330001";
        ProcessInstance processInstance = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            log.info("流程已结束");
        } else {
            log.info("流程没有结束");
            //获取任务状态
            log.info("节点id：" + processInstance.getActivityId());
        }
    }

    /**
     * 历史活动查询接口
     */
    @Test
    public void findHistoryActivity() {
        String processInstanceId = "330001";
        List<HistoricActivityInstance> list = processEngine.getHistoryService() //与历史数据（历史表）相关的Service
                .createHistoricActivityInstanceQuery() //创建查询条件
                .processInstanceId(processInstanceId)
                .list();
        for (HistoricActivityInstance historicActivityInstance : list) {
            log.info("活动id：" + historicActivityInstance.getActivityId() + "   审批人：" + historicActivityInstance.getAssignee() + "  任务id：" + historicActivityInstance.getTaskId());
            log.info("************************************");
        }
    }
}

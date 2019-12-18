package com.hugang.activiti;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.io.*;
import java.util.*;
import java.util.zip.ZipInputStream;

/**
 * 管理流程定义
 *
 * @author: hg
 * @date: 2019/12/10 16:51
 */
@Slf4j
public class ProcessDefineManage {

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
     * 部署流程定义（压缩文件）
     */
    @Test
    public void deployProcessDefineByZip() throws FileNotFoundException {
        //不加/代表从当前包里找文件
//        InputStream resourceAsStream = this.getClass().getResourceAsStream("helloworld.zip");
        //加/代表从classpath的根目录里找文件
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/helloworld.zip");
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ZipInputStream zipInputStream = new ZipInputStream(resourceAsStream);
        Deployment deployment = repositoryService.createDeployment()
                .name("请假流程001")
                .addZipInputStream(zipInputStream)
                .deploy();
        System.out.println("部署成功：流程id为  " + deployment.getId());
    }

    /**
     * 查询流程部署信息
     */
    @Test
    public void queryDeployment() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<Deployment> deployments = repositoryService.createDeploymentQuery()
                //条件
                .deploymentName("请假流程001")
                //排序
                .orderByDeploymentId().desc()
                //结果集
                .list();
        if (!deployments.isEmpty()){
            for (Deployment deployment : deployments){
                log.info(deployment.getId());
            }
        }
    }

    /**
     * 查询流程部署信息
     */
    @Test
    public void queryProcessDef() {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                //条件
                .processDefinitionKey("HelloWorld")
                //排序
                .orderByProcessDefinitionKey().desc()
                //结果集
                .list();
        if (!processDefinitions.isEmpty()){
            for (ProcessDefinition pd : processDefinitions){
                System.out.println("流程定义ID:" + pd.getId());//流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:" + pd.getName());//对应helloworld.bpmn文件中的name属性值
                System.out.println("流程定义的key:" + pd.getKey());//对应helloworld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:" + pd.getVersion());//当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:" + pd.getResourceName());
                System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
                System.out.println("部署对象ID：" + pd.getDeploymentId());
                System.out.println("*********************************************");
            }
        }
    }

    /**
     * 启动流程
     */
    @Test
    public void startProcess() {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processDefineKey = "HelloWorld";
        Map<String, Object> variables = new HashMap<>();
        variables.put("请假天数", 10);
        variables.put("请假原因", "生病");
        variables.put("请假时间", new Date());
        //启动的时候可以带上流程变量
        ProcessInstance instance =runtimeService.startProcessInstanceByKey(processDefineKey, variables);

//        runtimeService.startProcessInstanceByKey(processDefineKey);
        System.out.println("流程启动成功,流程实例id：" + instance.getId() + "，流程定义id：" + instance.getProcessDefinitionId() + "，" + instance.getProcessInstanceId());
    }

    /**
     * 删除流程定义
     */
    @Test
    public void deleteProcessDef(){
        RepositoryService service = processEngine.getRepositoryService();
        //根据部署id删除流程定义，若此流程定义正在被使用，则会报错

        //service.deleteDeployment("1");
        //根据部署id删除流程定义，若此流程定义正在被使用，则会删除此流程定义相关的流程数据，包括 act_ru_* 和 act_hi_* 表的数据
        //service.deleteDeployment("1", true);

        List<Deployment> deployments = service.createDeploymentQuery().processDefinitionKey("myProcess_1").list();
        if (deployments != null){
            for (Deployment deployment : deployments){
                service.deleteDeployment(deployment.getId(), true);
                System.out.println("删除流程定义【" + deployment.getId() + "】成功");
            }
        }
        //System.out.println("删除流程定义成功");

    }

    /**
     * 查看流程图
     *
     * @throws IOException
     */
    @Test
    public void viewPic() throws IOException {
        RepositoryService repositoryService = processEngine.getRepositoryService();

        //根据流程部署id
        String deploymentId = "5001";
//        //获取该流程的所有资源名称列表
//        List<String> list = processEngine.getRepositoryService().getDeploymentResourceNames(deploymentId);
        //定义图片资源的名称
//        String resourceName = "";
//        if (list != null) {
//            for (String s : list) {
//                if (s.contains(".png")) {
//                    resourceName = s;
//                    break;
//                }
//            }
//        }
//
//        //获取图片输入流
//        InputStream inputStream = processEngine.getRepositoryService().getResourceAsStream(deploymentId, resourceName);
//
//        //将图片生成到电脑中
//        File file = new File("D:/test/images/" + resourceName);
//
//        FileUtils.copyInputStreamToFile(inputStream, file);

        //根据流程部署id也可先查出流程定义id，再根据流程定义id查看流程图
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();

        //根据流程定义id
        String processDefId = definition.getId();

        InputStream processDiagram = repositoryService.getProcessDiagram(processDefId);
        File file = new File("D:/test/images/" + definition.getDiagramResourceName());
        try {
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            int len = 0;
            byte[] bytes = new byte[1024];
            while (-1 != (len = processDiagram.read(bytes))){
                bufferedOutputStream.write(bytes, 0, len);
            }
            bufferedOutputStream.flush();
            bufferedOutputStream.close();
            processDiagram.close();
        } catch (Exception e){
            e.printStackTrace();
        }
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
}

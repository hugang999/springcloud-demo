package com.hugang.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Test;

/**
 * 初始化工作流表
 *
 * @author: hg
 * @date: 2019/12/10 10:10
 */
public class InitTable {


    /**
     * 第一种方法：使用流程引擎配置类
     */
    @Test
    public void initTable_1(){


        //配置数据源
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        dataSource.setUrl("jdbc:mysql://192.168.164.129:3306/activiti?useUnicode=true&amp;characterEncoding=utf8");
//        dataSource.setUsername("root");
//        dataSource.setPassword("root");

        //创建流程引擎配置
        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration();
        configuration.setJdbcDriver("com.mysql.jdbc.Driver");
        configuration.setJdbcUrl("jdbc:mysql://192.168.164.129:3306/activiti?useUnicode=true&amp;characterEncoding=utf8");
        configuration.setJdbcUsername("root");
        configuration.setJdbcPassword("root");
//        configuration.setDataSource(dataSource);

        /**
         * 配置表的初始化的方式
         * DB_SCHEMA_UPDATE_FALSE = "false" 数据库没有activiti相关表也不添加
         * DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop" 创建表，使用完之后删除
         * DB_SCHEMA_UPDATE_TRUE = "true" 如果数据库中没有表，就创建
         * "drop-create":删除并创建
         */
        configuration.setDatabaseSchemaUpdate("drop-create");

        //获取流程引擎
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
    }



    /**
     * 第二种方法：使用配置文件activiti.cfg.xml
     */
    @Test
    public void initTable_2(){

        ProcessEngineConfiguration configuration = ProcessEngineConfiguration
                .createProcessEngineConfigurationFromResource("activiti.cfg.xml");

        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
    }

    /**
     * 第三种方法：使用配置文件activiti.cfg.xml并获取默认流程引擎
     */
    @Test
    public void initTable_3(){

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        System.out.println(processEngine);
    }
}

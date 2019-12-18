package com.hugang.test;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.identity.Group;

/**
 * @author: hg
 * @date: 2019/11/28 19:23
 */
public class SaveGroup {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        IdentityService identityService = processEngine.getIdentityService();
        //添加用户组数据
        for (int i = 1; i < 11; i++) {

            Group group = identityService.newGroup(String.valueOf(i));
            group.setName("group_" + i);
            group.setType("type_" + i);
            identityService.saveGroup(group);
        }
    }

}

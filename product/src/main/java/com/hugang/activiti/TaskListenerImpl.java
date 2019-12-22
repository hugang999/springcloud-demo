package com.hugang.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * 任务监听器【测试失败，未找到原因，暂时不用此方法】
 * @author: hg
 * @date: 2019/12/19 09:49
 */

public class TaskListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进入任务监听器");
        String assignee = "李四";
        delegateTask.setAssignee(assignee);
    }
}

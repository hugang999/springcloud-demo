package com.hugang.activiti;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 *
 * @author: hg
 * @date: 2019/12/20 16:53
 */
public class GroupTaskListenerImpl implements TaskListener {

    @Override
    public void notify(DelegateTask delegateTask) {
        System.out.println("进入组任务监听器");
        delegateTask.addCandidateUser("小A");
        delegateTask.addCandidateUser("小B");
        delegateTask.addCandidateUser("小C");
        delegateTask.addCandidateUser("小D");
        System.out.println("组任务候选人设置完成");
    }
}

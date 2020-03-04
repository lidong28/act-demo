package com.ld.demo;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ActivitiTest {
    @Test
    public  void creatActivitiTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .name("请假流程")
                .addClasspathResource("shengqing.bpmn")
                .addClasspathResource("shengqing.png")
                .deploy();
    }
    @Test
    public void testStartProcessInstance(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService().startProcessInstanceById("myProcess_1:1:4");
    }
    @Test
    public void testQingjia() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("2505"); //查看act_ru_task表
    }

    @Test
    public void testQueryTask(){
        //下面代码中的小毛，就是我们之前设计那个流程图中添加的班主任内容
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("小毛")
                .list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }
    }

    /**
     * 班主任小毛完成任务
     */
    @Test
    public void testFinishTask_manager(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        engine.getTaskService()
                .complete("5002"); //查看act_ru_task数据表
    }

    /**
     * 教务处的大毛完成的任务
     */
    @Test
    public void testFinishTask_Boss() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("7502");  //查看act_ru_task数据表
    }

    /**
     * 根据名称查询流程部署act_re_deployment
     */
    @Test
    public void testQueryDeploymentByName(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Deployment> deployments = processEngine.getRepositoryService().createDeploymentQuery().orderByDeploymenTime().desc().deploymentName("请假流程").list();
        for(Deployment deployment:deployments){
            System.out.println(deployment.getId());
        }
    }

    /**
     * 查询所有的流程定义 act_re_procdef
     */
    @Test
    public void testQueryAllPD(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> pdList = processEngine.getRepositoryService()
                .createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion()
                .desc().list();
        for(ProcessDefinition processDefinition:pdList){
            System.out.println(processDefinition.getName());
        }
    }
}

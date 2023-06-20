package com.repins.db.mysql;

import com.repins.infinite.db.mysql.MybatisProcessRepositoryFinder;
import com.repins.infinite.engine.InfiniteProcessEngine;
import com.repins.infinite.engine.ProcessEngine;
import com.repins.infinite.engine.command.CompleteTaskCmd;
import com.repins.infinite.engine.command.CreateDeploymentCmd;
import com.repins.infinite.engine.command.DeployDeploymentCmd;
import com.repins.infinite.engine.command.StartProcessInstanceCmd;
import com.repins.infinite.engine.configuration.DefaultProcessEngineConfiguration;
import com.repins.infinite.engine.service.DeploymentService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/application-test.xml")
public class CommonTest {

    private ProcessEngine processEngine;

    private static final String json = "[\n" +
            "  {\n" +
            "    \"key\": \"START_EVENT1\",\n" +
            "    \"name\": \"开始\",\n" +
            "    \"type\": \"START_EVENT\",\n" +
            "    \"outgoing\": [\n" +
            "      \"SEQUENCE_FLOW1\",\n" +
            "      \"SEQUENCE_FLOW3\"\n" +
            "    ],\n" +
            "    \"incoming\": []\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"SEQUENCE_FLOW1\",\n" +
            "    \"name\": \"\",\n" +
            "    \"type\": \"SEQUENCE_FLOW\",\n" +
            "    \"outgoing\": [\n" +
            "      \"USER_TASK1\"\n" +
            "    ],\n" +
            "    \"incoming\": [\n" +
            "      \"START_EVENT1\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"USER_TASK1\",\n" +
            "    \"name\": \"用户节点1\",\n" +
            "    \"type\": \"USER_TASK\",\n" +
            "    \"outgoing\": [\n" +
            "      \"SEQUENCE_FLOW2\"\n" +
            "    ],\n" +
            "    \"incoming\": [\n" +
            "      \"SEQUENCE_FLOW1\"\n" +
            "    ],\n" +
            "    \"taskAssigneeExtensionElement\": {\n" +
            "      \"assignee\": \"user1\",\n" +
            "      \"assigneeType\": \"user\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"SEQUENCE_FLOW2\",\n" +
            "    \"name\": \"\",\n" +
            "    \"type\": \"SEQUENCE_FLOW\",\n" +
            "    \"outgoing\": [\n" +
            "      \"END_EVENT1\"\n" +
            "    ],\n" +
            "    \"incoming\": [\n" +
            "      \"USER_TASK1\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"SEQUENCE_FLOW3\",\n" +
            "    \"name\": \"\",\n" +
            "    \"type\": \"SEQUENCE_FLOW\",\n" +
            "    \"outgoing\": [\n" +
            "      \"USER_TASK2\"\n" +
            "    ],\n" +
            "    \"incoming\": [\n" +
            "      \"START_EVENT1\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"USER_TASK2\",\n" +
            "    \"name\": \"用户节点2\",\n" +
            "    \"type\": \"USER_TASK\",\n" +
            "    \"outgoing\": [\n" +
            "      \"SEQUENCE_FLOW4\"\n" +
            "    ],\n" +
            "    \"incoming\": [\n" +
            "      \"SEQUENCE_FLOW3\"\n" +
            "    ],\n" +
            "    \"taskAssigneeExtensionElement\": {\n" +
            "      \"assignee\": \"user2\",\n" +
            "      \"assigneeType\": \"user\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"SEQUENCE_FLOW4\",\n" +
            "    \"name\": \"\",\n" +
            "    \"type\": \"SEQUENCE_FLOW\",\n" +
            "    \"outgoing\": [\n" +
            "      \"END_EVENT1\"\n" +
            "    ],\n" +
            "    \"incoming\": [\n" +
            "      \"USER_TASK2\"\n" +
            "    ]\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"END_EVENT1\",\n" +
            "    \"name\": \"结束节点\",\n" +
            "    \"type\": \"END_EVENT\",\n" +
            "    \"outgoing\": [],\n" +
            "    \"incoming\": [\n" +
            "      \"SEQUENCE_FLOW2\",\n" +
            "      \"SEQUENCE_FLOW4\"\n" +
            "    ]\n" +
            "  }\n" +
            "]";


    @Before
    public void initEngine() {
        DefaultProcessEngineConfiguration processEngineConfiguration = DefaultProcessEngineConfiguration.defaultConfig();
        processEngineConfiguration.setProcessEngineRepositoryFinder(new MybatisProcessRepositoryFinder());
        processEngine = new InfiniteProcessEngine(processEngineConfiguration);
        processEngine.init();
    }

    @Test
    public void getDeploymentService() {
        DeploymentService deploymentService = processEngine.getDeploymentService();
        System.out.println(deploymentService);
    }

    @Test
    public void saveDeployment() {
        CreateDeploymentCmd cmd = new CreateDeploymentCmd();
        cmd.setMetaInfo(json);
        cmd.setCategory("测试类型");
        cmd.setName("测试deployment1");
        cmd.setCreateBy("user1");
        cmd.setRemark("备注测试");
        String deploymentId = processEngine.getDeploymentService().createDeployment(cmd);

        DeployDeploymentCmd deploymentCmd = new DeployDeploymentCmd();
        deploymentCmd.setDeploymentId(deploymentId);
        deploymentCmd.setDeployBy("user2");
        String deploy = processEngine.getDeploymentService().deploy(deploymentCmd);
        Assert.assertNotNull(deploy);
        System.out.println(deploy);
    }

    @Test
    public void startProcessInstance() {
        StartProcessInstanceCmd cmd = new StartProcessInstanceCmd();
        cmd.setStartBy("user_start");
        cmd.setProcessName("测试流程111");
        cmd.setDeploymentId("1b8ceb3b-82e9-4aac-9f89-137883c4ea9b");
        processEngine.getProcessInstanceService().startProcessInstance(cmd);
    }

    @Test
    public void completeTask(){
        CompleteTaskCmd cmd = new CompleteTaskCmd();
        cmd.setAssignee("user2");
        cmd.setTaskId("e7ff6a44-4e4d-4afb-8f27-7eb302433ade");
        cmd.setRemark("暂时没有");
        processEngine.getTaskInstanceService().complete(cmd);
    }
}

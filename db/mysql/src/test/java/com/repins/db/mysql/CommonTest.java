package com.repins.db.mysql;

import com.repins.infinite.db.mysql.MybatisProcessRepositoryFinder;
import com.repins.infinite.engine.InfiniteProcessEngine;
import com.repins.infinite.engine.ProcessEngine;
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
            "    \"outgoing\": [\"SEQUENCE_FLOW1\"],\n" +
            "    \"incoming\": []\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"SEQUENCE_FLOW1\",\n" +
            "    \"name\": \"\",\n" +
            "    \"type\": \"SEQUENCE_FLOW\",\n" +
            "    \"outgoing\": [\"USER_TASK1\"],\n" +
            "    \"incoming\": [\"START_EVENT1\"]\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"USER_TASK1\",\n" +
            "    \"name\": \"\",\n" +
            "    \"type\": \"USER_TASK\",\n" +
            "    \"outgoing\": [\"SEQUENCE_FLOW2\"],\n" +
            "    \"incoming\": [\"SEQUENCE_FLOW1\"],\n" +
            "    \"taskAssigneeExtensionElement\": {\n" +
            "      \"assignee\": \"user1\",\n" +
            "      \"assigneeType\": \"user\"\n" +
            "    }\n" +
            "  },\n" +
            "  {\n" +
            "    \"key\": \"END_EVENT1\",\n" +
            "    \"name\": \"结束节点\",\n" +
            "    \"type\": \"END_EVENT\",\n" +
            "    \"outgoing\": [],\n" +
            "    \"incoming\": [\"USER_TASK1\"]\n" +
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
        cmd.setDeploymentId("f29c0885-1d20-40fc-b8f3-ee3a364356b0");
        processEngine.getProcessInstanceService().startProcessInstance(cmd);
    }

}

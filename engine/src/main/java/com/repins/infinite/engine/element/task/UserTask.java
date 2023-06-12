package com.repins.infinite.engine.element.task;

import com.repins.infinite.engine.element.base.Activity;
import com.repins.infinite.engine.element.ext.ExtensionElement;
import com.repins.infinite.engine.element.ext.TaskAssigneeExtensionElement;

public class UserTask extends Activity {

    private ExtensionElement extensionElement;

    private TaskAssigneeExtensionElement taskAssigneeExtensionElement;

    public ExtensionElement getExtensionElement() {
        return extensionElement;
    }

    public void setExtensionElement(ExtensionElement extensionElement) {
        this.extensionElement = extensionElement;
    }

    public TaskAssigneeExtensionElement getTaskAssigneeExtensionElement() {
        return taskAssigneeExtensionElement;
    }

    public void setTaskAssigneeExtensionElement(TaskAssigneeExtensionElement taskAssigneeExtensionElement) {
        this.taskAssigneeExtensionElement = taskAssigneeExtensionElement;
    }
}

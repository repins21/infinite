package com.repins.infinite.engine.element.task;

import com.repins.infinite.engine.element.base.Activity;
import com.repins.infinite.engine.element.ext.ExtensionElement;

public class UserTask extends Activity {

    private ExtensionElement extensionElement;

    public ExtensionElement getExtensionElement() {
        return extensionElement;
    }

    public void setExtensionElement(ExtensionElement extensionElement) {
        this.extensionElement = extensionElement;
    }

}

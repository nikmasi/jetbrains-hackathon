package org.delite.alt.ctrl.taskoplugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import javax.swing.JSeparator
import javax.swing.SwingConstants

class TaskoToolWindow : ToolWindowFactory {
    override fun shouldBeAvailable(project: Project) = true

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow()
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    class MyToolWindow {
        private val content = JBPanel<JBPanel<*>>().apply {
            if (true /* Check if already logged in! */) {
                add(JBBox.createVerticalBox().apply {
                    add(UserLoginWindow().getContent())
                    add(JSeparator(SwingConstants.HORIZONTAL))
                    add(UserRegistrationWindow().getContent())
                })
            }
        }

        fun getContent(): JBPanel<JBPanel<*>> = content
    }
}
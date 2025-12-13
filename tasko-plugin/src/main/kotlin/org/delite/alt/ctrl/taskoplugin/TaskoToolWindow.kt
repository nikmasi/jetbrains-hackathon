package org.delite.alt.ctrl.taskoplugin

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import java.awt.Component

class TaskoToolWindow : ToolWindowFactory {
    override fun shouldBeAvailable(project: Project) = true

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow()
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    class MyToolWindow {
        private val content = JBPanel<JBPanel<*>>().apply {
            layout = BorderLayout()

            if (false /* TODO: Check if already logged in! */) {
                add(JBScrollPane(JBBox.createVerticalBox().apply {
                    alignmentY = Component.TOP_ALIGNMENT
                    add(UserLoginComponent().getContent().apply { maximumSize = preferredSize })
                    add(UserRegistrationComponent().getContent().apply { maximumSize = preferredSize })
                }), BorderLayout.CENTER)
            }
            else {

            }
        }

        fun getContent(): JBPanel<JBPanel<*>> = content
    }
}
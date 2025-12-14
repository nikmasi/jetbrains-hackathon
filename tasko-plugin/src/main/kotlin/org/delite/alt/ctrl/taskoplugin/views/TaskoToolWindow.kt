package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import org.delite.alt.ctrl.taskoplugin.services.TaskoLoginStateService
import java.awt.BorderLayout
import java.awt.Component

class TaskoToolWindow : ToolWindowFactory {
    override fun shouldBeAvailable(project: Project) = true

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = TaskoToolComponent(project)
        val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    class TaskoToolComponent(val project: Project) {
        private val content = JBPanel<JBPanel<*>>()

        init {
            refreshUI()
        }

        fun getContent(): JBPanel<JBPanel<*>> = content

        fun onLoginSuccess(username: String, password: String): Unit {
            TaskoLoginStateService.getInstance().state.username = username
            TaskoLoginStateService.getInstance().state.password = password
            refreshUI()
        }

        fun onLogout() {
            TaskoLoginStateService.getInstance().state.username = ""
            TaskoLoginStateService.getInstance().state.password = ""
            TaskoLoginStateService.getInstance().state.taskListIdx = -1
            refreshUI()
        }

        fun refreshUI() {
            content.removeAll()

            content.apply {
                layout = BorderLayout()

                if (!TaskoLoginStateService.getInstance().state.loggedIn()) {
                    add(JBScrollPane(JBBox.createVerticalBox().apply {
                        alignmentY = Component.TOP_ALIGNMENT
                        add(UserLoginComponent(::onLoginSuccess).getContent().apply { maximumSize = preferredSize })
                        add(UserRegistrationComponent(::onLoginSuccess).getContent().apply { maximumSize = preferredSize })
                    }), BorderLayout.CENTER)
                }
                else {
                    add(JBScrollPane(JBBox.createVerticalBox().apply {
                        alignmentY = Component.TOP_ALIGNMENT
                        add(BoardComponent(project, ::onLogout).getContent().apply { })
                    }), BorderLayout.CENTER)
                }
            }

            content.revalidate()
            content.repaint()
        }
    }
}
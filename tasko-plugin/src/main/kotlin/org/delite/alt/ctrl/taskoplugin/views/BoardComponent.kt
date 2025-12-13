package org.delite.alt.ctrl.taskoplugin.views

import java.awt.Color
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import org.delite.alt.ctrl.taskoplugin.models.Task
import org.delite.alt.ctrl.taskoplugin.services.TaskService

class BoardComponent {
    private val content = JBPanel<JBPanel<*>>().apply {
        add(JBBox.createHorizontalBox().apply {
            for (i in 1..3) {
                add(JBBox.createVerticalBox().apply {
                    for (t in TaskService.getTasks(1)) {
                        add(TaskComponent(t).getContent())
                    }

                    for (t in TaskService.getTasks(1)) {
                        add(TaskComponent(t).getContent())
                    }

                    for (t in TaskService.getTasks(1)) {
                        add(TaskComponent(t).getContent())
                    }
                })
            }
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

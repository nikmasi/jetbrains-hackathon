package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import org.delite.alt.ctrl.taskoplugin.common.TaskoConstants
import org.delite.alt.ctrl.taskoplugin.models.Task
import javax.swing.JSeparator
import javax.swing.SwingConstants

class TaskComponent(val task: Task) {
    private val content = JBPanel<JBPanel<*>>().apply {
        add(JBBox.createVerticalBox().apply {
            add(JBLabel(task.title))
            add(JSeparator(SwingConstants.VERTICAL))
            if (task.body.length > TaskoConstants.TASK_MAXIMUM_BODY_SIZE) {
                add(JBLabel("${task.body.take(TaskoConstants.TASK_MAXIMUM_BODY_SIZE)}..."))
            }
            else {
                add(JBLabel(task.body))
            }
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

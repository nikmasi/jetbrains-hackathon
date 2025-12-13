package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.ui.JBColor
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import org.delite.alt.ctrl.taskoplugin.common.TaskoConstants
import org.delite.alt.ctrl.taskoplugin.models.Task
import javax.swing.JSeparator
import javax.swing.SwingConstants
import java.awt.Color
import java.awt.Font
import javax.swing.JPanel

class TaskComponent(val task: Task) {
    private val content = JBPanel<JBPanel<*>>().apply {
        isOpaque = true
        background = JBColor(
            Color(245, 245, 245), // light theme
            Color(60, 63, 65)       // dark theme
        )

        add(JBBox.createVerticalBox().apply {
            add(JBLabel(task.title).apply {
                font = font.deriveFont(Font.BOLD, 16f)
                horizontalAlignment = SwingConstants.CENTER
            })

            add(JSeparator(SwingConstants.HORIZONTAL))

            if (task.body_text.length > TaskoConstants.TASK_MAXIMUM_BODY_SIZE)
                add(JBLabel("${task.body_text.take(TaskoConstants.TASK_MAXIMUM_BODY_SIZE)}..."))
            else
                add(JBLabel(task.body_text))
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

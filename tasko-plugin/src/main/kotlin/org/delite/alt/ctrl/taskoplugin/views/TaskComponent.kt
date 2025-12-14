package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.ui.JBColor
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.util.minimumHeight
import org.delite.alt.ctrl.taskoplugin.models.Task
import java.awt.BorderLayout
import javax.swing.JSeparator
import javax.swing.SwingConstants
import java.awt.Color
import java.awt.Font
import javax.swing.JButton

class TaskComponent(val task: Task) {
    private val content = JBPanel<JBPanel<*>>().apply {
        isOpaque = true
        background = JBColor(
            Color(245, 245, 245), // light theme
            Color(60, 63, 65)       // dark theme
        )

        layout = BorderLayout()

        add(JBBox.createVerticalBox().apply {
            add(JBLabel(task.title).apply { font = font.deriveFont(Font.BOLD, 16f) })

            // add(JSeparator(SwingConstants.HORIZONTAL))

            if (task.body_text.length > 100)
                add(JBLabel("${task.body_text.take(100)}..."))
            else
                add(JBLabel(task.body_text))

            add(JButton("Edit Task"))
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

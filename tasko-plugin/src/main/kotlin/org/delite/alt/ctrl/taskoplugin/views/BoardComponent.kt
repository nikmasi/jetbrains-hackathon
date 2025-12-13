package org.delite.alt.ctrl.taskoplugin.views

import java.awt.Color
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import org.delite.alt.ctrl.taskoplugin.models.Task

class BoardComponent {
    private val content = JBPanel<JBPanel<*>>().apply {
        add(JBBox.createHorizontalBox().apply {
            add(JBBox.createVerticalBox().apply {
                background = Color.decode("#454545")

                // add(TaskComponent().getContent())
            })
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

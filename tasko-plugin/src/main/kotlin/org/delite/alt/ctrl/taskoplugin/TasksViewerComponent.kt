package org.delite.alt.ctrl.taskoplugin

import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBScrollPane
import java.awt.BorderLayout
import java.awt.Component

class TasksViewerComponent {
    private val content = JBPanel<JBPanel<*>>().apply {
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class TaskEditorDialog : DialogWrapper(true) {
    init {
        title = "Task Editor Dialog"
        init()
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel()
        panel.add(JLabel("This is a popup window"))
        return panel
    }
}
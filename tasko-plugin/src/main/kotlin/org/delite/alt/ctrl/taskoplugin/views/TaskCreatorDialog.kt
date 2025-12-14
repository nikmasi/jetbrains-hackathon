package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.EditorTextField
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.JBUI
import org.delite.alt.ctrl.taskoplugin.models.Task
import java.awt.BorderLayout
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel
import java.awt.Dimension
import javax.swing.JTextArea
import javax.swing.JTextField

class TaskCreatorDialog(val project: Project, val onSuccess: (title: String, body_text: String) -> Unit) : DialogWrapper(project) {
    private val titleField = JBTextField()
    private val bodyTextArea = JTextArea(8, 40)

    init {
        title = "Task Creator Dialog"
        setResizable(true)
        init()
    }

    override fun createCenterPanel(): JComponent {
        val root = JPanel(BorderLayout(0, 10))
        root.border = JBUI.Borders.empty(10)

        val titleRow = JPanel(BorderLayout(8, 0))
        titleRow.add(JBLabel("Title:"), BorderLayout.WEST)
        titleRow.add(titleField, BorderLayout.CENTER)

        root.add(titleRow, BorderLayout.NORTH)

        bodyTextArea.apply {
            lineWrap = true
            wrapStyleWord = true
        }

        val bodyLabel = JBLabel("Text Body:")
        val bodyScroll = JBScrollPane(bodyTextArea)

        val bodyPanel = JPanel(BorderLayout(0, 5))
        bodyPanel.add(bodyLabel, BorderLayout.NORTH)
        bodyPanel.add(bodyScroll, BorderLayout.CENTER)

        root.add(bodyPanel, BorderLayout.CENTER)

        return root
    }

    override fun doOKAction() {
        val title = titleField.text
        val body = bodyTextArea.text
        onSuccess(title, body)
        super.doOKAction()
    }

    override fun doCancelAction() {
        super.doCancelAction()
    }
}

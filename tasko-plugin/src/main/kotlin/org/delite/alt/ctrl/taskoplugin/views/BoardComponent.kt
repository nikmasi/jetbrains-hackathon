package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.openapi.project.Project
import com.intellij.ui.JBColor
import java.awt.Color
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.openapi.ui.ComboBox
import org.delite.alt.ctrl.taskoplugin.models.Task
import org.delite.alt.ctrl.taskoplugin.services.TaskListService
import org.delite.alt.ctrl.taskoplugin.services.TaskService
import org.jetbrains.kotlin.idea.gradleTooling.get
import com.intellij.openapi.ui.Messages
import org.delite.alt.ctrl.taskoplugin.models.TaskList
import javax.swing.BoxLayout
import javax.swing.JButton
import javax.swing.JPanel
import java.awt.event.ItemEvent
import javax.swing.Box

class BoardComponent(val project: Project) {
    private val content = JBPanel<JBPanel<*>>()

    init {
        refreshUI(1)
    }

    fun refreshUI(taskListIdx: Int) {
        content.removeAll()

        content.add(JBBox.createVerticalBox().apply {
            val taskLists = TaskListService.getTaskListsByProject(1)
            val taskListSwitcher = ComboBox(taskLists.map { it.name }.toTypedArray())

            taskListSwitcher.addItemListener { e ->
                if (e.stateChange == ItemEvent.SELECTED) {
                    val index = taskListSwitcher.selectedIndex
                    refreshUI(taskLists[index].id)
                }
            }

            add(JPanel().apply {
                add(taskListSwitcher)
                add(JButton("+").apply {
                    addActionListener {
                        val ime: String = Messages.showInputDialog(project, "Enter name of the list!", "Name", Messages.getQuestionIcon()) ?: "ERROR"
                        /* TODO: Dodati logiku dodavanja */
                    }
                })
            })

            add(JBBox.createVerticalBox().apply {
                for (t in TaskService.getTasks(taskListIdx)) {
                    add(TaskComponent(t).getContent())
                    add(Box.createVerticalStrut(10))
                }
            })
        })

        content.revalidate()
        content.repaint()
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

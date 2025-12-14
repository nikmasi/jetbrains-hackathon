package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.openapi.ui.ComboBox
import org.delite.alt.ctrl.taskoplugin.services.TaskListService
import org.delite.alt.ctrl.taskoplugin.services.TaskService
import com.intellij.openapi.ui.Messages
import java.awt.BorderLayout
import javax.swing.JButton
import javax.swing.JPanel
import java.awt.event.ItemEvent
import javax.swing.Box
import java.awt.Dimension

class BoardComponent(val project: Project, val onLogout: () -> Unit) {
    private val content = JBPanel<JBPanel<*>>()

    init {
        refreshUI(1)
    }

    fun refreshUI(taskListIdx: Int) {
        content.removeAll()

        content.add(JBBox.createVerticalBox().apply {
            val taskLists = TaskListService.getTaskListsByProject(1).sortedBy { it.position }
            val taskListSwitcher = ComboBox(taskLists.map { it.name }.toTypedArray())
            taskListSwitcher.selectedIndex = taskLists.indexOfFirst { it.id == taskListIdx }

            taskListSwitcher.addItemListener { e ->
                if (e.stateChange == ItemEvent.SELECTED) {
                    val index = taskListSwitcher.selectedIndex
                    refreshUI(taskLists[index].id)
                }
            }

            add(JPanel().apply {
                add(taskListSwitcher)
                add(JButton("Add Task List").apply {
                    addActionListener {
                        val ime: String? = Messages.showInputDialog(project, "Enter name of the list!", "Name", Messages.getQuestionIcon())
                        if (ime != null) {
                            TaskListService.addTaskList(1, ime)
                            refreshUI(taskListIdx)
                        }
                        else {
                            Messages.showMessageDialog(project, "You did not enter valid name!", "Error!", Messages.getInformationIcon())
                        }
                    }
                })
            })

            add(JPanel(BorderLayout()).apply {
                add(JButton("Logout").apply {
                    addActionListener {
                        onLogout()
                    }
                }, BorderLayout.CENTER)
            })

            add(JBBox.createHorizontalBox().apply {
                add(JButton("Create Task").apply {
                    alignmentX = JPanel.CENTER_ALIGNMENT
                    maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)

                    addActionListener {
                        TaskCreatorDialog(project, {
                            title, bodyText ->
                            TaskService.addTask(taskListIdx, title, bodyText)
                        }).show()

                        refreshUI(taskListIdx)
                    }
                })

                add(JButton("TODOs to Tasks").apply {
                    alignmentX = JPanel.CENTER_ALIGNMENT
                    maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)
                })
            })

            add(Box.createVerticalStrut(10))

            add(JBBox.createVerticalBox().apply {
                for (t in TaskService.getTasks(taskListIdx).sortedBy { it.position }) {
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

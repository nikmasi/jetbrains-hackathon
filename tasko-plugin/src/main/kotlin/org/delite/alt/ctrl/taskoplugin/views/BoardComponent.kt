package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.openapi.application.ApplicationManager
import javax.swing.Timer
import com.intellij.openapi.project.Project
import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.openapi.ui.ComboBox
import org.delite.alt.ctrl.taskoplugin.services.TaskListService
import org.delite.alt.ctrl.taskoplugin.services.TaskService
import com.intellij.openapi.ui.Messages
import com.intellij.openapi.wm.ToolWindow
import org.delite.alt.ctrl.taskoplugin.services.ProjectService
import org.delite.alt.ctrl.taskoplugin.services.TaskoLoginStateService
import org.delite.alt.ctrl.taskoplugin.utilities.CommentsToTasks
import java.awt.BorderLayout
import java.awt.Component.LEFT_ALIGNMENT
import javax.swing.JButton
import javax.swing.JPanel
import java.awt.event.ItemEvent
import javax.swing.Box
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.BoxLayout

class BoardComponent(val project: Project, val onLogout: () -> Unit) {
    private val content = JBPanel<JBPanel<*>>()

    init {
        ProjectService.createProject(project.name, TaskoLoginStateService.getInstance().state.username)
        refreshUI(TaskListService.getTaskListsByProject(TaskoLoginStateService.getInstance().state.username,project.name)[0].id)
    }

    fun refreshUI(taskListIdx: Int) {
        TaskoLoginStateService.getInstance().state.taskListIdx = taskListIdx
        content.removeAll()

        content.add(JBBox.createVerticalBox().apply {
            val username = TaskoLoginStateService.getInstance().state.username
            val taskLists = TaskListService.getTaskListsByProject(username, project.name).sortedBy { it.position }

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
                        var dialog = TaskCreatorDialog(project, {
                            title, bodyText ->
                            TaskService.addTask(taskListIdx, title, bodyText)
                        })

                        dialog.window.addWindowListener(object : WindowAdapter() {
                            override fun windowClosed(e: WindowEvent?) {
                                refreshUI(taskListIdx)
                            }
                        })

                        dialog.show()
                    }
                })

                add(JButton("TODOs to Tasks").apply {
                    alignmentX = JPanel.CENTER_ALIGNMENT
                    maximumSize = Dimension(Int.MAX_VALUE, preferredSize.height)

                    addActionListener {
                        ApplicationManager.getApplication().executeOnPooledThread {
                            ApplicationManager.getApplication().runReadAction {
                                val ctt = CommentsToTasks()
                                ctt.convertTodoToTasks(taskListIdx, ctt.getActiveKtFile(project)!!)

                                ApplicationManager.getApplication().invokeLater {
                                    refreshUI(taskListIdx)
                                }
                            }
                        }
                    }
                })
            })

            add(Box.createVerticalStrut(10))

            add(JBPanel<JBPanel<*>>().apply {
                layout = BoxLayout(this, BoxLayout.Y_AXIS)

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
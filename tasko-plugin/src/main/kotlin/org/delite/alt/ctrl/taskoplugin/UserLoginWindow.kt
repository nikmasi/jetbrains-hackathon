package org.delite.alt.ctrl.taskoplugin

import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import java.awt.BorderLayout
import java.awt.Font
import javax.swing.Box
import javax.swing.JButton
import javax.swing.SwingConstants

class UserLoginWindow {
    private val content = JBPanel<JBPanel<*>>().apply {
        add(JBBox.createVerticalBox().apply {
            val usernameField = JBTextField().apply { columns = 30 }
            val passwordField = JBPasswordField().apply { columns = 30 }
            val loginButton = JButton("Login")

            add(JBLabel("~ Login ~").apply {
                font = font.deriveFont(Font.BOLD, 16f)
                horizontalAlignment = SwingConstants.CENTER
            }, BorderLayout.NORTH)
            add(Box.createVerticalStrut(10))

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(JBLabel("Username: "), BorderLayout.WEST)
                add(usernameField, BorderLayout.CENTER)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(JBLabel("Password: "), BorderLayout.WEST)
                add(passwordField, BorderLayout.CENTER)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(loginButton, BorderLayout.CENTER)
            })
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}
package org.delite.alt.ctrl.taskoplugin

import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField

import java.awt.BorderLayout
import java.awt.Font
import java.awt.GridLayout
import javax.swing.Box
import javax.swing.JButton
import javax.swing.SwingConstants

class UserRegistrationWindow {
    private val content = JBPanel<JBPanel<*>>().apply {
        add(JBBox.createVerticalBox().apply {
            val firstNameField = JBTextField().apply { columns = 30 }
            val lastNameField = JBTextField().apply { columns = 30 }
            val emailField = JBTextField().apply { columns = 30 }
            val usernameField = JBTextField().apply { columns = 30 }
            val firstPasswordField = JBPasswordField().apply { columns = 30 }
            val secondPasswordField = JBPasswordField().apply { columns = 30 }
            val registerButton = JButton("Register")

            add(JBLabel("~ Register ~").apply {
                font = font.deriveFont(Font.BOLD, 16f)
                horizontalAlignment = SwingConstants.CENTER
            }, BorderLayout.NORTH)
            add(Box.createVerticalStrut(10))

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(JBLabel("Firstname: "), BorderLayout.WEST)
                add(firstNameField, BorderLayout.CENTER)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(JBLabel("Lastname: "), BorderLayout.WEST)
                add(lastNameField, BorderLayout.CENTER)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(JBLabel("Email: "), BorderLayout.WEST)
                add(emailField, BorderLayout.CENTER)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(JBLabel("Username: "), BorderLayout.WEST)
                add(usernameField, BorderLayout.CENTER)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(JBLabel("Password: "), BorderLayout.WEST)
                add(firstPasswordField, BorderLayout.CENTER)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(JBLabel("Confirm password: "), BorderLayout.WEST)
                add(secondPasswordField, BorderLayout.CENTER)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(registerButton, BorderLayout.CENTER)
            })
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

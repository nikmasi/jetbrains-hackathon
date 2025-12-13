package org.delite.alt.ctrl.taskoplugin.views

import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField
import org.delite.alt.ctrl.taskoplugin.models.User
import org.delite.alt.ctrl.taskoplugin.services.UserService
import java.awt.BorderLayout
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.Box
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.SwingConstants

class UserLoginComponent(private val onLoginSuccess: (User?) -> Unit) {
    private val content = JBPanel<JBPanel<*>>().apply {
        add(JBBox.createVerticalBox().apply {
            val usernameField = JBTextField().apply { columns = 30 }
            val passwordField = JBPasswordField().apply { columns = 30 }
            val loginButton = JButton("Login")

            add(JBLabel("~ Login ~").apply {
                font = font.deriveFont(Font.BOLD, 16f)
                horizontalAlignment = SwingConstants.CENTER
            })

            add(Box.createVerticalStrut(10))

            add(JBPanel<JBPanel<*>>(GridBagLayout()).apply {
                val gbc = GridBagConstraints().apply {
                    fill = GridBagConstraints.HORIZONTAL
                    insets = Insets(6, 6, 6, 6)
                }

                fun addRow(y: Int, labelText: String, field: JComponent) {
                    gbc.gridx = 0
                    gbc.gridy = y
                    gbc.weightx = 0.0
                    gbc.anchor = GridBagConstraints.EAST
                    add(JBLabel(labelText), gbc)

                    gbc.gridx = 1
                    gbc.weightx = 1.0
                    gbc.anchor = GridBagConstraints.WEST
                    add(field, gbc)
                }

                addRow(4, "Username:", usernameField)
                addRow(5, "Password:", passwordField)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(loginButton, BorderLayout.CENTER)
            })

            add(Box.createVerticalStrut(10))

            loginButton.addActionListener {
                onLoginSuccess(UserService.login(usernameField.text, passwordField.text))
            }
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

package org.delite.alt.ctrl.taskoplugin

import com.intellij.ui.components.JBBox
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.components.JBPasswordField
import com.intellij.ui.components.JBTextField

import java.awt.BorderLayout
import java.awt.Font
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.Insets
import javax.swing.Box
import javax.swing.JButton
import javax.swing.JComponent
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

                addRow(1, "Firstname:", firstNameField)
                addRow(2, "Lastname:", lastNameField)
                addRow(3, "Email:", emailField)
                addRow(4, "Username:", usernameField)
                addRow(5, "Password:", firstPasswordField)
                addRow(6, "Confirm password:", secondPasswordField)
            })

            add(JBPanel<JBPanel<*>>(BorderLayout()).apply {
                add(registerButton, BorderLayout.CENTER)
            })

            registerButton.addActionListener {
                /* TODO: Send register request */
            }
        })
    }

    fun getContent(): JBPanel<JBPanel<*>> = content
}

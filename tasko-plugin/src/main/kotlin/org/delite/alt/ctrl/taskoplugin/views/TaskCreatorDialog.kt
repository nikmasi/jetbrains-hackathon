import com.intellij.openapi.ui.DialogWrapper
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

class TaskCreatorDialog : DialogWrapper(true) {
    init {
        title = "Task Creator Dialog"
        init()
    }

    override fun createCenterPanel(): JComponent {
        val panel = JPanel()
        panel.add(JLabel("This is a popup window"))
        return panel
    }
}
package org.delite.alt.ctrl.taskoplugin.utilities

import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager
import com.intellij.psi.PsiFile
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.ui.Messages
import com.intellij.psi.PsiComment
import com.intellij.psi.util.PsiTreeUtil
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import org.delite.alt.ctrl.taskoplugin.services.Http
import org.delite.alt.ctrl.taskoplugin.services.TaskService
import org.delite.alt.ctrl.taskoplugin.services.TaskoLoginStateService
import org.jetbrains.kotlin.psi.KtFile
import kotlin.jvm.java

class CommentsToTasks {
    val apiURL = "http://localhost:8080"
    
    fun extractTodoList(file: PsiFile): List<String> {
        if (file !is KtFile) return emptyList()

        val comments = PsiTreeUtil.findChildrenOfType(file, PsiComment::class.java)
        return comments.mapNotNull { comment ->
            val text = comment.text
            val idx = text.indexOf("TODO", ignoreCase = true)
            if (idx != -1) {
                text.substring(idx + "TODO".length).trim()
            } else null
        }
    }

    fun textToTask(text: String): Pair<String, String> {
        var prompt: String = "I require you to generate two strings (title and body_text). The goal is for you to generate title of a task, and body_text of a task, of a todo task, a reminder for a programmer, and do taht based on the following text: \"${text}\". I want you to give me the output in following format: title=\"<generate_title_here>\" body_text=\"<generate_title_here>\""
        val response = Http.post("${apiURL}/chat", buildJsonObject {
            put("prompt", prompt)
        })

        fun extractData(s: String): Pair<String, String> {
            val parts = s.split("\"")
            val title = parts[1]
            val bodyText = parts[3]
            return title to bodyText
        }

        return extractData(response["response"]?.jsonPrimitive?.content ?: "title=\"ERROR\" body_text=\"ERROR\"")
    }

     fun getActiveKtFile(project: Project): PsiFile? {
        val editor: Editor? = FileEditorManager.getInstance(project).selectedTextEditor
        val document = editor?.document
        val psiFile: PsiFile? = document?.let {
            PsiDocumentManager.getInstance(project).getPsiFile(it)
        }
        return psiFile
    }

    fun convertTodoToTasks(idTaskList: Int, psiFile: PsiFile) {
        val strings = extractTodoList(psiFile)
        for (s in strings) {
            val p: Pair<String, String> = textToTask(s)
            TaskService.addTask(idTaskList, p.first, p.second)
        }
    }
}
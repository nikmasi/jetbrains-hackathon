package com.example

import com.example.data.ListTasks
import com.example.data.MessageResponse
import com.example.data.NewProject
import com.example.data.ProjectList
import com.example.data.Task
import com.example.data.TaskName
import com.example.data.TasksListList
import com.example.data.User
import com.example.data.UserRequest
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

val openAiService = OpenAiService(apiKey = "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")

fun Application.configureRouting() {
    val databaseService = DatabaseService(
        dbUrl = "jdbc:mysql://localhost:3306/tasko_database?useSSL=false&allowPublicKeyRetrieval=true",
        user = "root",
        password = "rootpassword"
    )
    val connection = databaseService.getDatabaseConnection() ?: error("Database connection failed — cannot start routing.")
    val repository: Repository = Repository(connection)
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        post("/signUp"){
            try {
                val conn = databaseService.getDatabaseConnection()
                val repo = conn?.let { Repository(it) }
                val korisnik = call.receive<User>()

                repo?.createUser(korisnik)
                call.respond(MessageResponse("TRUE"))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Failed to insert Korisnik"))
            }
        }

        post("/logIn"){
            try{
                val conn = databaseService.getDatabaseConnection()
                val repo = conn?.let { Repository(it) }
                println("logIn")
                val korisnik = call.receive<UserRequest>()
                val result = repo?.selectUser(korisnik.username,korisnik.hashed_password)
                if (result != null) {
                    println("User is logged in")
                    call.respond(MessageResponse("TRUE"))
                }
                else {
                    println("User is not logged in")
                    call.respond(MessageResponse("FALSE"))
                }
            }
            catch (e: Exception){
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Failed to log in Korisnik"))
            }
        }

        post("/createProject"){
            try {
                val conn = databaseService.getDatabaseConnection()
                val repo = conn?.let { Repository(it) }
                val pr = call.receive<NewProject>()

                repo?.createProject(pr)

                call.respond(HttpStatusCode.OK, MessageResponse("Inserted Project Successfully"))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Failed "))
            }
        }

        post("/createTask"){
            try {
                val conn = databaseService.getDatabaseConnection()
                val repo = conn?.let { Repository(it) }
                val pr = call.receive<Task>()

                repo?.createTask(pr)

                call.respond(HttpStatusCode.OK, MessageResponse("Inserted Project Successfully"))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Failed " + e.message))
            }
        }

        post("/selectProjectUser"){
            try {
                val conn = databaseService.getDatabaseConnection()
                val repo = conn?.let { Repository(it) }
                val pr = call.receive<UserRequest>()

                val result = repo?.selectProjectsOfUser(User("","",pr.username,"",""))

                println(ProjectList(result))
                call.respond(ProjectList(result))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Failed "))
            }
        }

        post("/selectAllTasksProject"){
            try {
                val conn = databaseService.getDatabaseConnection()
                val repo = conn?.let { Repository(it) }
                val pr = call.receive<NewProject>()

                val result = repo?.selectAllTaskListsOfProject(pr.project)
                println(result)
                call.respond(TasksListList(result))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Failed "))
            }
        }

        post("/selectAllTasksOfTaskLists"){
            try {
                val conn = databaseService.getDatabaseConnection()
                val repo = conn?.let { Repository(it) }
                val pr = call.receive<TaskName>()

                val result = repo?.selectAllTasksOfTaskLists(pr)
                println(result)
                call.respond(ListTasks(result))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Failed "))
            }
        }

        post("/selectAllTasksOfTaskLists2"){
            try {
                val conn = databaseService.getDatabaseConnection()
                val repo = conn?.let { Repository(it) }
                val pr = call.receive<TaskName>()
                print("ovdee")

                val result = repo?.selectAllTasksOfTaskLists2(pr)
                println(result)
                call.respond(ListTasks(result))
            } catch (e: Exception) {
                e.printStackTrace()
                call.respond(HttpStatusCode.BadRequest, MessageResponse("Failed "))
            }
        }

        post("/chat") {
            val request = call.receive<Map<String, String>>() // očekuje JSON {"prompt": "tvoj tekst"}
            val prompt = request["prompt"] ?: ""
            val response = openAiService.sendMessage(prompt)
            println(response)
            call.respond(mapOf("response" to response))
        }

    }
}

fun closeResources(conn: Connection?, statement: PreparedStatement?, resultSet: ResultSet?) {
    resultSet?.close()
    statement?.close()
    //conn?.close()
}
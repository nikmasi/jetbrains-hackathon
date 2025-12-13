package com.example

import com.asyncapi.kotlinasyncapi.context.service.AsyncApiExtension
import com.asyncapi.kotlinasyncapi.ktor.AsyncApiPlugin
import com.example.data.MessageResponse
import com.example.data.User
import com.example.data.UserRequest
import com.example.repository.Repository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

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

    }
}

fun closeResources(conn: Connection?, statement: PreparedStatement?, resultSet: ResultSet?) {
    resultSet?.close()
    statement?.close()
    //conn?.close()
}
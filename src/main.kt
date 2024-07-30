import db.*
import gui.*

import javafx.application.Application


val config = Config()
val database = DB(host = config.host,  port = config.port,
    username = config.username, password = config.password, database = config.database)
val connection = database.getConnection()

fun main(args: Array<String>) {

    val count = database.getCountQueryData(connection,  table = "users", query = "code=61")
    println(count)
    println(count)
    println(count)
    println(count)

    Application.launch(FXMLApplication::class.java, *args)
}






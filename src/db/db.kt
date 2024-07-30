package db

import java.sql.*

class DB(host: String, port: Int, private val username: String, private val password: String, database: String) {

    private val url: String = "jdbc:postgresql://$host:$port/$database"

    /** Метод создает соединение с базой данных и возвращает соединение connection */
    fun getConnection(): Connection {
        try {
            return DriverManager.getConnection(url, username, password)
        } catch (e: SQLException) {
            e.printStackTrace()
            throw e
        }
    }

    /** Метод получает все данные из таблицы и возвращает результат в виде ResultSet с сортирокой order */
    fun getAllData(connection: Connection, table: String, order: String): ResultSet? {
        try {
            return connection.createStatement().executeQuery("SELECT * FROM $table ORDER BY $order")
        } catch (e: SQLException) {
            println("Ошибка при получении данных в таблице $table: $e")
            return null
        }
    }

    /** Метод получает данные из таблицы по запросу и возвращает результат в виде ResultSet */
    fun getQueryData(connection: Connection, table: String, query: String, order: String): ResultSet? {
        try {
            return connection.createStatement().executeQuery("SELECT * FROM $table WHERE $query ORDER BY $order")
        } catch (e: SQLException) {
            println("Ошибка при получении данных в таблице $table: $e")
            return null
        }
    }

    /** Метод обновляет данные в таблице из data по id */
    fun updateDataByID(connection: Connection, table: String, data: Map<String, Any>, id: Int): Boolean {
        try {
            data.forEach { (key, value) ->
                if (value is String) {
                    connection.createStatement().executeUpdate("UPDATE $table SET $key = '$value' WHERE id = $id")
                } else {
                    connection.createStatement().executeUpdate("UPDATE $table SET $key = $value WHERE id = $id")
                }
            }
            return true
        } catch (e: SQLException) {
            println("Ошибка при обновлении данных в таблице $table: $e")
            return false
        }
    }

    /** Метод обновляет данные в таблице по запросу query из data */
    fun updateQueryData(connection: Connection, table: String, data: Map<String, Any>, query: String): Boolean {
        try {
            data.forEach { (key, value) ->
                if (value is String) {
                    connection.createStatement().executeUpdate("UPDATE $table SET $key = '$value' WHERE $query")
                } else {
                    connection.createStatement().executeUpdate("UPDATE $table SET $key = $value WHERE $query")
                }
            }
            return true
        } catch (e: SQLException) {
            println("Ошибка при обновлении данных в таблице $table: $e")
            return false
        }
    }

    /** Метод вставляет данные в таблицу из data */
    fun insertData(connection: Connection, table: String, data: Map<String, Any>): Boolean {
        val query = StringBuilder("INSERT INTO ").append(table).append(" (")
        val values = StringBuilder("VALUES (")

        data.forEach { (key, value) ->
            query.append(key).append(",")
            if (value is String) {
                values.append("'").append(value).append("'").append(",")

            } else {
                values.append(value).append(",")
            }
        }

        query.deleteCharAt(query.lastIndexOf(","))
        values.deleteCharAt(values.lastIndexOf(","))
        query.append(") ").append(values.append(")"))

        try {
            connection.createStatement()
                .executeUpdate(query.toString())
            return true
        } catch (e: SQLException) {
            println("Ошибка при записи данных в таблице $table: $e")
            return false
        }
    }

    /** Метод удаляет данные из таблицы по запросу */
    fun deleteData(connection: Connection, table: String, query: String): Boolean {
        try {
            connection.createStatement().executeUpdate("DELETE FROM $table WHERE $query")
            return true
        } catch (e: SQLException) {
            println("Ошибка при удалении данных в таблице $table: $e")
            return false
        }
    }

    /** Метод возвращает количество данных в resultSet */
    fun getCountData(result: ResultSet): Int {
        var count = 0
        while (result.next()) {
            count++
        }
        return count
    }

    /** Метод возвращает количество записей в таблице table по запросу query*/
    fun getCountQueryData(connection: Connection, table: String, query: String): Int {
        var count = 0
        try {
            val result = connection.createStatement().executeQuery("SELECT * FROM $table WHERE $query")
            while (result.next()) count++
            result.close()
            return count
        }
        catch (e: SQLException) {
            println("Ошибка при получении данных в таблице $table: $e")
            return -1
        }
    }

    /** Метод возвращает количество всех записей в таблице table */
    fun getAllCountData(connection: Connection, table: String): Int {
        var count = 0
        try {
            val result = connection.createStatement().executeQuery("SELECT * FROM $table")
            while (result.next()) count++
            result.close()
            return count
        }
        catch (e: SQLException) {
            println("Ошибка при получении данных в таблице $table: $e")
            return 0
        }
    }


    /** Метод закрывает соединение с базой данных */
    fun closeConnection(connection: Connection) {
        try {
            connection.close()
        } catch (e: SQLException) {
            e.printStackTrace()
            throw e
        }
    }
}
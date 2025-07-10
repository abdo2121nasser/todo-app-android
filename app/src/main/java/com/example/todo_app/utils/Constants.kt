package com.example.todo_app.utils

object Constants {

    object RoomDB {
        const val AUTH_ENTITY = "authentication entity"
        const val TODO_DB = "authentication entity"


    }
    object Api {
        const val BASE_URL = "https://todo.iraqsapp.com"

        object Endpoints {
            const val SIGN_UP = "/auth/register"
            const val SIGN_IN = "/auth/login"
            // Add other endpoints here
        }
    }
    object UiStrings {
        const val ALL = "All"
        const val IN_PROGRESS = "Inpogress"
        const val WAITING = "Waiting"
        const val FINISH = "Finished"


    }

}
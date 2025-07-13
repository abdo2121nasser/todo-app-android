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
            const val TODO = "/todos?"
        }

        object Queries {
            const val PAGE = "page"
        }

        object Headers {
            const val AUTH = "Authorization"
            const val BEAR_TOKEN = "Bearer "
        }
    }

    object UiStrings {
        const val ALL = "All"
        const val IN_PROGRESS = "Inpogress"
        const val WAITING = "Waiting"
        const val FINISH = "Finished"


    }
  object NavigationExtras {
        const val AUTH = "auth model extra"



    }

}
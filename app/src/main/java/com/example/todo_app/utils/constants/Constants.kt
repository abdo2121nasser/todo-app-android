package com.example.todo_app.utils.constants

object Constants {

    object RoomDB {
        const val AUTH_ENTITY = "authentication entity"
        const val TODO_DB = "data base"

        const val   TODO_ENTITY = "todo item entity"

    }

    object Api {
        const val BASE_URL = "https://todo.iraqsapp.com"

        object Endpoints {
            const val SIGN_UP = "/auth/register"
            const val SIGN_IN = "/auth/login"
            const val REFRESH = "/auth/refresh-token"
            const val TODO = "/todos?"
            const val CREATE_TODO_ITEM = "/todos"
            const val PROFILE = "/auth/profile"
            const val UPDATE_TODO_ITEM = "/todos/{id}"
            const val DELETE_TODO_ITEM = "/todos/{id}"
        }

        object Queries {
            const val PAGE = "page"
            const val TOKEN = "token"
        }

        object Headers {
            const val AUTH = "Authorization"
            const val BEAR_TOKEN = "Bearer "
        }
    }

    object UiStrings {
         private const val ALL = "All"
         const val IN_PROGRESS = "Inprogress"
         const val WAITING = "Waiting"
         const val FINISH = "Finished"
         val categories=listOf(ALL, IN_PROGRESS, WAITING, FINISH)
        val statuses=listOf( IN_PROGRESS, WAITING, FINISH)
         const val LOW = "low"
         const val MEDIUM = "medium"
         const val HIGH = "high"
        val priorities=listOf(LOW,MEDIUM,HIGH)



    }
  object NavigationExtras {
        const val AUTH = "auth model extra"
        const val UPDATE_TODO_ENTITY = "update todo entity"
        const val DETAILED_TODO_ENTITY = "detailed todo entity"



    }

}
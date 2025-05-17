package andorid.example.myapplication.network

import andorid.example.myapplication.data.Todo
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>

    @GET("todos/{id}")
    suspend fun getTodoById(@Path("id") id: Int): Todo
} 
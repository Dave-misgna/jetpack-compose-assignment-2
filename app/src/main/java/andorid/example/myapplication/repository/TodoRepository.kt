package andorid.example.myapplication.repository

import andorid.example.myapplication.data.Todo
import andorid.example.myapplication.data.TodoDao
import andorid.example.myapplication.network.TodoApiService
import kotlinx.coroutines.flow.Flow

class TodoRepository(
    private val todoDao: TodoDao,
    private val apiService: TodoApiService
) {
    val todos: Flow<List<Todo>> = todoDao.getAllTodos()

    suspend fun refreshTodos() {
        try {
            val todos = apiService.getTodos()
            todoDao.deleteAll()
            todoDao.insertAll(todos)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getTodoById(id: Int): Todo? {
        return todoDao.getTodoById(id)
    }
} 
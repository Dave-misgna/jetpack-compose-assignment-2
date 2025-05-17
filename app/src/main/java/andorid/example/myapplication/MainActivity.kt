package andorid.example.myapplication

import andorid.example.myapplication.data.TodoDatabase
import andorid.example.myapplication.network.TodoApiService
import andorid.example.myapplication.repository.TodoRepository
import andorid.example.myapplication.ui.TodoApp
import andorid.example.myapplication.ui.theme.MyApplicationTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = TodoDatabase.getDatabase(applicationContext)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(TodoApiService::class.java)
        val repository = TodoRepository(database.todoDao(), apiService)

        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TodoApp(repository)
                }
            }
        }
    }
}




package andorid.example.myapplication.ui

import andorid.example.myapplication.repository.TodoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TodoViewModelFactory(private val repository: TodoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 
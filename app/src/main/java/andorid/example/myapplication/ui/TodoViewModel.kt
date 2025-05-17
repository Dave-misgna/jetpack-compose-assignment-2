package andorid.example.myapplication.ui

import andorid.example.myapplication.data.Todo
import andorid.example.myapplication.repository.TodoRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {
    private val _listUiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val listUiState: StateFlow<TodoUiState> = _listUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val detailUiState: StateFlow<TodoUiState> = _detailUiState.asStateFlow()

    init {
        loadTodos()
    }

    private fun loadTodos() {
        viewModelScope.launch {
            repository.todos.collect { todos ->
                _listUiState.value = TodoUiState.Success(todos)
            }
        }
        refreshTodos()
    }

    private fun refreshTodos() {
        viewModelScope.launch {
            repository.refreshTodos()
        }
    }

    fun getTodoById(id: Int) {
        viewModelScope.launch {
            _detailUiState.value = TodoUiState.Loading
            val todo = repository.getTodoById(id)
            _detailUiState.value = TodoUiState.Success(listOf(todo!!))
        }
    }
}

sealed class TodoUiState {
    data object Loading : TodoUiState()
    data class Success(val todos: List<Todo>) : TodoUiState()
    data class Error(val message: String) : TodoUiState()
} 
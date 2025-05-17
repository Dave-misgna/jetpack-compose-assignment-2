package andorid.example.myapplication.ui

import andorid.example.myapplication.data.Todo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TodoListScreen(
    viewModel: TodoViewModel,
    onTodoClick: (Int) -> Unit
) {
    val uiState by viewModel.listUiState.collectAsState()

    when (uiState) {
        is TodoUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is TodoUiState.Success -> {
            val todos = (uiState as TodoUiState.Success).todos
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(todos) { todo ->
                    TodoItem(
                        todo = todo,
                        onClick = { onTodoClick(todo.id) }
                    )
                }
            }
        }
        is TodoUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = (uiState as TodoUiState.Error).message)
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = "User ID: ${todo.userId}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Checkbox(
                checked = todo.completed,
                onCheckedChange = null
            )
        }
    }
} 
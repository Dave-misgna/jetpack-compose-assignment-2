package andorid.example.myapplication.ui

import andorid.example.myapplication.data.Todo
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    viewModel: TodoViewModel,
    todoId: Int,
    onBackClick: () -> Unit
) {
    val uiState by viewModel.detailUiState.collectAsState()

    LaunchedEffect(todoId) {
        viewModel.getTodoById(todoId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Todo Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (uiState) {
                is TodoUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                is TodoUiState.Success -> {
                    val todo = (uiState as TodoUiState.Success).todos.firstOrNull()
                    todo?.let {
                        TodoDetailContent(todo = it)
                    }
                }
                is TodoUiState.Error -> {
                    Text(
                        text = (uiState as TodoUiState.Error).message,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun TodoDetailContent(todo: Todo) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "To do number: ${todo.id}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "User ID: ${todo.userId}",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Title:",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Completed:",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Checkbox(
                        checked = todo.completed,
                        onCheckedChange = null
                    )
                }
            }
        }
    }
} 
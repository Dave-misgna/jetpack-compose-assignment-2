package andorid.example.myapplication.ui

import andorid.example.myapplication.repository.TodoRepository
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun TodoApp(repository: TodoRepository) {
    val navController = rememberNavController()
    val viewModel: TodoViewModel = viewModel(
        factory = TodoViewModelFactory(repository)
    )

    NavHost(navController = navController, startDestination = "todoList") {
        composable("todoList") {
            TodoListScreen(
                viewModel = viewModel,
                onTodoClick = { todoId ->
                    navController.navigate("todoDetail/$todoId")
                }
            )
        }
        composable(
            route = "todoDetail/{todoId}",
            arguments = listOf(
                navArgument("todoId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: return@composable
            TodoDetailScreen(
                viewModel = viewModel,
                todoId = todoId,
                onBackClick = {
                    navController.navigate("todoList")
                }
            )
        }
    }
}
package com.gzaber.remindme

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.gzaber.remindme.ui.addedit.AddEditScreen
import com.gzaber.remindme.ui.reminders.RemindersScreen
import kotlinx.serialization.Serializable

@Serializable
object Reminders

@Serializable
data class AddEdit(val id: Int? = null) {
    companion object {
        const val ID_ARG = "id"
    }
}

@Composable
fun RemindMeNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Reminders,
        modifier = modifier
    ) {
        composable<Reminders> {
            RemindersScreen(
                onNavigateToAddEdit = { id ->
                    navController.navigate(route = AddEdit(id = id))
                }
            )
        }
        composable<AddEdit> { backStackEntry ->
            val id = backStackEntry.toRoute<AddEdit>().id
            val title =
                if (id == null) R.string.add_edit_app_bar_create_title else R.string.add_edit_app_bar_update_title
            AddEditScreen(
                title = title,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
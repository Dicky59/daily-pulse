package com.dicky59.dailypulse.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.dicky59.dailypulse.articles.ui.ArticleScreen
import com.dicky59.dailypulse.sources.ui.SourceScreen

@Composable
fun MainNavigation() {
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  val currentDestination = navBackStackEntry?.destination

  Scaffold(
    bottomBar = {
      NavigationBar {
        NavigationBarItem(
          icon = {
            Icon(
              Icons.Default.Newspaper,
              contentDescription = "Articles",
            )
          },
          label = { Text("Articles") },
          selected =
            currentDestination?.hierarchy?.any {
              it.route == NavigationRoute.Articles.route
            } == true,
          onClick = {
            navController.navigate(
              NavigationRoute.Articles.route,
            ) {
              popUpTo(
                navController.graph.findStartDestination().id,
              ) { saveState = true }
              launchSingleTop = true
              restoreState = true
            }
          },
        )
        NavigationBarItem(
          icon = {
            Icon(
              Icons.Default.Menu,
              contentDescription = "Sources",
            )
          },
          label = { Text("Sources") },
          selected =
            currentDestination?.hierarchy?.any {
              it.route == NavigationRoute.Sources.route
            } == true,
          onClick = {
            navController.navigate(
              NavigationRoute.Sources.route,
            ) {
              popUpTo(
                navController.graph.findStartDestination().id,
              ) { saveState = true }
              launchSingleTop = true
              restoreState = true
            }
          },
        )
      }
    },
  ) { _ ->
    NavHost(
      navController = navController,
      startDestination = NavigationRoute.Articles.route,
      modifier = Modifier.fillMaxSize(),
    ) {
      composable(NavigationRoute.Articles.route) { ArticleScreen() }
      composable(NavigationRoute.Sources.route) { SourceScreen() }
    }
  }
}

package com.dicky59.dailypulse.core.navigation

sealed class NavigationRoute(
  val route: String,
) {
  object Articles : NavigationRoute("articles")

  object Sources : NavigationRoute("sources")
}

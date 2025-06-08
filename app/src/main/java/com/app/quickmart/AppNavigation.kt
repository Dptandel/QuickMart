package com.app.quickmart

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.quickmart.pages.CategoryProductsPage
import com.app.quickmart.components.ProductDetailsPage
import com.app.quickmart.pages.CheckoutPage
import com.app.quickmart.screens.AuthScreen
import com.app.quickmart.screens.HomeScreen
import com.app.quickmart.screens.LoginScreen
import com.app.quickmart.screens.SignUpScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth


@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    GlobalNavigation.navController = navController

    val isLoggedIn = Firebase.auth.currentUser != null
    val firstPage = if(isLoggedIn) "home" else "auth"

    NavHost(navController = navController, startDestination = firstPage) {
        composable("auth") {
            AuthScreen(modifier, navController)
        }

        composable("login") {
            LoginScreen(modifier, navController)
        }

        composable("signup") {
            SignUpScreen(modifier, navController)
        }

        composable("home") {
            HomeScreen(modifier, navController)
        }

        composable("category-products/{categoryId}") {
            val categoryId = it.arguments?.getString("categoryId")
            CategoryProductsPage(modifier, categoryId?:"")
        }

        composable("product-details/{productId}") {
            val productId = it.arguments?.getString("productId")
            ProductDetailsPage(modifier, productId?:"")
        }

        composable("checkout") {
            CheckoutPage(modifier)
        }
    }
}

object GlobalNavigation {
    @SuppressLint("StaticFieldLeak")
    lateinit var navController: NavHostController
}
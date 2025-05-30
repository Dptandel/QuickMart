package com.app.quickmart.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quickmart.GlobalNavigation
import com.app.quickmart.components.CartItemView
import com.app.quickmart.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CartPage(modifier: Modifier = Modifier) {

    val userModel = remember {
        mutableStateOf(UserModel())
    }

    DisposableEffect(key1 = Unit) {
        val listener = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener { it, _ ->
                if (it != null) {
                    val result = it.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result
                    }
                }
            }

        onDispose {
            listener.remove()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Your Cart",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 16.dp)
                .weight(1f)
        ) {
            items(userModel.value.cartItems.toList(), key = { it.first }) { (productId, qty) ->
                CartItemView(productId = productId, qty = qty)
            }
        }

        Button(
            onClick = {
                GlobalNavigation.navController.navigate("checkout")
            }, modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Checkout", fontSize = 18.sp)
        }
    }
}
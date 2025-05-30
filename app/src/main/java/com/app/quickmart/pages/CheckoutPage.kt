package com.app.quickmart.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.quickmart.AppUtils
import com.app.quickmart.models.ProductModel
import com.app.quickmart.models.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CheckoutPage(modifier: Modifier = Modifier) {

    val userModel = remember {
        mutableStateOf(UserModel())
    }

    val productList = remember {
        mutableStateListOf<ProductModel>()
    }

    val subTotal = remember {
        mutableFloatStateOf(0f)
    }

    val discount = remember {
        mutableFloatStateOf(0f)
    }

    val tax = remember {
        mutableFloatStateOf(0f)
    }

    val total = remember {
        mutableFloatStateOf(0f)
    }

    fun calculateAndAssign() {
        var calculatedSubTotal = 0f
        productList.forEach {
            if (it.price.isNotEmpty()) {
                val price = it.price.toFloatOrNull() ?: 0f
                val qty = userModel.value.cartItems[it.id] ?: 0
                calculatedSubTotal += price * qty
            }
        }
        subTotal.floatValue = calculatedSubTotal
        discount.floatValue = calculatedSubTotal * AppUtils.getDiscountPercentage() / 100
        tax.floatValue = calculatedSubTotal * AppUtils.getTaxPercentage() / 100
        total.floatValue = subTotal.floatValue - discount.floatValue + tax.floatValue
    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result

                        Firebase.firestore.collection("data")
                            .document("stocks")
                            .collection("products")
                            .whereIn("id", userModel.value.cartItems.keys.toList())
                            .get().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val resultProducts =
                                        task.result.toObjects(ProductModel::class.java)
                                    productList.addAll(resultProducts)
                                    calculateAndAssign()
                                }
                            }
                    }
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Checkout", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Deliver to: ", fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = userModel.value.address, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))
        RowCheckoutItems(title = "Sub Total", value = subTotal.floatValue)
        Spacer(modifier = Modifier.height(8.dp))
        RowCheckoutItems(title = "Discount (-)", value = discount.floatValue)
        Spacer(modifier = Modifier.height(8.dp))
        RowCheckoutItems(title = "Tax (+)", value = tax.floatValue)
        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider()

        Spacer(modifier = Modifier.height(16.dp))
        Text(modifier = Modifier.fillMaxWidth(), text = "Total", textAlign = TextAlign.Center)
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "₹ " + total.floatValue,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                AppUtils.startPayment(total.floatValue)
            }, modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Pay Now", fontSize = 18.sp)
        }
    }
}

@Composable
fun RowCheckoutItems(title: String, value: Float) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "₹ " + value, fontSize = 18.sp)
    }
}
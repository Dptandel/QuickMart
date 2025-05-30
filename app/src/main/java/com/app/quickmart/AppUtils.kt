package com.app.quickmart

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.app.quickmart.pages.CheckoutPage
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import com.razorpay.Checkout
import org.json.JSONObject

object AppUtils {
    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun addToCart(context: Context, productId: String) {
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity + 1

                val updatedCart = mapOf("cartItems.$productId" to updatedQuantity)

                userDoc.update(updatedCart).addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        showToast(context, "Item added to cart")
                    } else {
                        showToast(context, "Failed to add item to cart")
                    }
                }
            }
        }
    }

    fun removeFromCart(context: Context, productId: String, removeAll: Boolean = false) {
        val userDoc = Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!)

        userDoc.get().addOnCompleteListener {
            if (it.isSuccessful) {
                val currentCart = it.result.get("cartItems") as? Map<String, Long> ?: emptyMap()
                val currentQuantity = currentCart[productId] ?: 0
                val updatedQuantity = currentQuantity - 1

                val updatedCart =
                    if (updatedQuantity <= 0 || removeAll) {
                        mapOf("cartItems.$productId" to FieldValue.delete())
                    } else {
                        mapOf("cartItems.$productId" to updatedQuantity)
                    }

                userDoc.update(updatedCart).addOnCompleteListener { updateTask ->
                    if (updateTask.isSuccessful) {
                        showToast(context, "Item remove from cart")
                    } else {
                        showToast(context, "Failed to remove item from cart")
                    }
                }
            }
        }
    }

    fun getDiscountPercentage(): Float {
        return 10.0f
    }

    fun getTaxPercentage(): Float {
        return 13.0f
    }

    fun razorpayApiKey(): String {
        return "rzp_test_iOC0BErwspg0EC"
    }

    fun startPayment(amount: Float) {
        val checkout = Checkout()
        checkout.setKeyID(razorpayApiKey())

        val options = JSONObject()
        options.put("name", "QuickMart")
        options.put("description", "Order Payment from QuickMart")
        options.put("amount", amount * 100)
        options.put("currency", "INR")

        checkout.open(GlobalNavigation.navController.context as Activity, options)
    }
}
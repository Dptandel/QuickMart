package com.app.quickmart.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.quickmart.components.ProductItemView
import com.app.quickmart.models.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun CategoryProductsPage(modifier: Modifier = Modifier, categoryId: String) {

    val productsList = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }

    LaunchedEffect(key1 = Unit) {
        Firebase.firestore.collection("data")
            .document("stocks")
            .collection("products")
            .whereEqualTo("category", categoryId)
            .get().addOnCompleteListener {
                if (it.isSuccessful) {
                    val resultList = it.result.documents.mapNotNull { document ->
                        document.toObject(ProductModel::class.java)
                    }
                    productsList.value = resultList.plus(resultList).plus(resultList).plus(resultList).plus(resultList)
                }
            }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        items(productsList.value.chunked(2)) { rowItems ->
            Row {
                rowItems.forEach {
                    ProductItemView(product = it, modifier = Modifier.weight(1f))
                }
                if(rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}
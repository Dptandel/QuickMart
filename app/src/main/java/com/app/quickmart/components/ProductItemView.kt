package com.app.quickmart.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.app.quickmart.models.ProductModel

@Composable
fun ProductItemView(modifier: Modifier = Modifier, product: ProductModel) {
    Card(
        modifier = modifier
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.name,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "₹ " + product.mrp,
                    fontSize = 14.sp,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "₹ " + product.price,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Button(
                onClick = {},
                modifier = Modifier.height(50.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Add to Cart", style = TextStyle(fontSize = 14.sp))
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    imageVector = Icons.Default.ShoppingCart,
                    modifier = Modifier.size(20.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(Color.White),
                    contentDescription = "Cart"
                )
            }
        }
    }
}
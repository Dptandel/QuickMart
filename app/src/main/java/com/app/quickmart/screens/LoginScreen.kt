package com.app.quickmart.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.app.quickmart.AppUtils
import com.app.quickmart.R
import com.app.quickmart.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.login),
            contentDescription = "Login",
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Login", modifier = Modifier.fillMaxWidth(), style = TextStyle(
                fontSize = 45.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Continue your shopping journey with us.",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = FontFamily.Monospace,
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text(text = "Email")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            )
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text(text = "Password")
            },
            modifier = Modifier.fillMaxWidth(),
            shape = CircleShape,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text, imeAction = ImeAction.Next
            ),
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                isLoading = true
                authViewModel.login(email, password) { success, errorMessage ->
                    if (success) {
                        isLoading = false
                        navController.navigate("home") {
                            popUpTo("auth") { inclusive = true }
                        }
                    } else {
                        isLoading = false
                        AppUtils.showToast(context, errorMessage ?: "Something went wrong!!!")
                    }
                }
            }, enabled = !isLoading, modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                text = if (isLoading) "Logging In" else "Login", style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.SemiBold
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Or Continue with",
            modifier = Modifier.fillMaxWidth(),
            style = TextStyle(
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TextButton(
                onClick = {},
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f),
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google),
                    contentDescription = "Google",
                    modifier = Modifier.fillMaxHeight()
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Google", style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            TextButton(
                onClick = {},
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f),
                shape = CircleShape,
                border = BorderStroke(width = 1.dp, color = Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.facebook),
                    contentDescription = "Facebook",
                    modifier = Modifier.fillMaxHeight()
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Facebook", style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
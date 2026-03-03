package com.example.smartboard.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.smartboard.R
import com.example.smartboard.login.modelviewmodel.AuthState
import com.example.smartboard.login.modelviewmodel.AuthViewModel
import com.example.smartboard.navigation.Screen
import com.example.smartboard.ui.theme.ElectricBlue
import com.example.smartboard.ui.theme.SmartBoardTheme
import com.example.smartboard.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    navController: NavHostController
) {
    var showSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to SmartBoard",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = ElectricBlue
            )
        }

        Button(
            onClick = { showSheet = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
                .height(64.dp)
                .width(140.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = ElectricBlue)
        ) {
            Text(text = "Start", fontSize = 20.sp, color = White)
        }

        if (showSheet) {
            ModalBottomSheet(
                onDismissRequest = { showSheet = false },
                sheetState = sheetState,
                containerColor = White
            ) {
                LoginBottomSheetContent(
                    onForgotPasswordClick = {
                        showSheet = false
                        onForgotPasswordClick()
                    },
                    onCreateAccountClick = {
                        showSheet = false
                        onCreateAccountClick()
                    },
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun LoginBottomSheetContent(
    onForgotPasswordClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    viewModel: AuthViewModel = viewModel(),
    navController: NavHostController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isFormValid = email.isNotEmpty() && password.isNotEmpty()
    var passwordvisibility by remember { mutableStateOf(false) }

    //Navigation to main screen
    val authState by viewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            navController.navigate(Screen.Main.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (passwordvisibility) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordvisibility = !passwordvisibility }) {
                    if (passwordvisibility) Icon(painter = painterResource(R.drawable.visibility_24px), contentDescription = "hide password")
                    else Icon(painter = painterResource(R.drawable.visibility_off_24px), contentDescription = "show password")
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = onForgotPasswordClick) {
                Text("Forgot Password?", color = Color.Gray)
            }
            TextButton(onClick = onCreateAccountClick) {
                Text("Create Account", color = ElectricBlue)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.login(email,password) },
            enabled = isFormValid,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = ElectricBlue,
                disabledContainerColor = Color.LightGray
            )
        ) {
            Text(
                text = "Login",
                fontSize = 16.sp,
                color = if (isFormValid) White else Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (authState) {
            is AuthState.Loading -> {
                CircularProgressIndicator()
            }
            is AuthState.Success -> {
                Text(
                    text = (authState as AuthState.Success).message,
                    color = Color.Green
                )
            }
            is AuthState.Error -> {
                Text(
                    text = (authState as AuthState.Error).error,
                    color = Color.Red
                )
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "OR", color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = { /* TODO */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Logo",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text("Login with Google", color = Color.Black)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    SmartBoardTheme {
        LoginScreen(onForgotPasswordClick = {}, onCreateAccountClick = {} , navController = navController)
    }
}

@Preview(showBackground = true)
@Composable
fun LoginBottomSheetPreview() {
    val navController = rememberNavController()
    SmartBoardTheme {
        LoginBottomSheetContent(onForgotPasswordClick = {}, onCreateAccountClick = {}, navController = navController)
    }
}

package com.example.smartboard.login

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import com.example.smartboard.R
import com.example.smartboard.ui.theme.CardActive
import com.example.smartboard.ui.theme.CardInactive
import com.example.smartboard.ui.theme.DarkGray
import com.example.smartboard.ui.theme.ElectricBlue
import com.example.smartboard.ui.theme.SmartBoardTheme
import com.example.smartboard.ui.theme.White
import kotlin.io.encoding.Base64

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(onBackClick: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var boardnumber by remember { mutableStateOf("") }
    var help by remember { mutableStateOf(false) }
    var passwordvisibility by remember { mutableStateOf(false) }

    val isFormValid = name.isNotEmpty() && email.isNotEmpty() && 
                     password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                     password == confirmPassword

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create Account") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Join SmartBoard",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Enter your details to create a new account.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = boardnumber,
                onValueChange = { boardnumber = it },
                label = { Text("Board Number") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {IconButton(onClick = {help = !help}) {Icon(painter = painterResource(R.drawable.help_24px), contentDescription = "where to find code") }}
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Full Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

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
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* TODO: Implement signup logic */ },
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
                    text = "Sign Up",
                    fontSize = 16.sp,
                    color = if (isFormValid) White else Color.Gray
                )
            }
        }
    }
    if (help) {
        AlertDialog(
            onDismissRequest = { help = false },
            title = { Text("Help") },
            confirmButton = {},
            //modifier = TODO(),
            dismissButton = {},
            //icon = TODO(),
            text = {Text("The required code is on card present in the box.")},
            shape = RoundedCornerShape(16.dp),
            containerColor = CardInactive,
            titleContentColor = ElectricBlue,
            textContentColor = DarkGray,
            tonalElevation = 6.dp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CreateAccountScreenPreview() {
    SmartBoardTheme {
        CreateAccountScreen(onBackClick = {})
    }
}

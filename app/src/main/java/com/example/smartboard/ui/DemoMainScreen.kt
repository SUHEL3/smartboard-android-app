package com.example.smartboard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen1()
        }
    }
}

@Composable
fun MainScreen1() {
    val backgroundColor = Color(0xFF6EC6FF)

    Scaffold(
        containerColor = backgroundColor,
        bottomBar = { BottomNavBar() }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TopBar()

            Spacer(modifier = Modifier.height(24.dp))

            DeviceGrid()
        }
    }
}

@Composable
fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = "Back",
            tint = Color.White
        )
        Icon(
            imageVector = Icons.Default.Home,
            contentDescription = "Home",
            tint = Color.White
        )
    }
}

@Composable
fun DeviceGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DeviceCard(Icons.Default.Home, "Light outlet", true)
            DeviceCard(Icons.Default.Add, "Fan", false)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DeviceCard(Icons.Default.Settings, "Motor", false)
            DeviceCard(Icons.Default.Add, "Socket", false)
        }
    }
}

@Composable
fun DeviceCard(
    icon: ImageVector,
    label: String,
    active: Boolean
) {
    val cardColor = if (active) Color.White else Color(0xFFB3E5FC)

    Card(
        modifier = Modifier.size(150.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF2196F3),
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun BottomNavBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = { Icon(Icons.Default.Home, "Home") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {},
            icon = { Icon(Icons.Default.Settings, "Settings") }
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun MainScreenPreview1() {
    MainScreen1()
}


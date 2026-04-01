package com.example.smartboard.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartboard.HistoryScreen
import com.example.smartboard.R
import com.example.smartboard.login.modelviewmodel.AuthViewModel
import com.example.smartboard.mainscreen.mainscreenviewmodel.BoardViewModel
import com.example.smartboard.ui.theme.*

data class DashboardItem(val title: String, val icon: Int ,val dbKey: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
   viewModel: AuthViewModel = viewModel(),
   viewModel1: BoardViewModel = viewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var mDisplayMenu by remember { mutableStateOf(false) }
    val tabs = listOf("Home", "Turn off", "History")
    val icons = listOf(painterResource(R.drawable.home_24px),painterResource(R.drawable.power_settings_new_24px), painterResource(R.drawable.history_24px))
    var bDisplaymenu by remember {mutableStateOf(false)}
    val boardList by viewModel1.boardList.collectAsState()
    val historyList by viewModel1.historyList.collectAsState()

    LaunchedEffect(Unit) {
        viewModel1.getBoard("suhelmujawar3269@gmail.com")
    }
    LaunchedEffect(selectedTab) {
        if(selectedTab  == 2) {
            viewModel1.FetchHistory()
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (selectedTab == 1) "Force Stop" else "SmartBoard",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 16.dp),
                        color = White,
                    )
                },
                navigationIcon = {
//                    IconButton(onClick = {}) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = White)
//                    }
                },
                actions = {
                    //board selection menu
                    Box {
                        IconButton(onClick = { bDisplaymenu = true }) {
                            Icon(painter = painterResource(R.drawable.arrow_drop_down_circle_24px), contentDescription = "Board Selection Menu", tint = White)
                        }
                        DropdownMenu(
                            expanded = bDisplaymenu,
                            onDismissRequest = { bDisplaymenu = false },
                            modifier = Modifier.background(White, shape = RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            containerColor = White
                        ) {
                            //fetch board list from firebase and display each using foreachIndex method
                            boardList.forEachIndexed { index, boardItem ->
                                DropdownMenuItem(
                                    text = {
                                        Column {
                                            Text(text = "${boardItem.key}:", fontWeight = FontWeight.Bold)
                                            Text(text = boardItem.value)
                                        }
                                    },
                                    onClick = {
                                        bDisplaymenu = false
                                        // You can handle click on the item here
                                    }
                                )
                            }
                        }
                    }
                    //navigation menu
                    Box {
                        IconButton(onClick = { mDisplayMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = White)
                        }
                        DropdownMenu(
                            expanded = mDisplayMenu,
                            onDismissRequest = { mDisplayMenu = false },
                            modifier = Modifier.background(White, shape = RoundedCornerShape(16.dp)),
                            shape = RoundedCornerShape(16.dp),
                            containerColor = White
                        ) {
                            DropdownMenuItem(
                                text = { Text("Profile", color = IconBlue) },
                                onClick = { mDisplayMenu = false },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = IconBlue) }
                            )
                            DropdownMenuItem(
                                text = { Text("Settings", color = IconBlue) },
                                onClick = { mDisplayMenu = false },
                                leadingIcon = { Icon(Icons.Default.Settings, contentDescription = null, tint = IconBlue) }
                            )
                            Divider(color = LightGray.copy(alpha = 0.5f))
                            DropdownMenuItem(
                                text = { Text("Logout", color = Color.Red) },
                                onClick = { mDisplayMenu = false
                                            // Handle logout action here
                                                viewModel.logout()
                                          },
                                leadingIcon = { Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.Red) }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ScreenBlue
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = White,
                contentColor = IconBlue
            ) {
                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        label = { Text(title) },
                        icon = { Icon(icons[index], contentDescription = title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = IconBlue,
                            selectedTextColor = IconBlue,
                            unselectedIconColor = MaterialTheme.colorScheme.outline,
                            unselectedTextColor = MaterialTheme.colorScheme.outline,
                            indicatorColor = White
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(ScreenBlue)
                .padding(paddingValues)
        ) {
            if (selectedTab == 0) {
                DashboardGrid(viewModel = viewModel1)
            } else if (selectedTab == 1) {
                // Placeholder for Turn off / Chat as requested before
               AlertDialog(onDismissRequest = {selectedTab = 0}) {
                   Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
Column(horizontalAlignment = Alignment.CenterHorizontally) {
    Text("Turn Off All Devices", color = White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
    Text(
        text = "This will immediately turn off all connected devices.",
        fontSize = 14.sp,
        color = Color.LightGray
    )
Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {
            //later when viewmodel is commented out apply this function
            viewModel1.forceTurnOFF()
            selectedTab = 0
                  },
        colors = ButtonDefaults.buttonColors(Color.Red)
    ){
        Text("Turn off", color = White)
        Icon(painter = painterResource(R.drawable.power_settings_new_24px), contentDescription ="Power off")
    }
    Spacer(modifier = Modifier.height(8.dp))
    TextButton(onClick = { selectedTab = 0 }) {
        Text("Cancel", color = Color.Gray)
    }
}

                   }
               }
            } else {
                Box(modifier = Modifier.fillMaxSize()) {
                    HistoryScreen(historyList)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardGrid(
    viewModel: BoardViewModel
) {

    LaunchedEffect(true) {
        viewModel.listenToDeviceStates()
    }

    val items = listOf(
        DashboardItem("Light", R.drawable.lightbulb_24px,"appliance1"),
        DashboardItem("Fan", R.drawable.toys_fan_24px,"appliance2"),
        DashboardItem("Fan 2", R.drawable.toys_fan_24px,"appliance4"),
        DashboardItem("Outlet", R.drawable.smart_outlet_24px,"appliance3")
    )
    var timerAlertDialog by remember { mutableStateOf(false) }

    var selectedTimer by remember { mutableStateOf("") }

    //var activeItems by remember { mutableStateOf(setOf<String>()) }
    val selectedBoard by remember { mutableStateOf("Hall") }

    val deviceStates by viewModel.deviceStates.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            itemsIndexed(items) { index, item ->
                val isActive = deviceStates[item.dbKey] ?: false
                DashboardCard(
                    item = item,
                    isActive = isActive,
                    onClick = {
//                        val newState = !isActive
//                        activeItems =
//                            if (newState)
//                                activeItems + item.dbKey
//                            else
//                                activeItems - item.dbKey

                        //  Write to Firebase
                        viewModel.updateDeviceState(item.dbKey, !isActive)
                    },
                    onTimerClick = {
                        // Handle timer click
                        selectedTimer = item.dbKey
                        timerAlertDialog = !timerAlertDialog
                    }
                )
            }
       }


        Button(
            onClick = {}, 
            colors = ButtonDefaults.buttonColors(White), 
            modifier = Modifier
                .width(300.dp)
                .height(60.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), 
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "", tint = ElectricBlue)
                }
                Text(text = "${selectedBoard}", color = ElectricBlue, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = {}) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "", tint = ElectricBlue)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(3) { index ->
                Surface(
                    shape = CircleShape,
                    color = if (index == 1) White else White.copy(alpha = 0.5f),
                    modifier = Modifier.size(8.dp)
                ) {}
            }
        }
    }
    var isTimerOn by remember { mutableStateOf(false) }
    val timeState = rememberTimePickerState(
        initialHour = 12,
        initialMinute = 0,
        is24Hour = false
    )
    if(timerAlertDialog) {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))//diming effect
        ){
        BasicAlertDialog(onDismissRequest = { timerAlertDialog = false }) {
            Column(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1E2A38) // dark bluish background
                    ),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        Column {
                            Text(
                                text = "Timer",
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Enable scheduling for devices",
                                fontSize = 14.sp,
                                color = Color.LightGray
                            )
                        }

                        Switch(
                            checked = isTimerOn,
                            onCheckedChange = { isTimerOn = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFF6C63FF), // purple highlight
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                TimePicker(state = timeState)

                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(
                        onClick = { timerAlertDialog = false }
                    ) {
                        Text("Cancel", color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { timerAlertDialog = false }
                    ) {
                        Text("Set Timer")
                    }
                }
            }
        }
    }
    }
}


@Composable
fun DashboardCard(
    item: DashboardItem,
    isActive: Boolean,
    onClick: () -> Unit,
    onTimerClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .aspectRatio(0.8f)
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isActive) CardActive else CardInactive
        ),
        elevation = CardDefaults.cardElevation(6.dp),
        onClick = onClick
    ) {Box(modifier = Modifier.fillMaxSize()){
        IconButton(
            onClick = onTimerClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 4.dp, 4.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.timer_24px),
                contentDescription = "Timer",
                modifier = Modifier.size(32.dp),
                tint = if(isActive) IconBlue else White
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                modifier = Modifier.size(84.dp),
                tint = if (isActive) IconBlue else White
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = IconBlue
            )
//            Row(modifier = Modifier.fillMaxWidth()) {
//                IconButton(onClick = {}) {
//                    Icon(painterResource(R.drawable.timer_24px), contentDescription = "Timer")
//                }
//            }
        }
    }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    SmartBoardTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MainScreen()
        }
    }
}

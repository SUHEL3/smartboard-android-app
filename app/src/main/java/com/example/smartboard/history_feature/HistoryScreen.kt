package com.example.smartboard.history_feature

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.smartboard.history_feature.HistoryDataClass
import androidx.compose.runtime.collectAsState

@Composable
fun HistoryScreen(email: String , viewModel: HistoryViewModel = viewModel() ) {
    val data = viewModel.historyList.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.fetchHistory(email = email)
    }
    Column {
        // Header
        Row(modifier = Modifier.fillMaxWidth()) {
            Tablecell("Device", 1f, true)
            Tablecell("State", 1f, true)
            Tablecell("User", 2f, true)
            Tablecell("Time",1f,true)
        }

        LazyColumn {
            items(data) { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Tablecell(row.appliance, 1f)
                    Tablecell(row.operation, 1f)
                    Tablecell(row.user, 2f)
                    Tablecell(row.timestamp, 1f)
                }
                }
            }
        }
    }


@Composable
fun RowScope.Tablecell(
    text:String,
    weight:Float,
    isHeader:Boolean = false
){
Text(text = text,
    modifier = Modifier.weight(weight)
        .border(1.dp, Color.Gray)
        .padding(8.dp),
    fontWeight = if (isHeader) FontWeight.Bold else FontWeight.Normal,
    textAlign = TextAlign.Center
)
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    val tableData = listOf(
        HistoryDataClass("Light", "On", "User1", "12:00 PM"),
        HistoryDataClass("Light", "On", "User1", "12:00 PM"),
        HistoryDataClass("Light", "On", "User1", "12:00 PM"),
        HistoryDataClass("Light", "On", "User1", "12:00 PM")
    )
    //HistoryScreen(tableData)
}
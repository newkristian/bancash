package me.kristianconk.bancash.presentation.features.home

import android.icu.text.NumberFormat
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.kristianconk.bancash.R
import me.kristianconk.bancash.ui.theme.BanCashTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeUiState,
    actions: HomeActions
) {
    BackHandler(enabled = true) {
        // no esta permitido el back
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = uiState.userName,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
    ) { paddVals ->
        LazyColumn(
            modifier = Modifier
                .padding(paddVals)
                .fillMaxSize()
        ) {
            uiState.balance?.let {
                item(key = "balance") {
                    BalanceSection(balance = it)
                }
            }
        }
    }
}

@Composable
fun BalanceSection(balance: Double, modifier: Modifier = Modifier) {
    Card(
        onClick = { /*TODO*/ }, modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.balance_sheet),
                contentDescription = "balance",
                modifier = Modifier.padding(8.dp)
            )
            Column {
                Text(text = "Tienes")
                Text(
                    text = NumberFormat.getCurrencyInstance().format(balance),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(text = "En tu cuenta")
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(modifier: Modifier = Modifier) {
    BanCashTheme {
        HomeScreen(
            uiState = HomeUiState(userName = "Alfonso", balance = 1347.50),
            actions = HomeActions()
        )
    }
}
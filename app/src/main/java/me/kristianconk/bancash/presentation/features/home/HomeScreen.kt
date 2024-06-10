package me.kristianconk.bancash.presentation.features.home

import android.icu.text.NumberFormat
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import me.kristianconk.bancash.R
import me.kristianconk.bancash.domain.model.Movement
import me.kristianconk.bancash.domain.model.MovementType
import me.kristianconk.bancash.presentation.features.movements.MovementRow
import me.kristianconk.bancash.ui.theme.BanCashTheme
import java.time.LocalDateTime

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
                ),
                actions = {
                    IconButton(onClick = actions.onSignOut) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.sign_out),
                            contentDescription = "cerrar sesion"
                        )
                    }
                }
            )
        },
    ) { paddVals ->
        LazyColumn(
            modifier = Modifier
                .padding(paddVals)
                .fillMaxSize(),

            ) {
            item(key = "avatar") {
                Box(modifier = Modifier.fillMaxWidth()) {
                    uiState.avatarUrl?.let {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current).data(it).crossfade(true).build(),
                            contentDescription = "avatar",
                            placeholder = painterResource(id = R.drawable.empty_avatar),
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .padding(8.dp)
                                .size(width = 180.dp, height = 180.dp)
                                .align(Alignment.Center)
                        )
                    } ?: run {
                        Image(
                            imageVector = ImageVector.vectorResource(id = R.drawable.empty_avatar),
                            contentDescription = "avatar",
                            modifier = Modifier
                                .padding(8.dp)
                                .size(width = 180.dp, height = 180.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
            uiState.balance?.let {
                item(key = "balance") {
                    BalanceSection(balance = it)
                }
            }
            items(items = uiState.movements, key = { it.id }) {
                MovementRow(movement = it)
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
            uiState = HomeUiState(
                userName = "Alfonso", balance = 1347.50, movements = listOf(
                    Movement(
                        id = "1234",
                        dateTime = LocalDateTime.now(),
                        amount = 100.0,
                        type = MovementType.PAYMENT,
                        description = "promocion aplicada"
                    ),
                    Movement(
                        id = "2345",
                        dateTime = LocalDateTime.now(),
                        amount = 100.0,
                        type = MovementType.WITHDRAW,
                        description = "promocion aplicada"
                    ),
                    Movement(
                        id = "asdfg",
                        dateTime = LocalDateTime.now(),
                        amount = 100.0,
                        type = MovementType.PURCHASE,
                        description = "promocion aplicada"
                    )
                )
            ),
            actions = HomeActions()
        )
    }
}
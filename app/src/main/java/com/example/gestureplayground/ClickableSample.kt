package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role

@Composable
fun ClickableSample() {
    val logger = rememberLogger()
    Column {
        Image(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(
                onClickLabel = "clickableの動作を確認する",
                role = Role.Button
            ) { logger.log("Click") },
            painter = painterResource(R.drawable.arcticfox),
            contentDescription = null,
        )
        LogConsole(logger = logger, modifier = Modifier.weight(1f))
    }
}
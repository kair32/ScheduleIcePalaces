package su.wolfstudio.schedule_ice.ui.athletes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.ui.theme.SkateColor


@Composable
fun EmptyAthletes(modifier: Modifier, addClick: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(72.dp),
            painter = painterResource(id = R.drawable.ic_figure_skating),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 10.dp),
            text = stringResource(id = R.string.empty_athletes),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        )
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(20))
                .clickable { addClick() }
                .padding(all = 16.dp),
            text = stringResource(id = R.string.add),
            style = TextStyle(
                color = SkateColor,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        )
    }
}
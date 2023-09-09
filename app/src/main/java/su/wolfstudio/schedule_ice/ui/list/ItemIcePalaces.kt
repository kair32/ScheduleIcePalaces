package su.wolfstudio.schedule_ice.ui.list

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.model.Palace
import su.wolfstudio.schedule_ice.ui.theme.SkateColor40
import su.wolfstudio.schedule_ice.ui.theme.medium

@Composable
fun ItemIcePalaces(
    modifier: Modifier,
    palace: Palace,
    onFavorite: (palaceId: Long) -> Unit,
    onPalacesClick: (palaceId: Long) -> Unit,
    onPalacesScheduleClick: (palaceId: Long) -> Unit
){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(palace.urlRoute)
    val context = LocalContext.current
    Box(modifier = modifier){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = palace.name,
                modifier = Modifier
                    .clickable { onPalacesClick(palace.id) }
                    .weight(1f)
                    .padding(
                        horizontal = 16.dp,
                        vertical = 12.dp
                    ),
                style = Typography.medium
            )
            ImageIce(
                modifier = Modifier
                    .padding(start = 16.dp, end = 8.dp),
                res = R.drawable.ic_schedule
            ) { onPalacesScheduleClick(palace.id) }
            ImageIce(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                res = R.drawable.ic_route
            ){
                ContextCompat.startActivity(context, intent, null)
            }
            ImageIce(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp),
                res = if (palace.isFavorite)
                    R.drawable.ic_favorite_fill
                else R.drawable.ic_favorite
            ){
                onFavorite(palace.id)
            }
        }

        Divider(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomCenter),
            color = SkateColor40,
            thickness = 1.dp
        )
    }
}
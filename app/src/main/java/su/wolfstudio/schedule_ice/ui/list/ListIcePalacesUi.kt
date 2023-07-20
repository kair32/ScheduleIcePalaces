package su.wolfstudio.schedule_ice.ui.list

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.model.Palaces
import su.wolfstudio.schedule_ice.ui.components.SearchLine
import su.wolfstudio.schedule_ice.ui.theme.SkateColor
import su.wolfstudio.schedule_ice.ui.theme.SkateColor40
import su.wolfstudio.schedule_ice.ui.theme.medium

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListIcePalacesUi(component: ListPalacesComponent) {
    val listPalaces by component.listPalaces.collectAsState()
    val isSearch = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            stickyHeader {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.list_palaces))
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(SkateColor),
                    actions = {
                        IconButton(
                            onClick = {
                                isSearch.value = !isSearch.value
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = null
                            )
                        }
                    }
                )
                SearchLine(findLine = component::onFindLine, isSearch)
            }
            items(
                items = listPalaces,
                key = {it.id}
            ){
                ItemIcePalaces(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(),
                    palaces = it,
                    component = component
                )
            }
        }
    )
}

@Composable
fun ItemIcePalaces(modifier: Modifier, palaces: Palaces, component: ListPalacesComponent){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(palaces.urlRoute)
    val context = LocalContext.current
    Box(modifier = modifier){
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = palaces.name,
                modifier = Modifier
                    .clickable { component.onPalacesClick(palaces.id) }
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
            ) { component.onPalacesScheduleClick(palaces.id) }
            ImageIce(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                res = R.drawable.ic_route
            ){
                startActivity(context, intent, null)
            }
            ImageIce(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp),
                res = if (palaces.isFavorite)
                    R.drawable.ic_favorite_fill
                else R.drawable.ic_favorite
            ){
                component.onFavorite(palaces.id)
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

@Composable
fun ImageIce(modifier: Modifier, res: Int, onClick: () -> Unit){
    Image(
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = MutableInteractionSource(),
                indication = rememberRipple(bounded = false)
            ),
        painter = painterResource(res),
        contentDescription = null
    )
}
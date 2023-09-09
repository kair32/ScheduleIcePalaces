package su.wolfstudio.schedule_ice.ui.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.navigation.Screen
import su.wolfstudio.schedule_ice.ui.components.SearchLine
import su.wolfstudio.schedule_ice.ui.theme.SkateColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ListIcePalacesUi(
    modifier: Modifier,
    navigation: NavController,
    viewModel: ListPalacesViewModel = viewModel(ListPalacesViewModelImpl::class.java)
) {
    val listPalaces by viewModel.listPalace.collectAsState()
    val isSearch = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        content = {
            stickyHeader {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.list_palaces))
                    },
                    colors = TopAppBarDefaults.topAppBarColors(SkateColor),
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
                SearchLine(findLine = viewModel::onFindLine, isSearch)
            }
            items(
                items = listPalaces,
                key = { it.id }
            ) {
                ItemIcePalaces(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateItemPlacement(),
                    palace = it,
                    onFavorite = viewModel::onFavorite,
                    onPalacesClick = { palaceId ->
                        navigation.navigate(Screen.ViewIcePalaces.screenName + "/$palaceId/false")
                    },
                    onPalacesScheduleClick = { palaceId ->
                        navigation.navigate(Screen.ViewIcePalaces.screenName + "/$palaceId/true")
                    }
                )
            }
        }
    )
}

@Composable
fun ImageIce(modifier: Modifier, res: Int, onClick: () -> Unit) {
    Image(
        modifier = modifier
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = false)
            ),
        painter = painterResource(res),
        contentDescription = null
    )
}
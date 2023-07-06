package su.wolfstudio.schedule_ice.ui.list

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import su.wolfstudio.schedule_ice.R
import su.wolfstudio.schedule_ice.model.Palaces
import su.wolfstudio.schedule_ice.ui.theme.GreenBlueColor
import su.wolfstudio.schedule_ice.ui.theme.SkateColor
import su.wolfstudio.schedule_ice.ui.theme.SkateColor40
import su.wolfstudio.schedule_ice.ui.theme.medium

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListIcePalacesUi(component: ListPalacesComponent) {
    val listPalaces by component.listPalaces.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.list_palaces))
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(SkateColor)
        )
        SearchLine(findLine = component::onFindLine)
        listPalaces.map {
            ItemIcePalaces(it, component)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchLine(findLine: (String) -> Unit){
    var text by rememberSaveable { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val focus = remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged { focus.value = it.isFocused },
        value = text,
        onValueChange = {
            text = it
            findLine.invoke(text)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            IconButton(
                enabled = focus.value,
                onClick = {
                if (text.isBlank())
                    focusManager.clearFocus()
                text = ""
                findLine.invoke(text)
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_close),
                    contentDescription = null
                )
            }
        },
        placeholder = { Text(stringResource(id = R.string.search_palaces)) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = SkateColor,
            focusedIndicatorColor = GreenBlueColor
        )

    )
}

@Composable
fun ItemIcePalaces(palaces: Palaces, component: ListPalacesComponent){
    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(palaces.urlRoute)
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxWidth() ){
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
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp,
                        bottom = 12.dp
                    ),
                style = Typography.medium
            )
            ImageIce(
                modifier = Modifier,
                res = R.drawable.ic_schedule
            ) { component.onPalacesScheduleClick(palaces.id) }
            ImageIce(
                modifier = Modifier,
                res = R.drawable.ic_route
            ){
                startActivity(context, intent, null)
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
            )
            .padding(horizontal = 16.dp),
        painter = painterResource(res),
        contentDescription = null
    )
}
package com.example.weathercompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercompose.R
import com.example.weathercompose.data.Forecastday
import com.example.weathercompose.data.Hour
import com.example.weathercompose.data.WeatherModel
import com.example.weathercompose.ui.theme.Blue10
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun MainCard(item: WeatherModel, onClickSync: () -> Unit, onSearchClick: () -> Unit) {
    var stringQuery by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .padding(5.dp)
            .alpha(0.5f),

        ) {
        Card(

            modifier = Modifier
                .fillMaxWidth(),
            elevation = 0.dp,
            backgroundColor = Blue10,
            shape = RoundedCornerShape(15.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Blue10.copy(alpha = 0.5f)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, top = 8.dp),
                        text = item.location.localTime,
                        style = TextStyle(fontSize = 15.sp, color = Color.White)
                    )
                    AsyncImage(
                        model = "https:${item.current.condition.icon}",
                        contentDescription = "icon",
                        modifier = Modifier
                            .padding(end = 8.dp, top = 8.dp)
                            .size(45.dp)
                    )
                }
                Text(
                    text = item.location.name,
                    style = TextStyle(fontSize = 24.sp),
                    color = Color.White
                )
                Text(
                    text = "${item.forecast.forecastday[0].day.maxtempC}ºC/${item.forecast.forecastday[0].day.mintempC}ºC",
                    style = TextStyle(fontSize = 50.sp, fontWeight = FontWeight.Bold),
                    color = Color.White
                )
                Text(
                    text = item.current.condition.text,
                    style = TextStyle(fontSize = 16.sp),
                    color = Color.White
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = {
                        onSearchClick.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "search_icon",
                            tint = Color.White,

                        )
                    }
                    Text(
                        text = "${item.forecast.forecastday[0].day.maxtempC}ºC/${item.forecast.forecastday[0].day.mintempC}ºC",
                        style = TextStyle(fontSize = 16.sp),
                        color = Color.White
                    )

                    IconButton(onClick = {
                        onClickSync.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_sync),
                            contentDescription = "sync_icon",
                            tint = Color.White
                        )
                    }
                }
            }
        }
//    }
//return stringQuery
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(item: WeatherModel) {
    val tabList = listOf("HOURS", "DAYS")
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(start = 3.dp, end = 3.dp)
            .alpha(0.5f)
            .clip(RoundedCornerShape(5.dp))
    ) {
        TabRow(

            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )
            },
            backgroundColor = Blue10,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(selected = false, onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                    text = {
                        Text(text = text)
                    }
                )
            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->

            val list1 = mutableListOf<Forecastday>()
            val list2 = mutableListOf<Hour>()
            for (forecast in item.forecast.forecastday) {
                list1.add(forecast)
                for (hour in item.forecast.forecastday[0].hour) {
                    list2.add(hour)
                }
            }
            when (index) {
                0 -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(
                            list2
                        ) { _, item ->
                            ListItemByHour(item)
                        }
                    }


                }

                1 -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        itemsIndexed(
                            list1
                        ) { _, item ->
                            ListItemByDays(item)
                        }
                    }
                }


            }
        }


    }
}

@Composable
fun SearchArea(onClickSearch: (String) -> Unit) {
    var searchVisible by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var onClick by remember {
        mutableStateOf(Unit)
    }

    Row(modifier = Modifier.padding(16.dp)) {
        if (searchVisible) {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                modifier = Modifier.clickable {
                    onClick = onClickSearch.invoke(searchText)
                    searchVisible = !searchVisible
                }
            )
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                )
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Search",
                modifier = Modifier.clickable {
                    searchVisible = !searchVisible
                }
            )
        }
    }
    return onClick
}
package com.example.cinewave.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDownAlt
import androidx.compose.material.icons.filled.ThumbUpAlt
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cinewave.R
import com.example.cinewave.presentation.component.VideoPlayer
import com.example.cinewave.presentation.component.VideoScreen
import kotlinx.coroutines.flow.distinctUntilChanged

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel : HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val paginationVideo by viewModel.paginatedVideo.collectAsState()
    val pagerState = rememberPagerState()


    LaunchedEffect(key1 = pagerState ){
        snapshotFlow {
            pagerState.currentPage
        }.distinctUntilChanged().collect{ page ->
            pagerState.animateScrollToPage(page)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ){

        VerticalPager(
            pageCount = paginationVideo.items.size,
            state = pagerState ,
            key = {
                paginationVideo.items[it].id
            }
        ) {index ->
            if (index == pagerState.currentPage){
                state.playMediaAt(index)
                VideoScreen(
                    state = state,
                    viewModel = viewModel,
                    index = index,
                    pagingState = paginationVideo,
                    modifier = Modifier.clickable {
                        viewModel.onTap()
                    },
                )

            }


        }
    }
}
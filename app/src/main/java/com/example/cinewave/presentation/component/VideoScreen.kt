package com.example.cinewave.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleFilled
import androidx.compose.material.icons.filled.ThumbDownAlt
import androidx.compose.material.icons.filled.ThumbUpAlt
import androidx.compose.material.icons.outlined.Comment
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.cinewave.R
import com.example.cinewave.presentation.home.HomeViewModel
import com.example.cinewave.presentation.home.PaginationVideo
import com.example.cinewave.presentation.util.VideoUIState

@Composable
fun VideoScreen(
    pagingState : PaginationVideo,
    state : VideoUIState,
    viewModel: HomeViewModel,
    index : Int,
    modifier: Modifier = Modifier
) {

    var pause by remember{
        mutableStateOf(false)
    }

    LaunchedEffect(true){
        viewModel.effect.collect{ effect ->
            pause = effect
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        VideoPlayer(
            video = pagingState.items[index],
            modifier = Modifier.fillMaxSize(),
            player = state.player,
            viewModel = viewModel
        )

        if (pause){
            Box(modifier = Modifier.fillMaxSize() , contentAlignment = Alignment.Center){
                Icon(
                    imageVector = Icons.Filled.PlayCircleFilled,
                    contentDescription = "Play",
                    modifier = Modifier.size(85.dp),
                    tint = Color.White
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(15.dp)
                .align(Alignment.BottomCenter)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(),
                contentAlignment = Alignment.BottomStart
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = pagingState.items[index].username,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(5.dp))


                    }

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = pagingState.items[index].title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

        }
    }
}
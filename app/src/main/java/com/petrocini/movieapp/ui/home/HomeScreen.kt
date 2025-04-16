package com.petrocini.movieapp.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.petrocini.movieapp.ui.components.LoadingView
import com.petrocini.movieapp.ui.home.components.BodyContent
import com.petrocini.movieapp.ui.home.components.TopContent
import kotlinx.coroutines.delay

val defaultPadding = 16.dp
val itemSpacing = 8.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onMovieClick: (id: Int) -> Unit
) {
    var isAutoScrolling by remember {
        mutableStateOf(true)
    }
    val state by homeViewModel.homeState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { state.discoverMovies.size }
    )
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(key1 = pagerState.currentPage) {
        if (isDragged) {
            isAutoScrolling = false
        } else {
            isAutoScrolling = true
            delay(5000)
            with(pagerState) {
                val target = if (currentPage < state.discoverMovies.size - 1) currentPage + 1 else 0
                scrollToPage(target)
            }
        }
    }

    Box(modifier = modifier) {
        AnimatedVisibility(visible = state.error != null) {
            Text(
                text = state.error ?: "unknown error",
                color = MaterialTheme.colorScheme.error,
                maxLines = 2
            )
        }

        AnimatedVisibility(visible = !state.isLoading && state.error == null) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val boxHeight = maxHeight
                val topItemHeight = boxHeight * 0.45f
                val bodyItemHeight = boxHeight * 0.55f

                HorizontalPager(
                    state = pagerState,
                    contentPadding = PaddingValues(defaultPadding),
                    pageSize = PageSize.Fill,
                    pageSpacing = itemSpacing
                ) { page ->
                    if (isAutoScrolling) {
                        AnimatedContent(
                            targetState = page,
                            label = "",
                        ) { index ->
                            TopContent(
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .fillMaxWidth()
                                    .height(topItemHeight),
                                movie = state.discoverMovies[index],
                                onMovieClick = {
                                    onMovieClick(it)
                                }
                            )
                        }
                    } else {
                        TopContent(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .fillMaxWidth()
                                .height(topItemHeight),
                            movie = state.discoverMovies[page],
                            onMovieClick = {
                                onMovieClick(it)
                            }
                        )
                    }
                }

                BodyContent(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .height(bodyItemHeight),
                    discoverMovies = state.discoverMovies,
                    trendingMovies = state.trendingMovies,
                    onMovieClick = onMovieClick
                )
            }
        }
    }

    LoadingView(isLoading = state.isLoading)
}

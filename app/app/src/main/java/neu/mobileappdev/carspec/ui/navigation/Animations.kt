package neu.mobileappdev.carspec.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally

object Animations {
    val enterRight =
        slideInHorizontally(
            initialOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(300),
        )

    val exitRight =
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> fullWidth },
            animationSpec = tween(300),
        )

    val enterLeft =
        slideInHorizontally(
            initialOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(300),
        )

    val exitLeft =
        slideOutHorizontally(
            targetOffsetX = { fullWidth -> -fullWidth },
            animationSpec = tween(300),
        )
}

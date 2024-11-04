package com.github.pploszczyca.expensetrackerv2.features.main.features.bottom_bar

sealed interface MainBottomBarEvent {
    data object MenuButtonClick : MainBottomBarEvent
    data object SearchButtonClick : MainBottomBarEvent
}
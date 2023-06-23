package com.example.expensetrackerv2.ui.main.features.bottom_bar

sealed interface MainBottomBarEvent {
    object MenuButtonClick : MainBottomBarEvent
    object ClearButtonClick : MainBottomBarEvent
    object SearchButtonClick : MainBottomBarEvent
    object FilterButtonClick : MainBottomBarEvent
}
package com.rahul.utilityapp.ui.navigation

sealed class BottomNav(val route:String,val title:String){
    object Home:BottomNav("home","Home")
    object Settings:BottomNav("settings","Settings")
}
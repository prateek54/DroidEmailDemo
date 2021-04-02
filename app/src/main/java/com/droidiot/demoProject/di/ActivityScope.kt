package com.droidiot.demoProject.di

import javax.inject.Scope

@Scope
annotation class ActivityScope

//scope is used to define once lifetime
//qualifier/named is used to define in case we have 2 different variation of same type of class then we will use this
package com.droidiot.demoProject.utils

import io.reactivex.Scheduler

interface ScheduleProvider {

    fun io():Scheduler

    fun ui(): Scheduler

    fun computation():Scheduler

}
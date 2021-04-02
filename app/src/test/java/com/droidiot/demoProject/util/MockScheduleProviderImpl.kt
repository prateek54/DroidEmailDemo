package com.droidiot.demoProject.util

import com.droidiot.demoProject.utils.ScheduleProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class MockScheduleProviderImpl :
    ScheduleProvider {
    override fun io(): Scheduler = Schedulers.trampoline()
    override fun ui(): Scheduler = Schedulers.trampoline()
    override fun computation(): Scheduler = Schedulers.trampoline()

}
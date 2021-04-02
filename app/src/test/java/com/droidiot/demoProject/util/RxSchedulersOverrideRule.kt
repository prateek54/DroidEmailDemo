package com.droidiot.demoProject.util

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.functions.Function
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.util.concurrent.Callable

open class RxSchedulersOverrideRule : TestRule {

    private val schedulerInstance = Schedulers.trampoline()

    private val schedulerFunction = Function<Scheduler, Scheduler> {
        return@Function schedulerInstance
    }

    private val schedulerFunctionLazy = Function<Callable<Scheduler>, Scheduler> {
        return@Function schedulerInstance
    }

    override fun apply(base: Statement, description: Description): Statement {

        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerFunctionLazy)

                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler(schedulerFunction)
                RxJavaPlugins.setNewThreadSchedulerHandler(schedulerFunction)
                RxJavaPlugins.setComputationSchedulerHandler(schedulerFunction)

                RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerFunctionLazy)
                RxJavaPlugins.setInitIoSchedulerHandler(schedulerFunctionLazy)
                base.evaluate()

                RxAndroidPlugins.reset()
                RxJavaPlugins.reset()
            }
        }
    }
}
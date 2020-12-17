package com.nassiansoft.watchit

import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxSchedulerRule:TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {

        return  object: Statement(){

            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler{ TrampolineScheduler.instance() }
                RxJavaPlugins.setComputationSchedulerHandler { TrampolineScheduler.instance() }

                RxJavaPlugins.reset()

            }

        }
    }

}
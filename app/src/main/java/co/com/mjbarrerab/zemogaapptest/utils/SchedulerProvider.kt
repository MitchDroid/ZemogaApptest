package co.com.mjbarrerab.zemogaapptest.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by miller.barrera on 08/08/2020.
 */

@Singleton
interface BaseSchedulerProvider  {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler
}


@Singleton
class SchedulerProvider @Inject constructor() : BaseSchedulerProvider {
    override fun computation() = Schedulers.computation()
    override fun ui() = AndroidSchedulers.mainThread()
    override fun io() = Schedulers.io()
}

/**
 *TrampolineSchedulerProvider scheduler is great for testing.
 *It executes all tasks in a FIFO manner on one of the participating threads.
 *TrampolineSchedulerProvider and TestSchedulerProvider are used only in tests
 */
@Singleton
class TrampolineSchedulerProvider @Inject constructor() : BaseSchedulerProvider {
    override fun computation() = Schedulers.trampoline()
    override fun ui() = Schedulers.trampoline()
    override fun io() = Schedulers.trampoline()
}

/**
 *TestSchedulerProvider scheduler is great for testing.
 *It executes all tasks in a FIFO manner on one of the participating threads.
 *TrampolineSchedulerProvider and TestSchedulerProvider are used only in tests
 */
@Singleton
class TestSchedulerProvider @Inject constructor(private val scheduler: TestScheduler) : BaseSchedulerProvider {
    override fun computation() = scheduler
    override fun ui() = scheduler
    override fun io() = scheduler
}
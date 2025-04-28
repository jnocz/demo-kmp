package utils

import di.AppCoroutineDispatchers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

open class TestCoroutineDispatchers : AppCoroutineDispatchers() {
    override val database: CoroutineDispatcher = StandardTestDispatcher()
    override val disk: CoroutineDispatcher = StandardTestDispatcher()
    override val network: CoroutineDispatcher = StandardTestDispatcher()
    override val main: CoroutineDispatcher = StandardTestDispatcher()
}
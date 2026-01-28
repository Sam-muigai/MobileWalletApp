package com.app.compulynx.core.work.status

import android.content.Context
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.app.compulynx.core.work.workers.SyncLocalTransactionWorker
import com.app.compulynx.core.work.workers.SyncLocalTransactionWorker.Companion.SYNC_LOCAL_TRANSACTION_WORK_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.map

interface SyncManager {
    val isSyncing: Flow<Boolean>
    fun requestSync()
}

class SyncManagerImpl(
    private val context: Context
) : SyncManager {
    override val isSyncing: Flow<Boolean>
        get() = WorkManager.getInstance(context)
            .getWorkInfosForUniqueWorkFlow(SYNC_LOCAL_TRANSACTION_WORK_NAME)
            .map(List<WorkInfo>::anyRunning)
            .conflate()

    override fun requestSync() {
        val workManager = WorkManager.getInstance(context)
        workManager.enqueueUniqueWork(
            SYNC_LOCAL_TRANSACTION_WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            SyncLocalTransactionWorker.startUpSyncWork(),
        )
    }
}

private fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }
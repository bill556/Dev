package com.sino.frame.common.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.sino.frame.common.bean.home.AppletsMenuBean
import kotlinx.coroutines.flow.Flow

@Dao
abstract class AppletsMenuDao : BaseDao<AppletsMenuBean>() {
    @Query("SELECT * FROM AppletsMenuBean")
    abstract fun getAllFlow(): Flow<List<AppletsMenuBean>>
}
package com.sino.frame.common.db.dao

import androidx.room.Dao
import com.sino.frame.common.bean.home.AppletsMenuBean
import com.sino.frame.common.bean.home.DictBean
import javax.inject.Inject

@Dao
abstract class DictDao : BaseDao<DictBean>()
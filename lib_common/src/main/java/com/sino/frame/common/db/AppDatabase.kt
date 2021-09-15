package com.sino.frame.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sino.frame.common.bean.home.AppletsMenuBean
import com.sino.frame.common.bean.home.DictBean
import com.sino.frame.common.db.dao.AppletsMenuDao
import com.sino.frame.common.db.dao.DictDao

@Database(entities = [AppletsMenuBean::class, DictBean::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getAppletsMenuDao(): AppletsMenuDao
    abstract fun getDictDao(): DictDao

    companion object {
        const val DB_NAME = "AppDatabase.db"

        const val CREATE_DICT_SQL =
            "CREATE TABLE IF NOT EXISTS `DictBean` (`id` TEXT NOT NULL, `parentId` TEXT NOT NULL, `code` TEXT NOT NULL, `dictKey` INTEGER NOT NULL, `dictValue` TEXT NOT NULL, `sort` INTEGER NOT NULL, `remark` TEXT NOT NULL, `isDeleted` INTEGER NOT NULL, PRIMARY KEY(`id`))"

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(CREATE_DICT_SQL)
            }
        }
    }
}

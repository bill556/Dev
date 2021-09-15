package com.sino.frame.common.di

import android.content.Context
import androidx.room.Room
import com.sino.frame.base.BaseApplication
import com.sino.frame.common.db.AppDatabase
import com.sino.frame.common.db.AppDatabase.Companion.MIGRATION_1_2
import com.sino.frame.common.db.dao.AppletsMenuDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * 全局作用域的的依赖注入模块
 *
 * @author
 * @since 6/4/21 8:58 AM
 */
@Module
@InstallIn(SingletonComponent::class)
object DIDbModule {
    /**
     * [AppDatabase]依赖提供方法
     *
     * @return AppDatabase
     */
    @Singleton
    @Provides
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
            BaseApplication.context,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).addMigrations(MIGRATION_1_2).build()
    }

    @Provides
    fun provideBookDao(database: AppDatabase): AppletsMenuDao {
        return database.getAppletsMenuDao()
    }
}
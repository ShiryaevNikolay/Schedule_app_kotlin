package com.shiryaev.data.di.modules

import androidx.room.Room
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.repository.WeekRepository
import com.shiryaev.data.database.repository.ScheduleRepository
import com.shiryaev.data.database.Storage
import com.shiryaev.data.database.repository.NoteRepository
import com.shiryaev.domain.utils.UtilsDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val mApp: AppDelegate) {

    @Provides
    @Singleton
    fun provideApp() = mApp

    @Provides
    @Singleton
    fun provideStorage() = Room.databaseBuilder(mApp, Storage::class.java, UtilsDb.APP_DATABASE.name)
                .fallbackToDestructiveMigration()
                .build()

    @Provides
    @Singleton
    fun provideScheduleRepository() = ScheduleRepository()

    @Provides
    @Singleton
    fun provideWeekRepository() = WeekRepository()

    @Provides
    @Singleton
    fun provideNoteRepository() = NoteRepository()
}
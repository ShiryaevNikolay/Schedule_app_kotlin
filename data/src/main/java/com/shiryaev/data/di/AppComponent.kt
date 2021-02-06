package com.shiryaev.data.di

import com.shiryaev.data.database.repository.NoteRepository
import com.shiryaev.data.database.repository.ScheduleRepository
import com.shiryaev.data.database.repository.WeekRepository
import com.shiryaev.data.di.modules.AppModule
import com.shiryaev.data.viewmodels.AddScheduleViewModel
import com.shiryaev.data.viewmodels.NoteViewModel
import com.shiryaev.data.viewmodels.PageScheduleViewModel
import com.shiryaev.data.viewmodels.WeekSettingsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun injectPageScheduleViewModel(viewModel: PageScheduleViewModel)
    fun injectAddScheduleViewModel(viewModel: AddScheduleViewModel)
    fun injectWeekSettingsViewModel(viewModel: WeekSettingsViewModel)
    fun injectNoteViewModel(viewModel: NoteViewModel)
    fun injectWeekRepository(repository: WeekRepository)
    fun injectScheduleRepository(repository: ScheduleRepository)
    fun injectNoteRepository(repository: NoteRepository)
}
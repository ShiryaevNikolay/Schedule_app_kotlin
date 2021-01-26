package com.shiryaev.data.di

import com.shiryaev.data.database.Repository
import com.shiryaev.data.database.WeekRepository
import com.shiryaev.data.di.modules.AppModule
import com.shiryaev.data.viewmodels.AddScheduleViewModel
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
    fun injectWeekRepository(repository: WeekRepository)
    fun injectRepository(repository: Repository)
}
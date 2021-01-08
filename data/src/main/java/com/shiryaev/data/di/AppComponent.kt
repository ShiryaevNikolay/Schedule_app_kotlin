package com.shiryaev.data.di

import com.shiryaev.data.database.Repository
import com.shiryaev.data.di.modules.AppModule
import com.shiryaev.data.viewmodels.AddScheduleViewModel
import com.shiryaev.data.viewmodels.ScheduleViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun injectScheduleViewModel(viewModel: ScheduleViewModel)
    fun injectAddScheduleViewModel(viewModel: AddScheduleViewModel)
    fun injectRepository(repository: Repository)
}
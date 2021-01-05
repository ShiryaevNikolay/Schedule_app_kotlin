package com.shiryaev.data.di

import com.shiryaev.data.database.Repository
import com.shiryaev.data.di.modules.AppModule
import com.shiryaev.data.viewmodels.ScheduleViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun injectScheduleViewModel(viewModel: ScheduleViewModel)
    fun injectRepository(repository: Repository)
}
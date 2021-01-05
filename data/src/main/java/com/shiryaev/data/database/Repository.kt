package com.shiryaev.data.database

import com.shiryaev.data.AppDelegate
import javax.inject.Inject

class Repository {

    @Inject
    lateinit var mStorage: Storage

    init {
        AppDelegate.getAppComponent().injectRepository(this)
    }
}
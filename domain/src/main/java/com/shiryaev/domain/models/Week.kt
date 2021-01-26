package com.shiryaev.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shiryaev.domain.utils.UtilsTable

@Entity(tableName = UtilsTable.TABLE_WEEK)
class Week {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UtilsTable.WEEK_ID)
    var mId: Long = 0

    @ColumnInfo(name = UtilsTable.WEEK_NAME)
    var mName: String? = null

    @ColumnInfo(name = UtilsTable.WEEK_COLOR)
    var mColor: String? = null
}
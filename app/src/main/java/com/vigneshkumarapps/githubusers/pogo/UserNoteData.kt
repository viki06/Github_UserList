package com.vigneshkumarapps.githubusers.pogo

import androidx.room.Entity
import androidx.room.PrimaryKey



    @Entity
    data class UserNoteData(
        @PrimaryKey val login : String
        ,val Note : String? = ""

    )

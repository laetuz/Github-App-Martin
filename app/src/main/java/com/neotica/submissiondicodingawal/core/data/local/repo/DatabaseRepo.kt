package com.neotica.submissiondicodingawal.core.data.local.repo

import com.neotica.submissiondicodingawal.core.data.local.database.LocalDataSource
import com.neotica.submissiondicodingawal.core.domain.home.IHomeRepo

class DatabaseRepo(
    private val localDataSource: LocalDataSource,
    private val remote: IHomeRepo
) {
}
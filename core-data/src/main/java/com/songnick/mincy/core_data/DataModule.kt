package com.songnick.mincy.core_data

import LocalMediaRepository
import MediaRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindToLocalMediaRepository(
        localMediaRepository: LocalMediaRepository
    ):MediaRepository
}
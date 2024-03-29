package com.songnick.mincy.core.data.di
import com.songnick.mincy.core.data.repositorys.LocalMediaRepository
import com.songnick.mincy.core.data.repositorys.MediaRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*****
 * @author qfsong
 * Create Time: 2022/9/19
 **/
@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsToLocalMediaRepository(
        localMediaRepository: LocalMediaRepository
    ):MediaRepository
}


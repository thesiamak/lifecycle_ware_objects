package ir.drax.dindinn.di

import com.dotin.app.network.LoqateApiService
import ir.drax.dindinn.repository.LoqateRepository
import ir.drax.dindinn.repository.LoqateRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //////// ORDERS REPOSITORY ///////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////

    @Provides
    @ViewModelScoped
    fun provideLoqateRepository(loqateApiService: LoqateApiService): LoqateRepository = LoqateRepositoryImpl(loqateApiService)


}
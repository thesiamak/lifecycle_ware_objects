package ir.drax.dindinn.di.module

import dagger.Module
import ir.drax.dindinn.di.module.NetworkModule

@Module(
    includes = [NetworkModule::class, ReposModule::class]
)
class DataModule
package ir.drax.samples.lifecycle.di.module

import dagger.Module

@Module(
    includes = [NetworkModule::class, ReposModule::class, DatabaseModule::class]
)
class DataModule
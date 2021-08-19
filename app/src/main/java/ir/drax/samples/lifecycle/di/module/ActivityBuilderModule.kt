package ir.drax.samples.lifecycle.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.drax.samples.lifecycle.ui.MainActivity
import ir.drax.samples.lifecycle.ui.ingredients.IngredientsFragment
import ir.drax.samples.lifecycle.ui.orders.OrdersFragment

@Module
interface ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): OrdersFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): IngredientsFragment
}
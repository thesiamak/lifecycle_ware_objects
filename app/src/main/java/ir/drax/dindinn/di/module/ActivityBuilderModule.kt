package ir.drax.dindinn.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import ir.drax.dindinn.ui.MainActivity
import ir.drax.dindinn.ui.ingredients.IngredientsFragment
import ir.drax.dindinn.ui.orders.OrdersFragment

@Module
interface ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): OrdersFragment

    @ContributesAndroidInjector
    abstract fun contributeDetailsFragment(): IngredientsFragment
}
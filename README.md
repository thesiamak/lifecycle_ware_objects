# dinDinn
## Sample project for the interview at DinDinn

### Technologies
`Arcitecture`: MVVM
`Database`: Room
`DI`: Dagger2
`Thread system`: RxJava
`Network`: Retrofit
`Other`: KotlinDsl, DataBinding, LiveData, CustomView, Jetpack Nav,..

### Notes
* Mock data is fetched throughout an Interceptor. It returns a list of orders with a 5 min expiration period.
* Single source of truth is implemented(Orders are stored in DB > Repos fetch DB) 
* Thread management is done via [CountdownRunnerView.kt] which is lifecycleAware.
* View states are handled in such way to consume lowest possible resources.
* Kotlin DSL is used for Gradle scripts.
* Unfortunately there was not enough time for me to write test cases.
  I'm busy with some personal problems these last few weeks an couldn't finish tests.
  However, if I'd be given another chance there will no such excuse anymore.
  
* The whole process would be quite simpler and cleaner if we could use Kotlin Coroutines.

That's it. Thanks for reading.
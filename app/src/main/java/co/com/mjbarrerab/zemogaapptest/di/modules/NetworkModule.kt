package co.com.mjbarrerab.zemogaapptest.di.modules

import co.com.mjbarrerab.zemogaapptest.data.network.manager.DataManager
import co.com.mjbarrerab.zemogaapptest.data.network.remote.APIService
import co.com.mjbarrerab.zemogaapptest.data.network.remote.factory.Factory
import dagger.Module
import dagger.Provides

/**
 * Created by miller.barrera on 14/10/2019.
 */
@Module
class NetworkModule {

    @Provides
    fun latamAPIService(): APIService {
        return Factory.create()
    }

    @Provides
    fun provideDataManager(): DataManager {
        return DataManager(latamAPIService())
    }


}
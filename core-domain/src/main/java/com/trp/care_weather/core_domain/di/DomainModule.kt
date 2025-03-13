package com.trp.care_weather.core_domain.di

import com.trp.care_weather.core_domain.GetDailyWeatherNetworkUseCase
import com.trp.care_weather.core_domain.GetDailyWeatherUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {
    
    @Singleton
    @Binds
    fun bindsGetDailyWeatherUseCase(
        getDailyWeatherUseCase: GetDailyWeatherNetworkUseCase
    ): GetDailyWeatherUseCase

}
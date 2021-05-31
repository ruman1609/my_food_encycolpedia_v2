package com.rudyrachman16.core.di

import androidx.room.Room
import com.rudyrachman16.core.BuildConfig
import com.rudyrachman16.core.data.MealRepositories
import com.rudyrachman16.core.data.api.RetrofitGetData
import com.rudyrachman16.core.data.api.retrofit.ApiService
import com.rudyrachman16.core.data.db.RoomGetData
import com.rudyrachman16.core.data.db.room.MealDB
import com.rudyrachman16.core.domain.repository.IMealRepositories
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidContext(), MealDB::class.java, "MealDB"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<MealDB>().getDao() }
}

val apiModule = module {
    single {
        OkHttpClient.Builder().apply {
            addInterceptor(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
        }.build()
    }
    single {
        Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(get())
        }.build().create(ApiService::class.java)
    }
}

val repositoriesModule = module {
    single { RetrofitGetData(get()) }
    single { RoomGetData(get()) }
    single<IMealRepositories> {
        MealRepositories(get(), get())
    }
}
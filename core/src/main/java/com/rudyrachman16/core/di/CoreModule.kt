package com.rudyrachman16.core.di

import androidx.room.Room
import com.rudyrachman16.core.BuildConfig
import com.rudyrachman16.core.data.MealRepositories
import com.rudyrachman16.core.data.api.RetrofitGetData
import com.rudyrachman16.core.data.api.retrofit.ApiService
import com.rudyrachman16.core.data.db.RoomGetData
import com.rudyrachman16.core.data.db.room.MealDB
import com.rudyrachman16.core.domain.repository.IMealRepositories
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dbModule = module {
    single {
        val passphrase: ByteArray =
            SQLiteDatabase.getBytes("Rudy Rachman Dicoding Submission 2".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(), MealDB::class.java, "MealDB"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
    single { get<MealDB>().getDao() }
}

val apiModule = module {
    single {
        val certificate = CertificatePinner.Builder()
            .add(BuildConfig.BASE_URL, "sha256/pz7CjjOO6yeiHWrcJ+RWljKC2pBYw+9O7XwRIl7HLn8=")
            .build()
        OkHttpClient.Builder().apply {
            addInterceptor(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                else HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            certificatePinner(certificate)
        }.build()
    }
    single {
        Retrofit.Builder().apply {
            baseUrl("https://${BuildConfig.BASE_URL}/api/json/v1/1/")
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
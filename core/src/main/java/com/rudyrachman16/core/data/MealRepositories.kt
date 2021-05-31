package com.rudyrachman16.core.data

import com.rudyrachman16.core.data.api.RetrofitGetData
import com.rudyrachman16.core.data.api.response.MealResponse
import com.rudyrachman16.core.data.api.retrofit.ApiStatus
import com.rudyrachman16.core.data.db.RoomGetData
import com.rudyrachman16.core.domain.model.Meal
import com.rudyrachman16.core.domain.repository.IMealRepositories
import com.rudyrachman16.core.utils.MapVal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class MealRepositories(
    private val db: RoomGetData, private val api: RetrofitGetData
) : IMealRepositories {

    companion object {
        @Volatile
        var FIRST_REC: Boolean = true

        @Volatile
        var FIRST_DET: MutableMap<String, Boolean> = mutableMapOf()
    }

    override fun getRecommended(): Flow<Status<List<Meal>>> =
        object : NetworkBoundRes<List<MealResponse>, List<Meal>>() {
            override fun loadDB(): Flow<List<Meal>> =
                db.getRecommended().map { MapVal.mealEntToDom(it) }

            override fun shouldFetch(db: List<Meal>?): Boolean =
                if (FIRST_REC || db == null || db.isEmpty()) {
                    FIRST_REC = false
                    true
                } else false

            override suspend fun apiCall(): Flow<ApiStatus<List<MealResponse>>> =
                api.getCategories()

            override suspend fun saveCallResult(data: List<MealResponse>, result: List<Meal>?) {
                db.insertToDB(MapVal.mealResToEnt(data, MapVal.mealDomToEnt(result)))
            }
        }.asFlow()

    override fun getDetail(id: String): Flow<Status<Meal>> =
        object : NetworkBoundRes<MealResponse, Meal>() {
            override fun loadDB(): Flow<Meal> = db.getDetail(id).map { MapVal.mealEntToDom(it) }

            override fun shouldFetch(db: Meal?): Boolean =
                if (db?.ingredients == null || db.measurements == null || FIRST_DET[db.idMeal] == null) {
                    FIRST_DET[db!!.idMeal] = false
                    true
                } else false

            override suspend fun apiCall(): Flow<ApiStatus<MealResponse>> = api.getDetail(id)

            override suspend fun saveCallResult(data: MealResponse, result: Meal?) {
                db.insertToDB(MapVal.mealResToEnt(data, MapVal.mealDomToEnt(result!!)))
            }
        }.asFlow()

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun getSearch(state: StateFlow<String>): Flow<List<Meal?>?> =
        state.debounce(300).distinctUntilChanged().mapLatest {
            api.getSearch(it).meals?.map { mr ->
                MapVal.mealResToEnt(
                    mr, db.getDetailOrNull(mr.idMeal!!).first()
                )
            }?.map { mt -> MapVal.mealEntToDom(mt) }
        }.flowOn(Dispatchers.IO)

    override fun getFavorite(): Flow<List<Meal>> = db.getFavorite().map { MapVal.mealEntToDom(it) }

    override suspend fun insertMeal(meal: Meal) = db.insertToDB(MapVal.mealDomToEnt(meal))

    override suspend fun updateMeal(meal: Meal) = db.updateMeal(MapVal.mealDomToEnt(meal))
}
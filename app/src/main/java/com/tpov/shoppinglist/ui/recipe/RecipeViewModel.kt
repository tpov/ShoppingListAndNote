package com.tpov.shoppinglist.ui.recipe

import android.util.Log
import androidx.lifecycle.*
import com.tpov.shoppinglist.ConvertorApiDB
import com.tpov.shoppinglist.api.ApiFactory
import com.tpov.shoppinglist.db.MainDatabase
import com.tpov.shoppinglist.entities.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import okhttp3.Response

@InternalCoroutinesApi
class RecipeViewModel(database: MainDatabase) : ViewModel() {
    private val dao = database.getDao()
    private val apiService = ApiFactory.apiService
    private var checkDateApi = false
    private var checkNotDateApi = false
    private lateinit var apiList: Response
    private var network = true
    private lateinit var liveDataDao: LiveData<List<ShopingListName>>
    private var idListName = 0

    var allItemsFromList = MutableLiveData<List<TableRecipe>>()

    fun loadRecipe() = viewModelScope.launch {
        if (network) {
            var apiList = apiService.getFullPriceList()

            Log.d("RecipeActivity", "loadDB.")
            ConvertorApiDB().apiToTable(apiList).forEach { item ->
                Log.d("RecipeActivity", "Загружаем в бд.")
                when (item) {
                    is TableRecipe -> {
                        insertTableRecipe(item)
                    }
                    is EntityRecipe -> {
                        insertEntityRecipe(item)
                    }
                    is EntityRecipeAnalyzedInstruction -> {
                        insertEntityRecipeAnalyzedInstruction(item)
                    }
                    is EntityRecipeStep -> {
                        insertEntityRecipeStep(item)
                    }
                    is EntityRecipeEquipment -> {
                        insertEntityRecipeEquipment(item)
                    }
                    is EntityRecipeIngredient -> {
                        insertEntityRecipeIngredient(item)
                    }
                    is EntityRecipeCuisines -> {
                        insertEntityRecipeCuisines(item)
                    }
                    is EntityRecipeDiets -> {
                        insertEntityRecipeDiets(item)
                    }
                    is EntityRecipeDishTypes -> {
                        insertEntityRecipeDishTypes(item)
                    }
                    is EntityRecipeExtendedIngredient -> {
                        insertEntityRecipeExtendedIngredient(item)
                    }
                    is EntityRecipeMeta -> {
                        insertEntityRecipeMeta(item)
                    }
                    is EntityRecipeOccasions -> {
                        insertEntityRecipeOccasions(item)
                    }
                    is EntityRecipeMeasures -> {
                        insertEntityRecipeMeasures(item)
                    }
                    is EntityRecipeMetric -> {
                        insertEntityRecipeMetric(item)
                    }
                    is EntityRecipeUs -> {
                        insertEntityRecipeUs(item)
                    }
                    is EntityRecipeLength -> {
                        insertEntityRecipeLength(item)
                    }
                }
            }
        }
    }

    private fun insertEntityRecipeCuisines(item: EntityRecipeCuisines) = viewModelScope.launch {
        dao.insertTableRecipe(item)
    }
    private fun insertEntityRecipeMeasures(item: EntityRecipeMeasures) = viewModelScope.launch {
        dao.insertEntityRecipe(item)
    }
    private fun insertEntityRecipeOccasions(item: EntityRecipeOccasions) = viewModelScope.launch {
        dao.insertEntityRecipeAnalyzedInstruction(item)
    }
    private fun insertEntityRecipeUs(item: EntityRecipeUs) = viewModelScope.launch {
        dao.insertEntityRecipeStep(item)
    }
    private fun insertEntityRecipeDiets(item: EntityRecipeDiets) = viewModelScope.launch {
        dao.insertEntityRecipeEquipment(item)
    }
    private fun insertEntityRecipeMetric(item: EntityRecipeMetric) = viewModelScope.launch {
        dao.insertEntityRecipeIngredient(item)
    }
    private fun insertEntityRecipeLength(item: EntityRecipeLength) = viewModelScope.launch {
        dao.insertEntityRecipeCuisines(item)
    }
    private fun insertEntityRecipeMeta(item: EntityRecipeMeta) = viewModelScope.launch {
        dao.insertEntityRecipeDiets(item)
    }
    private fun insertEntityRecipeDishTypes(item: EntityRecipeDishTypes) = viewModelScope.launch {
        dao.insertEntityRecipeDishTypes(item)
    }
    private fun insertEntityRecipeIngredient(item: EntityRecipeIngredient) = viewModelScope.launch {
        dao.insertEntityRecipeExtendedIngredient(item)
    }
    private fun insertEntityRecipeExtendedIngredient(item: EntityRecipeExtendedIngredient) = viewModelScope.launch {
        dao.insertEntityRecipeMeta(item)
    }
    private fun insertEntityRecipeEquipment(item: EntityRecipeEquipment) = viewModelScope.launch {
        dao.insertEntityRecipeOccasions(item)
    }
    private fun insertEntityRecipeStep(item: EntityRecipeStep) = viewModelScope.launch {
        dao.insertEntityRecipeMeasures(item)
    }
    private fun insertEntityRecipeAnalyzedInstruction(item: EntityRecipeAnalyzedInstruction) = viewModelScope.launch {
        dao.insertEntityRecipeMetric(item)
    }
    private fun insertEntityRecipe(item: EntityRecipe) = viewModelScope.launch {
        dao.insertEntityRecipeUs(item)
    }
    private fun insertTableRecipe(item: TableRecipe) = viewModelScope.launch {
        dao.insertEntityRecipeLength(item)
    }
    fun addShopListProducts(name: String?, products: MutableMap<String, String>, date: String) = viewModelScope.launch {
        Log.d("RecipeViewModel", "addShopListProduct.")
        var nameShopList = ShopingListName(null, name!!, date, 0, 0, "")
        dao.insertShopListName(nameShopList)

    }

    fun getShopItemRroducts(): LiveData<List<ShopingListName>> {
        Log.d("RecipeViewModel", "getShopListProducts.")
        return dao.getAllShopListNames().asLiveData()
    }
    fun insertListItem(shopListItem: ShopingListItem) = viewModelScope.launch {
        Log.d("RecipeViewModel", "insertListItem")
        dao.insertItem(shopListItem)
    }

    fun getEntityRecipe(id: Int): LiveData<List<EntityRecipe>> {
        return dao.getEntityRecipe(id).asLiveData()
    }
    fun getEntityRecipeAnalyzedInstruction(id: Int): LiveData<List<EntityRecipeAnalyzedInstruction>> {
        return dao.getEntityRecipeAnalyzedInstruction(id).asLiveData()
    }
    fun getEntityRecipeStep(id: Int): LiveData<List<EntityRecipeStep>> {
        return dao.getEntityRecipeStep(id).asLiveData()
    }
    fun getEntityRecipeEquipment(id: Int): LiveData<List<EntityRecipeEquipment>> {
        return dao.getEntityRecipeEquipment(id).asLiveData()
    }
    fun getEntityRecipeIngredient(id: Int): LiveData<List<EntityRecipeIngredient>> {
        return dao.getEntityRecipeIngredient(id).asLiveData()
    }
    fun getEntityRecipeCuisines(id: Int): LiveData<List<EntityRecipeCuisines>> {
        return dao.getEntityRecipeCuisines(id).asLiveData()
    }
    fun getEntityRecipeDiets(id: Int): LiveData<List<EntityRecipeDiets>> {
        return dao.getEntityRecipeDiets(id).asLiveData()
    }
    fun getEntityRecipeDishTypes(id: Int): LiveData<List<EntityRecipeDishTypes>> {
        return dao.getEntityRecipeDishTypes(id).asLiveData()
    }
    fun getEntityRecipeExtendedIngredient(id: Int): LiveData<List<EntityRecipeExtendedIngredient>> {
        return dao.getEntityRecipeExtendedIngredient(id).asLiveData()
    }
    fun getEntityRecipeMeta(id: Int): LiveData<List<EntityRecipeMeta>> {
        return dao.getEntityRecipeMeta(id).asLiveData()
    }
    fun getEntityRecipeOccasions(id: Int): LiveData<List<EntityRecipeOccasions>> {
        return dao.getEntityRecipeOccasions(id).asLiveData()
    }
    fun getEntityRecipeMeasures(id: Int): LiveData<List<EntityRecipeMeasures>> {
        return dao.getEntityRecipeMeasures(id).asLiveData()
    }
    fun getEntityRecipeMetric(id: Int): LiveData<List<EntityRecipeMetric>> {
        return dao.getEntityRecipeMetric(id).asLiveData()
    }
    fun getEntityRecipeUs(id: Int): LiveData<List<EntityRecipeUs>> {
        return dao.getEntityRecipeUs(id).asLiveData()
    }
    fun getEntityRecipeLength(id: Int): LiveData<List<EntityRecipeLength>> {
        return dao.getEntityRecipeLength(id).asLiveData()
    }

    fun getAllItemsFromList(date: String) = viewModelScope.launch {
        Log.d("RecipeActivity", "dao.getAllRecipe(date)")
        allItemsFromList.postValue(dao.getAllRecipe(date))
    }

    fun updateTableRecipe(item: TableRecipe) = viewModelScope.launch {
        dao.updateTableRecipe(item)
    }



    class MainViewModelFactory(private val database: MainDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
                @Suppress("UNCHECKED.CAST")
                return RecipeViewModel(database) as T
            }
            throw IllegalAccessException("Unknown ViewModelClass")
        }
    }



}
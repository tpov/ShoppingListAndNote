package com.tpov.shoppinglist.db

import androidx.room.*
import androidx.room.Dao
import com.tpov.shoppinglist.entities.*
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertNode(note: NoteItem)
    @Insert
    suspend fun insertShopListName(note: ShopingListName)
    @Insert
    suspend fun insertItem(note: ShopingListItem)
    @Insert
    suspend fun insertItemLibrary(libraryItem: LibraryItem)
    @Insert
    suspend fun insertCrimeNewQuiz(name: CrimeNewQuiz)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTableRecipe(item: EntityRecipeCuisines)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeStep(item: EntityRecipeUs)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeAnalyzedInstruction(item: EntityRecipeOccasions)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeEquipment(item: EntityRecipeDiets)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipe(item: EntityRecipeMeasures)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeIngredient(item: EntityRecipeMetric)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeCuisines(item: EntityRecipeLength)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeDiets(item: EntityRecipeMeta)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeExtendedIngredient(item: EntityRecipeIngredient)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeMeta(item: EntityRecipeExtendedIngredient)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeDishTypes(item: EntityRecipeDishTypes)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeOccasions(item: EntityRecipeEquipment)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeMeasures(item: EntityRecipeStep)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeMetric(item: EntityRecipeAnalyzedInstruction)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeUs(item: EntityRecipe)
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEntityRecipeLength(item: TableRecipe)


    @Query("SELECT * FROM noteList")
    fun getAllNotes() : Flow<List<NoteItem>>
    @Query("SELECT * FROM shoping_list_names")
    fun getAllShopListNames() : Flow<List<ShopingListName>>
    @Query("SELECT * FROM shop_list_item WHERE listId LIKE :listId")
    fun getAllShopListItem(listId: Int) : Flow<List<ShopingListItem>>
    @Query("SELECT * FROM library WHERE name LIKE :name")
    suspend fun getAllLibraryItems(name: String) :List<LibraryItem>
    @Query("SELECT * FROM new_user_table")
    suspend fun getAllIdQuestion() : List<CrimeNewQuiz>

    @Query("SELECT * FROM entity_recipe WHERE date LIKE :date")
    suspend fun getAllRecipe(date: String): List<TableRecipe>
    @Query("SELECT * FROM entity_recipe WHERE id LIKE :id")
    suspend fun getTableRecipe(id: Int): List<TableRecipe>
    @Query("SELECT * FROM entity_recipe_recipe WHERE id LIKE :id")
    fun getEntityRecipe(id: Int): Flow<List<EntityRecipe>>
    @Query("SELECT * FROM entity_recipe_AnalyzedInstruction WHERE id LIKE :id ")
    fun getEntityRecipeAnalyzedInstruction(id: Int): Flow<List<EntityRecipeAnalyzedInstruction>>
    @Query("SELECT * FROM entity_recipe_Step WHERE id LIKE :id")
    fun getEntityRecipeStep(id: Int): Flow<List<EntityRecipeStep>>
    @Query("SELECT * FROM entity_recipe_Equipment WHERE id LIKE :id")
    fun getEntityRecipeEquipment(id: Int): Flow<List<EntityRecipeEquipment>>
    @Query("SELECT * FROM entity_recipe_Ingredient WHERE id LIKE :id")
    fun getEntityRecipeIngredient(id: Int): Flow<List<EntityRecipeIngredient>>
    @Query("SELECT * FROM entity_recipe_Cuisines WHERE id LIKE :id")
    fun getEntityRecipeCuisines(id: Int): Flow<List<EntityRecipeCuisines>>
    @Query("SELECT * FROM entity_recipe_Diets WHERE id LIKE :id")
    fun getEntityRecipeDiets(id: Int): Flow<List<EntityRecipeDiets>>
    @Query("SELECT * FROM entity_recipe_DishTypes WHERE id LIKE :id")
    fun getEntityRecipeDishTypes(id: Int): Flow<List<EntityRecipeDishTypes>>
    @Query("SELECT * FROM entity_recipe_ExtendedIngredient WHERE id LIKE :id")
    fun getEntityRecipeExtendedIngredient(id: Int): Flow<List<EntityRecipeExtendedIngredient>>
    @Query("SELECT * FROM entity_recipe_Meta WHERE id LIKE :id")
    fun getEntityRecipeMeta(id: Int): Flow<List<EntityRecipeMeta>>
    @Query("SELECT * FROM entity_recipe_Occasions WHERE id LIKE :id")
    fun getEntityRecipeOccasions(id: Int): Flow<List<EntityRecipeOccasions>>
    @Query("SELECT * FROM entity_recipe_Measures WHERE id LIKE :id")
    fun getEntityRecipeMeasures(id: Int): Flow<List<EntityRecipeMeasures>>
    @Query("SELECT * FROM entity_recipe_Metric WHERE id LIKE :id")
    fun getEntityRecipeMetric(id: Int): Flow<List<EntityRecipeMetric>>
    @Query("SELECT * FROM entity_recipe_Us WHERE id LIKE :id")
    fun getEntityRecipeUs(id: Int): Flow<List<EntityRecipeUs>>
    @Query("SELECT * FROM entity_recipe_Length WHERE id LIKE :id")
    fun getEntityRecipeLength(id: Int): Flow<List<EntityRecipeLength>>


    @Query("DELETE FROM noteList WHERE id IS :id")
    suspend fun deleteNote(id: Int)
    @Query("DELETE FROM shoping_list_names WHERE id IS :id")
    suspend fun deleteShopList(id: Int)
    @Query("DELETE FROM shop_list_item WHERE listId LIKE :listId")
    suspend fun deleteAllShopListItem(listId: Int)
    @Query("DELETE FROM library WHERE id LIKE :id")
    suspend fun deleteLibraryItem(id: Int)

    @Update
    suspend fun updateNote(note: NoteItem)
    @Update
    suspend fun updateNoteShopListName(note: ShopingListName)
    @Update
    suspend fun updateShopListItem(note: ShopingListItem)
    @Update
    suspend fun updateLibraryItem(note: LibraryItem)

    @Update
    suspend fun updateTableRecipe(item: TableRecipe)
}
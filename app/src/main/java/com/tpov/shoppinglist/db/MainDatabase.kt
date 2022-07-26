package com.tpov.shoppinglist.db

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tpov.shoppinglist.entities.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(
    entities = [
        LibraryItem::class, NoteItem::class,
        ShopingListItem::class, ShopingListName::class,
        CrimeNewQuiz::class,

        TableRecipe::class,
        EntityRecipe::class,
        EntityRecipeAnalyzedInstruction::class,
        EntityRecipeStep::class,
        EntityRecipeEquipment::class,
        EntityRecipeIngredient::class,
        EntityRecipeCuisines::class,
        EntityRecipeDiets::class,
        EntityRecipeDishTypes::class,
        EntityRecipeExtendedIngredient::class,
        EntityRecipeMeta::class,
        EntityRecipeOccasions::class,
        EntityRecipeMeasures::class,
        EntityRecipeMetric::class,
        EntityRecipeUs::class,
        EntityRecipeLength::class
    ],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(
            from = 1,
            to = 2
        )
    ]
)
abstract class MainDatabase : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        @Volatile
        var INSTANCE: MainDatabase? = null

        @InternalCoroutinesApi
        fun getDataBase(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,

                    "shoping_list.db"
                ).build()
                instance
            }
        }
    }
}
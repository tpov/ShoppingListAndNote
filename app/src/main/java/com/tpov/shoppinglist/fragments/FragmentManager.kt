package com.tpov.shoppinglist.fragments

import androidx.appcompat.app.AppCompatActivity
import com.tpov.shoppinglist.R

object FragmentManager {
    var currentFrag: BaseFragment? = null       //Сюда будет сохранятся наш фрагмент

    fun setFragment(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()        //Эта переменная позволяет проводить действия над фрагментами
        transaction.replace(R.id.pliceHolder, newFrag)      //Помещаем туда новый фрагмент
        transaction.commit()                                //Применяем действия над фрагментом
        currentFrag = newFrag
    }

    fun setFragment2(newFrag: BaseFragment, activity: AppCompatActivity) {
        val transaction = activity.supportFragmentManager.beginTransaction()        //Эта переменная позволяет проводить действия над фрагментами
        transaction.replace(R.id.pliceHolger2, newFrag)      //Помещаем туда новый фрагмент
        transaction.commit()                                //Применяем действия над фрагментом
        currentFrag = newFrag
    }
}

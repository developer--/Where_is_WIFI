package com.awesomethings.whereiswifi.database

import com.activeandroid.ActiveAndroid
import com.activeandroid.query.Delete
import com.activeandroid.query.Select
import com.awesomethings.whereiswifi.model.NetworkLocationModel

/**
 * Created by Jemo on 8/11/16.
 */
class DBManager {
    fun getCoordinates() : List<NetworkLocationModel>{
        return Select().all()
        .from(NetworkLocationModel::class.java)
        .execute()
    }

    fun insert(data : List<NetworkLocationModel> ){
        ActiveAndroid.beginTransaction()
        for (item : NetworkLocationModel in data){
            item.save()
        }
        ActiveAndroid.setTransactionSuccessful()
        ActiveAndroid.endTransaction()
    }

    fun deleteAllValues(){
        val list = getCoordinates()
        for (item : NetworkLocationModel in list){
            item.delete()
        }
    }
}
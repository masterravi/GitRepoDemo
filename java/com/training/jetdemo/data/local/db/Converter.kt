package com.training.jetdemo.data.local.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.training.jetdemo.data.model.License
import com.training.jetdemo.data.model.Permissions

class Converter {

    @TypeConverter
    fun LicenseToJson(value: License?): String? {
        value?.let {
            return Gson().toJson(value)
        }?: return null
    }

    @TypeConverter
    fun jsonToLicense(value: String?): License? {
        val objects = Gson().fromJson(value, License::class.java)
        return objects?:null
    }

    @TypeConverter
    fun PermissionToJson(value: Permissions?): String? {
        value?.let {
            return Gson().toJson(value)
        }?: return null
    }

    @TypeConverter
    fun jsonToPermission(value: String?): Permissions? {
        val objects = Gson().fromJson(value, Permissions::class.java)
        return objects?:null
    }
}
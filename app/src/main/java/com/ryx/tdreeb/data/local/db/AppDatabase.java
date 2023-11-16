package com.ryx.tdreeb.data.local.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ryx.tdreeb.data.model.db.User;


@Database(entities = {User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
}

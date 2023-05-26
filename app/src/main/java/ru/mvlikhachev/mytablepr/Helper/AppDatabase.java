package ru.mvlikhachev.mytablepr.Helper;

import androidx.room.Database;
import androidx.room.RoomDatabase;


import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.CartDao;

@Database(entities = {RestoranDomain.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDao cartDao();
}
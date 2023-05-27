package ru.mvlikhachev.mytablepr.Helper;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;


import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.CartDao;

@Database(entities = {RestoranDomain.class}, version = 2)
public abstract class CartDatabase extends RoomDatabase {
    private static CartDatabase instance;

    public abstract CartDao cartDao();

    public static synchronized CartDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CartDatabase.class, "cart_database")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return instance;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Пример: Добавление нового столбца в таблицу
            database.execSQL("ALTER TABLE cart_table ADD COLUMN new_column TEXT");

            // Пример: Создание новой таблицы
            database.execSQL("CREATE TABLE IF NOT EXISTS new_table (id INTEGER PRIMARY KEY, name TEXT)");

            // Пример: Удаление столбца из таблицы
            database.execSQL("CREATE TABLE temp_table (id INTEGER PRIMARY KEY, name TEXT)");
            database.execSQL("INSERT INTO temp_table SELECT id, name FROM cart_table");
            database.execSQL("DROP TABLE cart_table");
            database.execSQL("ALTER TABLE temp_table RENAME TO cart_table");
        }
    };
}

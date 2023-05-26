package ru.mvlikhachev.mytablepr.Helper;

import android.content.Context;
import android.widget.Toast;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;

public class ManagementCart {
    private static final String DB_NAME = "cart_db";
    private AppDatabase database;

    public ManagementCart(Context context) {
        database = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .build();
    }

    public List<RestoranDomain> getListCart() {
        return database.cartDao().getAll();
    }

    public void addToCart(RestoranDomain cartItem) {
        RestoranDomain itemFromDb = database.cartDao().findByTitle(cartItem.getTitle());
        if (itemFromDb == null) {
            database.cartDao().insert(cartItem);
        }
    }
    public void updateCartItem(RestoranDomain cartItem) {
        database.cartDao().update(cartItem);
    }

    public void deleteCartItem(RestoranDomain cartItem) {
        database.cartDao().delete(cartItem);
    }

    public void clearCart() {
        database.cartDao().deleteAll();
    }


    public void removeItem(ArrayList<RestoranDomain> listRestSelected, int adapterPosition, RestoranDomain cartItem) {
        database.cartDao().delete(cartItem);
    }
}
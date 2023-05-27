package ru.mvlikhachev.mytablepr.Helper;

import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;

public class ManagementCart {
    private CartDatabase database;
    private CartListener cartListener;

    public interface CartListener {
        void onCartUpdated();
    }

    public ManagementCart(Context context, CartListener listener) {
        database = Room.databaseBuilder(context, CartDatabase.class, "cart_db")
                .build();
        cartListener = listener;
    }

    public List<RestoranDomain> getListCart() {
        return database.cartDao().getAll();
    }
    public void deleteCartItem(RestoranDomain cartItem) {
        database.cartDao().delete(cartItem);
    }
    public void addToCart(RestoranDomain cartItem) {
        new AddToCartAsyncTask().execute(cartItem);
    }
    public void removeItem(ArrayList<RestoranDomain> list, int position, RestoranDomain item) {
        list.remove(position);
        database.cartDao().delete(item);
    }
    private class AddToCartAsyncTask extends AsyncTask<RestoranDomain, Void, Void> {
        @Override
        protected Void doInBackground(RestoranDomain... cartItems) {
            RestoranDomain cartItem = cartItems[0];
            RestoranDomain itemFromDb = database.cartDao().findByTitle(cartItem.getName());
            if (itemFromDb == null) {
                database.cartDao().insert(cartItem);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            cartListener.onCartUpdated();
        }
    }
}

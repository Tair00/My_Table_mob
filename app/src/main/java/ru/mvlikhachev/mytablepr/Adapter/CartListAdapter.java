package ru.mvlikhachev.mytablepr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.behavior.SwipeDismissBehavior;

import java.util.ArrayList;

import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.ChangeNumberItemsListener;
import ru.mvlikhachev.mytablepr.Helper.ManagementCart;
import ru.mvlikhachev.mytablepr.R;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> implements ManagementCart.CartListener {

    private ArrayList<RestoranDomain> listRestSelected;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;
    private ItemTouchHelper itemTouchHelper;

    public CartListAdapter(ArrayList<RestoranDomain> listRestSelected, Context context,
                           ChangeNumberItemsListener changeNumberItemsListener) {
        this.listRestSelected = listRestSelected;
        managementCart = new ManagementCart(context, this); // Добавлен this в качестве CartListener
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.restoran_item, parent, false);
        return new ViewHolder(inflate);
    }

    public void updateCartList(ArrayList<RestoranDomain> list) {
        this.listRestSelected = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(listRestSelected.get(position).getName());
        holder.feeEachItem.setText(String.valueOf(listRestSelected.get(position).getPrice()));
        holder.grade.setText(String.valueOf(listRestSelected.get(position).getStar()));

        int drawableResourceId = holder.itemView.getContext().getResources()
                .getIdentifier(listRestSelected.get(position).getPicture(), "drawable",
                        holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.pic);

        SwipeDismissBehavior<View> swipe = new SwipeDismissBehavior<>();
        swipe.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_ANY);
        swipe.setListener(new SwipeDismissBehavior.OnDismissListener() {
            @Override
            public void onDismiss(View view) {
                int adapterPosition = holder.getAdapterPosition();
                RestoranDomain removedItem = listRestSelected.get(adapterPosition);
                managementCart.removeItem(listRestSelected, adapterPosition, removedItem);
                listRestSelected.remove(adapterPosition);
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            }


            @Override
            public void onDragStateChanged(int state) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return listRestSelected.size();
    }

    @Override
    public void onCartUpdated() {
        // Обработка обновления корзины
        // Вы можете вызвать методы, которые требуют обновленных данных корзины
    }

    public void deleteItem(int position) {
        managementCart.deleteCartItem(listRestSelected.get(position));
        listRestSelected.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        changeNumberItemsListener.changed();
    }

//    @Override
//    public void onItemMove(int fromPosition, int toPosition) {
//        // Do nothing
//    }
//
//    @Override
//    public void onItemDismiss(int position) {
//        // Do nothing
//    }

    interface ItemTouchHelperAdapter {
        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,feeEachItem;
        ImageView pic;
        TextView num,grade;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.title);
            pic= itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.fee);
            grade = itemView.findViewById(R.id.grade);
//            totalEachItem = itemView.findViewById(R.id.totalEachItem);


        }
    }
}

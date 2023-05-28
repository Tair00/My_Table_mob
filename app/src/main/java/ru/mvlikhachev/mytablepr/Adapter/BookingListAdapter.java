package ru.mvlikhachev.mytablepr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.behavior.SwipeDismissBehavior;

import java.util.ArrayList;

import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.ChangeNumberItemsListener;
import ru.mvlikhachev.mytablepr.Helper.ManagementCart;
import ru.mvlikhachev.mytablepr.R;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {

    private ArrayList<RestoranDomain> listRestSelected;
    private ManagementCart managementCart;
    private ChangeNumberItemsListener changeNumberItemsListener;
    private ItemTouchHelper itemTouchHelper;

    public BookingListAdapter(ArrayList<RestoranDomain> listRestSelected, Context context,
                              ChangeNumberItemsListener changeNumberItemsListener) {
        this.listRestSelected = listRestSelected;
//        managementCart = new ManagementCart(context, (ManagementCart.CartListener) context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item, parent, false);
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
                managementCart.removeItem(listRestSelected, adapterPosition);
                listRestSelected.remove(adapterPosition);
                notifyDataSetChanged();
                changeNumberItemsListener.changed();
            }

            @Override
            public void onDragStateChanged(int state) {

            }
        });

        // Установка поведения SwipeDismissBehavior для элемента списка
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) holder.itemView.getLayoutParams();
        params.setBehavior(swipe);
    }

    @Override
    public int getItemCount() {
        return listRestSelected.size();
    }

    public void deleteItem(int position) {
        managementCart.deleteCartItem(listRestSelected.get(position));
        listRestSelected.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, getItemCount());
        changeNumberItemsListener.changed();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic;
        TextView num, grade;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.fee);
            grade = itemView.findViewById(R.id.grade);
        }
    }
}

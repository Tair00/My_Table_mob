

package ru.mvlikhachev.mytablepr.Adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import ru.mvlikhachev.mytablepr.Activity.ShowDetailActivity;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestoranAdapter extends RecyclerView.Adapter<RestoranAdapter.RestoranViewHolder> {
    private Context context;
    private ArrayList<RestoranDomain> products;

    public RestoranAdapter(Context context) {
        this.context = context;
        this.products = new ArrayList<>();
    }

    public void updateProducts(ArrayList<RestoranDomain> newProducts) {
        products.clear();
        products.addAll(newProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestoranViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.restoran_item, parent, false);
        return new RestoranViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RestoranViewHolder holder, int position) {
        RestoranDomain product = products.get(position);
        holder.productTitles.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));

        Picasso.get().load(product.getPicture()).into(holder.productImage);

        holder.grade.setText(String.valueOf(product.getStar()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        new Pair<View, String>(holder.productImage, "productImage"));
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", product);
                holder.itemView.getContext().startActivity(intent, options.toBundle());
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce_animation);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class RestoranViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productTitles, productPrice;
        TextView grade;

        public RestoranViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.pic);
            productTitles = itemView.findViewById(R.id.title);
            productPrice = itemView.findViewById(R.id.fee);
            grade = itemView.findViewById(R.id.grade);
        }
    }
}

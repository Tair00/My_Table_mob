package ru.mvlikhachev.mytablepr.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


import ru.mvlikhachev.mytablepr.Activity.MainActivity;
import ru.mvlikhachev.mytablepr.Domain.CategoryDomain;
import ru.mvlikhachev.mytablepr.R;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    ArrayList<CategoryDomain> categoryDomains;
    List<CategoryDomain> categories;
    public CategoryAdapter(ArrayList<CategoryDomain> categoryDomains) {
        this.categoryDomains = categoryDomains;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new ViewHolder(inflate);


    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.categoryName.setText(categoryDomains.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.showOrderByCategory(categoryDomains.get(position).getId());
            }
        });
        String picUrl="";
        switch (position){
            case 0:{

                picUrl = "cat_1";
                break;

            }
            case 1:{

                picUrl = "cat_2";
                break;

            }
            case 2:{

                picUrl = "cat_3";
                break;

            }
            case 3:{

                picUrl = "cat_4";
                break;

            }
            case 4:{

                picUrl = "cat_5";
                break;

            }


        }
        int drawableReourceId =holder.itemView.getContext().getResources().getIdentifier(picUrl,"drawable",holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext()).load(drawableReourceId).into(holder.categoryPic);

    }
    @Override
    public int getItemCount(){return categoryDomains.size() ;}

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView categoryName;
        ImageView categoryPic;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            categoryName=itemView.findViewById(R.id.categoryName);
            categoryPic=itemView.findViewById(R.id.categoryPic);
            mainLayout=itemView.findViewById(R.id.mainLayout);
        }
    }
}
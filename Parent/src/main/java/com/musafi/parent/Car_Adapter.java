package com.musafi.parent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Car_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<String> cars;
    private OnItemClickListener mItemClickListener;

    public Car_Adapter(Context context, List<String> cars) {
        this.context = context;
        this.cars = cars;
    }

    public void updateList(ArrayList<String> cars) {
        this.cars = cars;
        notifyDataSetChanged();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.car_list, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            final String car = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.car_name.setText(car);

        }
    }

    @Override
    public int getItemCount() {
        return cars.size();
    }

    private String getItem(int position) {
        return cars.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView car_name;


        public ViewHolder(final View itemView) {
            super(itemView);
            this.car_name = itemView.findViewById(R.id.car_list_txt_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), getItem(getAdapterPosition()));
                }
            });
        }
    }

    public void removeAt(int position) {
        cars.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cars.size());
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String lesson);
    }
}

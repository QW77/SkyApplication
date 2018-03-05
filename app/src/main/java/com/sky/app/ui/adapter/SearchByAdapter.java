package com.sky.app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sky.app.R;
import com.sky.app.bean.Area;

import java.util.List;


/**
 * A custom adapter to use with the RecyclerView widget.
 */
public class SearchByAdapter extends RecyclerView.Adapter {
    protected Context mContext;
    protected List<Area> list;

    public SearchByAdapter(Context ctx, List<Area> list) {
        this.mContext = ctx;
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        holder=new SearchByHolder( View.inflate(mContext, R.layout.item_search_by_space_bottom, null));

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((SearchByHolder)holder).setData(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class SearchByHolder extends RecyclerView.ViewHolder {
        TextView name;

        public SearchByHolder(View itemView) {
            super(itemView);

             name = (TextView) itemView.findViewById(R.id.name);

        }

        public void setData(Area area){
            name.setText(area.getTwo_dir_name());

        }
    }
}

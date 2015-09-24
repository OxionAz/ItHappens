package com.oxionaz.ithappens.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;

import java.util.List;

/**
 * Created by Александр on 22.09.2015.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.CardViewHolder>{
    private List<Story> aNews;

    public StoryAdapter(List<Story> aNews){
        this.aNews = aNews;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position) {
        Story stories = aNews.get(position);
        holder.story.setText(Html.fromHtml(stories.getElementPureHtml()));
        holder.date.setText(stories.getDesc());
    }

    @Override
    public int getItemCount() {
        return aNews.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView story;
        protected TextView date;

        public CardViewHolder (View itemView){
            super(itemView);
            story = (TextView) itemView.findViewById(R.id.name_text);
            date = (TextView) itemView.findViewById(R.id.data_text);
        }
    }
}

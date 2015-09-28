package com.oxionaz.ithappens.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;

import java.util.List;

import io.realm.Realm;

/**
 * Created by Александр on 22.09.2015.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.CardViewHolder>{
    private List<Story> story;
    private Context context;

    public StoryAdapter(List<Story> story, Context context){
        this.story = story;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        final Story stories = story.get(position);
        holder.story.setText(Html.fromHtml(stories.getElementPureHtml()));
        holder.date.setText(stories.getDesc());
        holder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm realm = Realm.getInstance(context);
                realm.beginTransaction();
                stories.setFavorite(true);
                realm.commitTransaction();
            }
        });
    }

    @Override
    public int getItemCount() {
        return story.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        protected TextView story;
        protected TextView date;
        protected Button fav;

        public CardViewHolder (View itemView){
            super(itemView);
            story = (TextView) itemView.findViewById(R.id.name_text);
            date = (TextView) itemView.findViewById(R.id.data_text);
            fav = (Button) itemView.findViewById(R.id.favorite_button);
        }
    }
}

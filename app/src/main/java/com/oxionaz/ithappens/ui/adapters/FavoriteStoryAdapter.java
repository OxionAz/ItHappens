package com.oxionaz.ithappens.ui.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.oxionaz.ithappens.R;
import com.oxionaz.ithappens.database.Story;
import com.oxionaz.ithappens.util.ExpandableTextView;
import java.util.List;
import io.realm.Realm;

public class FavoriteStoryAdapter extends RecyclerView.Adapter<FavoriteStoryAdapter.CardViewHolder>{
    private List<Story> story;
    private Context context;

    public FavoriteStoryAdapter(List<Story> story, Context context){
        this.story = story;
        this.context = context;
    }

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new CardViewHolder(itemView);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(final CardViewHolder holder, final int position) {
        Story stories = story.get(position);
        holder.story.setText(Html.fromHtml(stories.getElementPureHtml()));
        holder.date.setText(stories.getDesc());
        holder.shareStory.setShareStory(stories.getElementPureHtml());
    }

    @Override
    public int getItemCount() {
        return story.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        protected ExpandableTextView story;
        protected TextView date;
        protected Button fav;
        protected Button share;
        protected Share shareStory;

        public CardViewHolder (View itemView){
            super(itemView);
            shareStory = new Share();
            story = (ExpandableTextView) itemView.findViewById(R.id.name_text);
            date = (TextView) itemView.findViewById(R.id.data_text);
            fav = (Button) itemView.findViewById(R.id.favorite_button);
            share = (Button) itemView.findViewById(R.id.share_button);
            share.setOnClickListener(shareStory);
            fav.setOnClickListener(this);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    story.trimText();
                }
            });
        }

        @Override
        public void onClick(View v) {
            removeFromFavorite(getAdapterPosition());
        }
    }

    private class Share implements View.OnClickListener {

        private String shareStory;

        public void setShareStory(String story){
            this.shareStory = story;
        }

        @Override
        public void onClick(View v) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareStory);
            context.startActivity(Intent.createChooser(sharingIntent, "Поделиться"));
        }
    }

    public void removeFromFavorite(int positions){
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        story.get(positions).setFavorite(false);
        realm.commitTransaction();
        notifyItemRemoved(positions);
    }
}
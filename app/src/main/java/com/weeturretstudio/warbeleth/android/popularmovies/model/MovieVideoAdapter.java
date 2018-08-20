package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.weeturretstudio.warbeleth.android.popularmovies.R;
import java.util.ArrayList;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.VideoViewHolder> {
    private static final String TAG = MovieVideoAdapter.class.getSimpleName();

    private ArrayList<MovieVideo> movieVideos;

    public MovieVideoAdapter(ArrayList<MovieVideo> videos) {
        this.setVideos(videos);
    }

    public void setVideos(ArrayList<MovieVideo> videos) {
        movieVideos = videos;
        this.notifyDataSetChanged();
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        TextView mName;
        TextView mSite;
        TextView mKey;

        public VideoViewHolder(View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.video_view_name);
            mSite = itemView.findViewById(R.id.video_view_site);
            mKey = itemView.findViewById(R.id.video_view_key);
        }

        public void SetupViewHolder(String name, String site, String key) {
            mName.setText(name);
            mSite.setText(site);
            mKey.setText(key);
        }
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForVideo = R.layout.movie_video_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediately = false;

        View view = inflater.inflate(layoutIdForVideo, parent,
                                    shouldAttachImmediately);
        VideoViewHolder viewHolder = new VideoViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.SetupViewHolder(movieVideos.get(position).getmName(),
                                movieVideos.get(position).getmSite(),
                                movieVideos.get(position).getmKey());
    }

    @Override
    public int getItemCount() {
        if(null == movieVideos)
            return 0;

        return movieVideos.size();
    }


}

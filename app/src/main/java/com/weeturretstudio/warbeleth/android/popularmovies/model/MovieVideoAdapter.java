package com.weeturretstudio.warbeleth.android.popularmovies.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weeturretstudio.warbeleth.android.popularmovies.R;

import java.util.ArrayList;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.VideoViewHolder>
                               implements View.OnClickListener {
    private static final String TAG = MovieVideoAdapter.class.getSimpleName();

    private static final String SITE_YOUTUBE = "YouTube";
    private ArrayList<MovieVideo> movieVideos;

    public MovieVideoAdapter(ArrayList<MovieVideo> videos) {
        this.setVideos(videos);
    }

    public void setVideos(ArrayList<MovieVideo> videos) {
        movieVideos = videos;
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Log.v(TAG, "View: " + v.toString());
        RecyclerView parent = (RecyclerView) v.getParent();
        int index = parent.getChildAdapterPosition(v);

        MovieVideo selectedVideo = movieVideos.get(index);
        if(selectedVideo.getSite().equals(SITE_YOUTUBE)) {
            Intent youtubeIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("vnd.youtube://"+selectedVideo.getKey()));

            parent.getContext().startActivity(youtubeIntent);
        }
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

        view.setOnClickListener(this);

        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        holder.SetupViewHolder(movieVideos.get(position).getName(),
                                movieVideos.get(position).getSite(),
                                movieVideos.get(position).getKey());
    }

    @Override
    public int getItemCount() {
        if(null == movieVideos)
            return 0;

        return movieVideos.size();
    }


}

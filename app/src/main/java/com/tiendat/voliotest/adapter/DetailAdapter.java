package com.tiendat.voliotest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tiendat.voliotest.activity.DetailActivity;
import com.tiendat.voliotest.api.DetailContent;
import com.tiendat.voliotest.api.DetailData;
import com.tiendat.voliotest.databinding.ItemDetailTypeFourBinding;
import com.tiendat.voliotest.databinding.ItemDetailTypeOneBinding;
import com.tiendat.voliotest.databinding.ItemDetailTypeThreeBinding;
import com.tiendat.voliotest.databinding.ItemDetailTypeTwoBinding;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder>{

    private final Context context;
    private final DetailData data;
    private final int parentWidth;
    private final DetailActivity activity;

    private final int TEXT_TYPE = 1;
    private final int VIDEO_TYPE = 2;
    private final int IMG_TYPE = 3;
    private final int TITLE_TYPE = 4;

    public DetailAdapter(Context context , DetailData data , int parentWidth , DetailActivity activity){
        this.data = data;
        this.context = context;
        this.parentWidth = parentWidth;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TEXT_TYPE:
                ItemDetailTypeOneBinding bindingText = ItemDetailTypeOneBinding
                        .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
                return new TextViewHolder(bindingText);
            case VIDEO_TYPE:
                ItemDetailTypeTwoBinding bindingVideo = ItemDetailTypeTwoBinding
                        .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
                return new VideoViewHolder(bindingVideo);
            case IMG_TYPE:
                ItemDetailTypeThreeBinding bindingImg = ItemDetailTypeThreeBinding
                        .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
                return new ImgViewHolder(bindingImg);
            default:
                ItemDetailTypeFourBinding bindingTitle = ItemDetailTypeFourBinding
                        .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
                return new TitleViewHolder(bindingTitle);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DetailContent content = data.getSections().get(position).getContent();
        switch (holder.getItemViewType()){
            case TITLE_TYPE:
                StringBuilder builder = new StringBuilder(content.getDatePublish());
                builder.setCharAt(10 , ' ');
                TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
                titleViewHolder.description.setText(content.getDescription());
                titleViewHolder.title.setText(data.getTitle());
                titleViewHolder.publisherName.setText(data.getPublisher().getName());
                titleViewHolder.publishDate.setText(builder.toString());
                break;
            case TEXT_TYPE:
                final SpannableStringBuilder text = new SpannableStringBuilder(content.getText());

                for (int i = 0 ; i < content.getMarkUps().size() ; i++){
                    if (content.getMarkUps().get(i).getType() == 4){
                        int finalI = i;
                        ClickableSpan clickable = new ClickableSpan() {
                            @Override
                            public void onClick(View textView) {
                                // show toast here
                                Toast.makeText(context ,
                                        content.getMarkUps().get(finalI).getHref(),
                                        Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void updateDrawState(TextPaint ds) {
                                super.updateDrawState(ds);
                                ds.setUnderlineText(true);

                            }
                        };
                        text.setSpan(clickable ,
                                content.getMarkUps().get(i).getStart() ,
                                content.getMarkUps().get(i).getEnd() ,
                                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }else {
                        final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                        text.setSpan(bss,
                                content.getMarkUps().get(i).getStart(),
                                content.getMarkUps().get(i).getEnd() + 1,
                                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                    }
                }
                TextViewHolder textViewHolder = (TextViewHolder) holder;
                textViewHolder.text.setText(text);
                textViewHolder.text.setMovementMethod(LinkMovementMethod.getInstance());
                textViewHolder.text.setHighlightColor(Color.TRANSPARENT);
                break;
            case VIDEO_TYPE:
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.caption.setText(content.getCaption());
                videoViewHolder.duration.setText(getStringDuration(content.getDuration()));
                Glide.with(context).load(content.getPrevImg().getHref()).diskCacheStrategy(DiskCacheStrategy.NONE).into(videoViewHolder.thumbVideo);
                videoViewHolder.videoView.setVideoURI(Uri.parse(content.getHref()));
                videoViewHolder.playButton.setVisibility(View.INVISIBLE);
                videoViewHolder.loadCircle.setVisibility(View.VISIBLE);
                videoViewHolder.frameLayout.setVisibility(View.VISIBLE);
                videoViewHolder.linearLayout.setOnClickListener(v -> {
                    if (videoViewHolder.customMediaController.getVisibility() == View.VISIBLE){
                        videoViewHolder.customMediaController.setVisibility(View.INVISIBLE);
                    }else {
                        videoViewHolder.customMediaController.setVisibility(View.VISIBLE);
                    }
                });
                videoViewHolder.stopButton.setOnClickListener(v -> {
                    videoViewHolder.videoView.pause();
                    videoViewHolder.frameLayout.setVisibility(View.VISIBLE);
                });
                videoViewHolder.videoView.setOnPreparedListener(mp -> {
                    int height = parentWidth * mp.getVideoHeight() / mp.getVideoWidth();
                    videoViewHolder.container.setLayoutParams(new LinearLayout.LayoutParams(parentWidth , height));
                    videoViewHolder.durationTime.setText(getStringDuration(videoViewHolder.videoView.getDuration()));
                    videoViewHolder.loadCircle.setVisibility(View.GONE);
                    videoViewHolder.playButton.setVisibility(View.VISIBLE);
                    videoViewHolder.playButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            playVideo(videoViewHolder);
                        }
                    });
                    videoViewHolder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            videoViewHolder.frameLayout.setVisibility(View.VISIBLE);
                            videoViewHolder.loadCircle.setVisibility(View.GONE);
                            videoViewHolder.customMediaController.setVisibility(View.GONE);
                        }
                    });
                    videoViewHolder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            int position1 = progress * videoViewHolder.videoView.getDuration() / 100;
                            videoViewHolder.currentTime.setText(getStringDuration(position1));
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                            videoViewHolder.isUpdate = false;
                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            Log.d("TAG1432", "onStartTrackingTouch: stop");
                            int position1 = seekBar.getProgress() * videoViewHolder.videoView.getDuration() / 100;
                            videoViewHolder.videoView.seekTo(position1);
                            videoViewHolder.videoView.start();
                            videoViewHolder.isUpdate = true;
                        }
                    });
                });
                break;
            case IMG_TYPE:
                ImgViewHolder imgViewHolder = (ImgViewHolder) holder;
                imgViewHolder.caption.setText(content.getCaption());
                int height = parentWidth * content.getOriginalHeight() / content.getOriginalWidth();
                imgViewHolder.imageView.setLayoutParams(new LinearLayout.LayoutParams(parentWidth , height));
                Glide.with(context).load(content.getHref()).diskCacheStrategy(DiskCacheStrategy.NONE).into(imgViewHolder.imageView);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return data.getSections().size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.getSections().get(position).getType();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class TitleViewHolder extends DetailAdapter.ViewHolder{

        TextView title , publisherName , publishDate , description;

        public TitleViewHolder(ItemDetailTypeFourBinding binding) {
            super(binding.getRoot());
            title = binding.title;
            publishDate = binding.publishDate;
            publisherName = binding.publisherName;
            description = binding.descrption;
        }
    }

    public class TextViewHolder extends DetailAdapter.ViewHolder{
        TextView text;
        public TextViewHolder(ItemDetailTypeOneBinding binding) {
            super(binding.getRoot());
            text = binding.text;
        }
    }

    public class VideoViewHolder extends DetailAdapter.ViewHolder{

        TextView caption , duration , currentTime , durationTime;
        ImageView thumbVideo ;
        VideoView videoView;
        FrameLayout container , frameLayout , customMediaController;
        ImageButton playButton , stopButton;
        ProgressBar loadCircle;
        LinearLayout linearLayout;
        SeekBar seekBar;
        boolean isUpdate = true;


        public VideoViewHolder(ItemDetailTypeTwoBinding binding) {
            super(binding.getRoot());
            caption = binding.caption;
            duration = binding.duration;
            thumbVideo = binding.videoThumbnail;
            videoView = binding.videoView;
            container = binding.videoContainer;
            frameLayout = binding.frameLayout;
            playButton = binding.playButton;
            loadCircle = binding.loadCircle;
            linearLayout = binding.mLinearLayout;
            seekBar = binding.customSeekbar;
            currentTime = binding.currentTime;
            durationTime = binding.durationTime;
            customMediaController = binding.customMediaController;
            stopButton = binding.customStopButton;
        }
    }

    public class ImgViewHolder extends DetailAdapter.ViewHolder{

        ImageView imageView;
        TextView caption;

        public ImgViewHolder(ItemDetailTypeThreeBinding binding) {
            super(binding.getRoot());
            imageView = binding.image;
            caption = binding.caption;
        }
    }

    @SuppressLint("DefaultLocale")
    private String getStringDuration(int duration){
        int min = duration / 60000;
        int sec = (duration % 60000) / 1000;
        return String.format("%02d" , min) + ":" + String.format("%02d" , sec);
    }

    private void playVideo(VideoViewHolder videoViewHolder){
        videoViewHolder.customMediaController.setVisibility(View.GONE);
        videoViewHolder.frameLayout.setVisibility(View.GONE);
        videoViewHolder.videoView.start();
        new Thread(()->{
            while (videoViewHolder.videoView.isPlaying()){
                int progress = (int) ((float) videoViewHolder.videoView.getCurrentPosition() / (float) videoViewHolder.videoView.getDuration() * (float) 100);
                if (videoViewHolder.isUpdate){
                    videoViewHolder.seekBar.setProgress(progress);
                    activity.runOnUiThread(()->{
                        videoViewHolder.currentTime.setText(getStringDuration(videoViewHolder.videoView.getCurrentPosition()));
                    });
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}

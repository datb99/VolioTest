package com.tiendat.voliotest.adapter;

import android.annotation.SuppressLint;
import android.content.Context;

import android.content.Intent;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tiendat.voliotest.R;
import com.tiendat.voliotest.activity.DetailActivity;
import com.tiendat.voliotest.api.Item;
import com.tiendat.voliotest.databinding.ItemTypeFourBinding;
import com.tiendat.voliotest.databinding.ItemTypeOneBinding;
import com.tiendat.voliotest.databinding.ItemTypeThreeBinding;
import com.tiendat.voliotest.databinding.ItemTypeTwoBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ItemPostAdapter extends RecyclerView.Adapter<ItemPostAdapter.ViewHolder>{

    private final Context context;
    private final ArrayList<Item> items;
    private final int parentWidth;

    private final int TYPE_ONE = 1;
    private final int TYPE_TWO = 2;
    private final int TYPE_THREE = 3;
    private final int TYPE_FOUR = 4;

    public ItemPostAdapter(Context context ,ArrayList<Item> items , int parentWidth){
        this.context = context;
        this.items = items;
        this.parentWidth = parentWidth;
    }



    @NonNull
    @Override
    public ItemPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_TWO:
                ItemTypeTwoBinding bindingTwo = ItemTypeTwoBinding
                        .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
                return new ViewHolderTypeTwo(bindingTwo);
            case TYPE_THREE:
                ItemTypeThreeBinding bindingThree = ItemTypeThreeBinding
                        .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
                return new ViewHolderTypeThree(bindingThree);
            case TYPE_FOUR:
                ItemTypeFourBinding bindingFour = ItemTypeFourBinding
                        .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
                return new ViewHolderTypeFour(bindingFour);
            default:
                ItemTypeOneBinding bindingOne = ItemTypeOneBinding
                        .inflate(LayoutInflater.from(parent.getContext()) , parent , false);
                return new ViewHolderTypeOne(bindingOne);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ItemPostAdapter.ViewHolder holder, int position) {
        Item item = items.get(position);
        switch (holder.getItemViewType()){
            case TYPE_ONE:
                ViewHolderTypeOne viewHolderTypeOne = (ViewHolderTypeOne)holder;
                viewHolderTypeOne.title.setText(item.getTitle());
                viewHolderTypeOne.name.setText(item.getPublisher().getName());
                viewHolderTypeOne.date.setText(getTimeString(item.getPublishDate()));
                if (item.getImages() != null){
                    int imgCount = item.getImages().size();
                    if (imgCount > 3){
                        imgCount = 3;
                    }
                    for (int i = 0 ; i < imgCount ; i ++){
                        int width = parentWidth / imgCount - 8;
                        int height = width * 3 / 4;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width , height);
                        params.setMargins(4 , 0 , 4 , 0);
                        viewHolderTypeOne.imageViews.get(i).setLayoutParams(params);
                        viewHolderTypeOne.imageViews.get(i).setImageResource(android.R.color.transparent);
                        viewHolderTypeOne.imageViews.get(i).setVisibility(View.VISIBLE);
                        Glide.with(context).load(item.getImages().get(i).getHref())
                                .diskCacheStrategy(DiskCacheStrategy.NONE)
                                .into(viewHolderTypeOne.imageViews.get(i));
                    }
                }
                viewHolderTypeOne.title.setOnClickListener(v -> goToDetail());
                break;
            case TYPE_TWO:
                ViewHolderTypeTwo viewHolderTypeTwo = (ViewHolderTypeTwo) holder;
                viewHolderTypeTwo.title.setText(item.getTitle());
                viewHolderTypeTwo.name.setText(item.getPublisher().getName());
                viewHolderTypeTwo.duration.setText(getStringDuration(item.getContent().getDuration()));
                viewHolderTypeTwo.date.setText(getTimeString(item.getPublishDate()));
                int height =  parentWidth * item.getAvatar().getWidth() / item.getAvatar().getHeight();
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(parentWidth, height);
                viewHolderTypeTwo.thumbVideo.setLayoutParams(params);
                Glide.with(context).load(item.getContent().getImage().getHref())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .into(viewHolderTypeTwo.thumbVideo);
                viewHolderTypeTwo.title.setOnClickListener(v -> goToDetail());
                break;
            case TYPE_THREE:
                ViewHolderTypeThree viewHolderTypeThree = (ViewHolderTypeThree) holder;
                viewHolderTypeThree.title.setText(item.getTitle());
                viewHolderTypeThree.name.setText(item.getPublisher().getName());
                viewHolderTypeThree.date.setText(getTimeString(item.getPublishDate()));
                viewHolderTypeThree.imageView.setImageDrawable (null);
                if (item.getAvatar() != null){
                    Glide.with(context).load(item.getAvatar().getHref())
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                            .into(viewHolderTypeThree.imageView);
                }else {
                    Glide.with(context).load(AppCompatResources.getDrawable(context , R.drawable.covid)).into(viewHolderTypeThree.imageView);
                }
                viewHolderTypeThree.title.setOnClickListener(v -> goToDetail());
                break;
            case TYPE_FOUR:
                ViewHolderTypeFour viewHolderTypeFour = (ViewHolderTypeFour) holder;
                viewHolderTypeFour.title.setText(item.getTitle());
                viewHolderTypeFour.name.setText(item.getPublisher().getName());
                viewHolderTypeFour.date.setText(getTimeString(item.getPublishDate()));
                ArrayList<String> imageUrls = new ArrayList<>();
                viewHolderTypeFour.recyclerView.setLayoutParams(new LinearLayout.LayoutParams(parentWidth , parentWidth / 4));
                for (int i = 0 ; i < item.getImages().size() ; i++){
                    imageUrls.add(item.getImages().get(i).getHref());
                }
                viewHolderTypeFour.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL , false));
                viewHolderTypeFour.recyclerView.setAdapter(new ItemTypeFourAdapter(context , imageUrls , parentWidth));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (items.get(position).getContentType()){
            case "video":
                return TYPE_TWO;
            case "overview":
                return TYPE_THREE;
        }
        if (items.get(position).getImages() == null){
            return TYPE_THREE;
        }
        if (items.get(position).getImages().size() > 3){
            return TYPE_FOUR;
        }
        return TYPE_ONE;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class ViewHolderTypeOne extends ViewHolder{

        TextView title , date , name;
        ArrayList<ImageView> imageViews = new ArrayList<>();

        public ViewHolderTypeOne(ItemTypeOneBinding binding) {
            super(binding.getRoot());
            title = binding.titleTextView;
            date = binding.publisherDateTextView;
            name = binding.publisherNameTextView;
            imageViews.add(binding.img1);
            imageViews.add(binding.img2);
            imageViews.add(binding.img3);
        }
    }

    public class ViewHolderTypeTwo extends ViewHolder{

        TextView title , date , name , duration;
        ImageView thumbVideo;

        public ViewHolderTypeTwo(ItemTypeTwoBinding binding) {
            super(binding.getRoot());
            title = binding.titleTextView;
            date = binding.publisherDateTextView;
            name = binding.publisherNameTextView;
            thumbVideo = binding.videoThumbnail;
            duration = binding.duration;
        }
    }

    public class ViewHolderTypeThree extends ViewHolder{

        TextView title , date , name ;
        ImageView imageView;

        public ViewHolderTypeThree(ItemTypeThreeBinding binding) {
            super(binding.getRoot());
            title = binding.titleTextView;
            date = binding.publisherDateTextView;
            name = binding.publisherNameTextView;
            imageView = binding.imageView;
        }
    }

    public class ViewHolderTypeFour extends ViewHolder{

        RecyclerView recyclerView;
        TextView title , date , name;
        ItemTypeFourAdapter adapter;

        public ViewHolderTypeFour(ItemTypeFourBinding binding) {
            super(binding.getRoot());
            recyclerView = binding.recycleView;
            title = binding.titleTextView;
            date = binding.publisherDateTextView;
            name = binding.publisherNameTextView;
        }
    }

    private String getTimeString(String date){
        String result = "";
        ArrayList<Integer> itemDate = new ArrayList<>();
        ArrayList<Integer> currentDate = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        int[] max = {0 , 12 , 30 , 24 , 60 , 60};
        String itemDateString = date.split("T" )[0];
        String itemTimeString = date.split("T" )[1].split("Z")[0];
        itemDate.add(Integer.parseInt(itemDateString.split("-")[0]));
        itemDate.add(Integer.parseInt(itemDateString.split("-")[1]));
        itemDate.add(Integer.parseInt(itemDateString.split("-")[2]));
        itemDate.add(Integer.parseInt(itemTimeString.split(":")[0]));
        itemDate.add(Integer.parseInt(itemTimeString.split(":")[1]));
        itemDate.add(Integer.parseInt(itemTimeString.split(":")[2]));
        Date currentTime = Calendar.getInstance().getTime();
        String currentDateString = DateFormat.format("yyyy-MM-dd-HH-mm-ss", currentTime).toString();
        for (int i = 0 ; i < 6 ; i++){
            currentDate.add(Integer.parseInt(currentDateString.split("-")[i]));
        }
        for (int i = itemDate.size() - 1 ; i >= 0; i--){
            if (currentDate.get(i) - itemDate.get(i) >= 0){
                temp.add(currentDate.get(i) - itemDate.get(i));
            }else {
                temp.add(max[i] + currentDate.get(i) - itemDate.get(i));
                currentDate.set(i - 1, currentDate.get(i - 1) - 1);
            }
        }
        for (int i = temp.size() - 1 ; i >= 0 ; i --){
            if (temp.get(i) != 0){
                switch (i){
                    case 5:
                        result = temp.get(i) + " năm trước";
                        break;
                    case 4:
                        result = temp.get(i) + " tháng trước";
                        break;
                    case 3:
                        result = temp.get(i) + " ngày trước";
                        break;
                    case 2:
                        result = temp.get(i) + " giờ trước";
                        break;
                    case 1:
                        result = temp.get(i) + " phút trước";
                        break;
                    case 0:
                        result = temp.get(i) + " giây trước";
                        break;
                }
                break;
            }
        }
        return result;
    }

    @SuppressLint("DefaultLocale")
    private String getStringDuration(int duration){
        int min = duration / 60000;
        int sec = (duration % 60000) / 1000;
        int millis = ((duration % 60000) % 1000) / 10;
        return String.format("%02d" , min) + ":" + String.format("%02d" , sec) + ":" + String.format("%02d" , millis);
    }

    private void goToDetail(){
        Intent intent = new Intent(context , DetailActivity.class);
        context.startActivity(intent);
    }

}

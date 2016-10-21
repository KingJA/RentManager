package com.tdr.wisdome.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.tdr.wisdome.R;
import com.tdr.wisdome.actvitiy.UrlActivity;

import java.util.List;

/**
 * Banner 图片适配器
 * Created by Linus_Xie on 2016/8/16.
 */
public class ImagePagerAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageIdList;
    private List<String> linkUrlArray;
    private List<String> urlTitlesList;
    private int size;
    private boolean isInfiniteLoop;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public ImagePagerAdapter(Context context, List<String> imageIdList,
                             List<String> urllist, List<String> urlTitlesList) {
        this.context = context;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        this.linkUrlArray = urllist;
        this.urlTitlesList = urlTitlesList;
        isInfiniteLoop = false;
        // 初始化imageLoader 否则会报错
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.ic_launcher) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.image_ad8) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.image_ad8) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .build();

    }

    @Override
    public int getCount() {
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = holder.imageView = new ImageView(context);
            holder.imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage(
                (String) this.imageIdList.get(getPosition(position)),
                holder.imageView, options);
        holder.imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String url = linkUrlArray.get(ImagePagerAdapter.this
                        .getPosition(position));
                String title = urlTitlesList.get(ImagePagerAdapter.this
                        .getPosition(position));

                Bundle bundle = new Bundle();
                bundle.putString("url", url);
                bundle.putString("title", title);
                Intent intent = new Intent(context, UrlActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);


            }
        });
        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

}

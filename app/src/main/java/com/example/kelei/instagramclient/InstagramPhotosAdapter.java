package com.example.kelei.instagramclient;

import android.content.Context;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by kelei on 2/21/15.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {

    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        InstagramPhoto photo = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
        }
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        TextView tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
        ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView pPhoto = (ImageView) convertView.findViewById(R.id.pPhoto);

        SpannableString captionString = new SpannableString(photo.username + " " + photo.caption);
        captionString.setSpan(new StyleSpan(Typeface.BOLD), 0, photo.username.length(), 0);
        captionString.setSpan(
            new ForegroundColorSpan(getContext().getResources().getColor(R.color.darkBlue)), 0, photo.username.length(), 0);
        tvCaption.setText(captionString);
        tvCreatedAt.setText(android.text.format.DateUtils.getRelativeTimeSpanString(
            getContext(), photo.createdDate.getTime()));
        tvUsername.setText(photo.username);
        tvLikes.setText(NumberFormat.getNumberInstance(Locale.US).format(photo.likesCount) + " likes");

        Transformation roundTransform = new RoundedTransformationBuilder()
            .cornerRadiusDp(getContext().getResources().getDimension(R.dimen.profile_pic_width))
            .oval(true)
            .build();
        ivPhoto.setImageResource(0);
        Picasso.with(getContext())
            .load(photo.imageUrl)
            .placeholder(R.drawable.default_placeholder)
            .into(ivPhoto);
        pPhoto.setImageResource(0);
        Picasso.with(getContext())
            .load(photo.profilePictureUrl)
            .transform(roundTransform)
            .into(pPhoto);

        return convertView;
    }
}

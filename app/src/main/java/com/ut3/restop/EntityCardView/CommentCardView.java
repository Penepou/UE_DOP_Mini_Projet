package com.ut3.restop.EntityCardView;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ut3.restop.R;

import java.util.List;

public class CommentCardView extends CardView {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private List<ImageView> imageView;
    private RatingBar ratingBar;
    private Button showImagesButton;

    public CommentCardView(Context context) {
        super(context);
        init();
    }

    public CommentCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CommentCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.comment_card_layout, this);
        titleTextView = findViewById(R.id.titleText);
        descriptionTextView = findViewById(R.id.descriptionText);
        showImagesButton = findViewById(R.id.imagesButton);
        ratingBar = findViewById(R.id.note);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(String title) {

        this.titleTextView.setText(title);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void setDescriptionTextView(String description) {
        this.descriptionTextView.setText(description);
    }

    public List<ImageView> getImageView() {
        return imageView;
    }

    public void setImageView(int pos, Bitmap image) {
        ImageView imageView1 = this.imageView.get(pos);
        imageView1.setImageBitmap(image);
        this.imageView.set(pos, imageView1);
    }

    public Button getShowImagesButton() {
        return showImagesButton;
    }

    public void setVisibilityButton(int visibility) {
        this.showImagesButton.setVisibility(visibility);
    }

    public void setListenerButton(View.OnClickListener v){
        this.showImagesButton.setOnClickListener(v);
    }

    public void setOnClickButton(int visibility) {
        this.showImagesButton.setVisibility(visibility);
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public void setRating(float rating) {
        this.ratingBar.setRating(rating);
    }
}

package com.ut3.restop.EntityCardView;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ut3.restop.R;

public class CommentCardView extends CardView {

    private TextView titleTextView;
    private TextView descriptionTextView;
    private ImageView imageView;
    private TextView pseudoTextView;

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
        imageView = findViewById(R.id.comment_image);
        pseudoTextView = findViewById(R.id.pseudoText);
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(String title) {
        this.titleTextView.setText("par " + title);
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public void setDescriptionTextView(String description) {
        this.descriptionTextView.setText(description);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setImageView(Bitmap image) {
        this.imageView.setImageBitmap(image);
    }

    public TextView getPseudoTextView() {
        return pseudoTextView;
    }

    public void setPseudoTextView(String pseudo) {
        this.pseudoTextView.setText(pseudo);
    }
}

package com.ut3.restop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class RestaurantCardView extends CardView {

        private TextView restaurantNameTextView;
        private TextView restaurantPriceTextView;
        private ImageView restaurantImageView;

        public RestaurantCardView(Context context) {
            super(context);
            init();
        }

        public RestaurantCardView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public RestaurantCardView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            inflate(getContext(), R.layout.restaurant_card_layout, this);
            restaurantNameTextView = findViewById(R.id.restaurant_name);
            restaurantPriceTextView = findViewById(R.id.restaurant_price);
            restaurantImageView = findViewById(R.id.restaurant_image);
        }

        public void setRestaurantPrice(String restaurantPrice) {
            restaurantPriceTextView.setText(restaurantPrice);
        }
        public void setRestaurantName(String restaurantName) {
            restaurantNameTextView.setText(restaurantName);
        }

        public void setRestaurantImage(Bitmap image) {
            restaurantImageView.setImageBitmap(image);
        }

    public Bitmap getRestaurantImage() {
        return ((BitmapDrawable) restaurantImageView.getDrawable()).getBitmap();
    }

    public TextView getRestaurantPriceTextView() {
        return restaurantPriceTextView;
    }

    public void setRestaurantPriceTextView(TextView restaurantPriceTextView) {
        this.restaurantPriceTextView = restaurantPriceTextView;
    }
}

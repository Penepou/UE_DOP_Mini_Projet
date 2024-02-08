package com.ut3.restop;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

public class MenuCardView extends CardView {

        private TextView menuNameTextView;
        private TextView menuPriceTextView;
        private ImageView menuImageView;
        private TextView menuIngredientsTextView;

        public MenuCardView(Context context) {
            super(context);
            init();
        }

        public MenuCardView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        public MenuCardView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init();
        }

        private void init() {
            inflate(getContext(), R.layout.menu_card_layout, this);
            menuNameTextView = findViewById(R.id.menu_name);
            menuPriceTextView = findViewById(R.id.menu_price);
            menuImageView = findViewById(R.id.menu_image);
            menuIngredientsTextView = findViewById(R.id.menu_ingredients);
        }

        public void setMenuPrice(String restaurantPrice) {
            menuPriceTextView.setText(restaurantPrice);
        }
        public void setMenuName(String restaurantName) {
            menuNameTextView.setText(restaurantName);
        }

        public void setMenuImage(int resourceId) {
            menuImageView.setImageResource(resourceId);
        }

    public void setMenuIngredients(String menuIngredients) {
        menuIngredientsTextView.setText(menuIngredients);
    }

    public TextView getMenuIngredientsTextView() {
        return menuIngredientsTextView;
    }

    public TextView getMenuPriceTextView() {
        return menuPriceTextView;
    }

    public void setMenuPriceTextView(TextView menuPriceTextView) {
        this.menuPriceTextView = menuPriceTextView;
    }
}

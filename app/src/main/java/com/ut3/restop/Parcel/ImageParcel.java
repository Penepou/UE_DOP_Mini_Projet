package com.ut3.restop.Parcel;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageParcel implements Parcelable {

        private Bitmap image;

        public ImageParcel(Bitmap image) {
            this.image = image;
        }

        protected ImageParcel(Parcel in) {
            image = in.readParcelable(Bitmap.class.getClassLoader());
        }

        public static final Creator<ImageParcel> CREATOR = new Creator<ImageParcel>() {
            @Override
            public ImageParcel createFromParcel(Parcel in) {
                return new ImageParcel(in);
            }

            @Override
            public ImageParcel[] newArray(int size) {
                return new ImageParcel[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(image, flags);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public Bitmap getImage() {
            return image;
        }

}

package com.google.code.easyshopper.activities.product;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.google.code.easyshopper.domain.CartProduct;

public class ProductImageManager implements ImageManager {
	private final CartProduct cartProduct;
	private final Activity activity;
	private final int productsmallpicture;
	private final int noProductImageId;
	boolean hasDrawnImage=false;

	public ProductImageManager(CartProduct cartProduct, Activity activity, int productsmallpicture, int noProductImageId) {
		this.cartProduct = cartProduct;
		this.activity = activity;
		this.productsmallpicture = productsmallpicture;
		this.noProductImageId = noProductImageId;
	}

	public void clean() {
		if (!hasDrawnImage)
			return;
		try {
			BitmapDrawable drawable = (BitmapDrawable) imageView().getDrawable();
			Bitmap bitmap = drawable.getBitmap();
			imageView().setImageBitmap(null);
			bitmap.recycle();
		} catch (Exception e) {

		}
	}


	private ImageView imageView() {
		return (ImageView) activity.findViewById(productsmallpicture);
	}

	public void refresh() {
		clean();
		Bitmap productImage = cartProduct.getProduct().getImage().getSmallBitmap();
		if(productImage==null) {
			imageView().setImageResource(noProductImageId);
			hasDrawnImage=false;
			return;
		}
		imageView().setImageBitmap(productImage);
		hasDrawnImage=true;
	}
}

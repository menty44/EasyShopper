package com.google.code.easyshopper.activities.product.editproduct;

import java.util.Currency;

import android.app.Activity;

import com.google.code.easyshopper.Logger;
import com.google.code.easyshopper.db.PriceDBAdapter;
import com.google.code.easyshopper.db.ProductDBAdapter;
import com.google.code.easyshopper.db.helpers.EasyShopperSqliteOpenHelper;
import com.google.code.easyshopper.domain.CartProduct;
import com.google.code.easyshopper.domain.Price;

public class ProductSaver {

	private final Activity activity;
	private final CartProduct cartProduct;

	public ProductSaver(CartProduct cartProduct, Activity activity) {
		this.cartProduct = cartProduct;
		this.activity = activity;
		
		
	}
	
	public void save(String productName, Currency currency, String priceString) {
		ProductDBAdapter productDBAdapter = new ProductDBAdapter(new EasyShopperSqliteOpenHelper(activity));
		Logger.d(this, "save", "Saving cartproduct: " + cartProduct + ", with name: " + productName);
		productDBAdapter.save(cartProduct.getBarcodeForProduct(), productName, cartProduct.getProduct().getNumberOfPriceCharacters());
		cartProduct.setProduct(productDBAdapter.lookup(cartProduct.getBarcodeForProduct()));
		Price price = cartProduct.getPrice();
		price.setProduct(cartProduct.getProduct());
		price.setMarket(cartProduct.getShopping().getMarket());
		price.getAmount().setCurrency(currency);
		price.getAmount().setFromReadableAmount(priceString);
		new PriceDBAdapter(new EasyShopperSqliteOpenHelper(activity)).saveAndAssociate(price, cartProduct);		
	}
}
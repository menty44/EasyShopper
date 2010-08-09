package com.google.code.easyshopper.activities;

import java.util.Currency;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.code.easyshopper.Logger;
import com.google.code.easyshopper.R;
import com.google.code.easyshopper.db.PriceDBAdapter;
import com.google.code.easyshopper.db.helpers.EasyShopperSqliteOpenHelper;
import com.google.code.easyshopper.domain.CartProduct;
import com.google.code.easyshopper.domain.Market;
import com.google.code.easyshopper.domain.Price;

public class EditPrice implements ESTab {
	private final CartProduct cartProduct;
	private final Activity activity;
//	private ArrayAdapter<CurrencyItem> currencySpinnerAdapter;
	private Spinner currencySpinner;
	private EditText editPrice;
	private final Runnable runOnOK;
	private View view;
	public static final String TAG = "edit_price";

	public EditPrice(Activity activity, CartProduct cartProduct, Runnable runOnOk) {
		this.activity = activity;
		this.cartProduct = cartProduct;
		this.runOnOK = runOnOk;
	}
	
	public View getView() {
//		if(view == null)
//			view = activity.getLayoutInflater().inflate(R.layout.edit_price_dialog, null);
		return view;
	}
	
	public void setup() {
//		this.setTitle(context.getResources().getString(R.string.Shopping_SetProductPrice));
//		setContentView(R.layout.edit_price_dialog);
		Logger.d(this, "setup", "cartProduct: " + cartProduct);
	}
	





	public class Cancel implements android.view.View.OnClickListener {
		public void onClick(View v) {
			// TODO what to do here?
//			SetPriceDialog.this.cancel();
		}

	}

	public void updateValuesOnExit() {
		// TODO Auto-generated method stub
	}
}

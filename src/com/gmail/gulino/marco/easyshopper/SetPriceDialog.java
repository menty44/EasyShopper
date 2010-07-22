package com.gmail.gulino.marco.easyshopper;

import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.gmail.gulino.marco.easyshopper.db.PriceDBAdapter;
import com.gmail.gulino.marco.easyshopper.db.helpers.EasyShopperSqliteOpenHelper;
import com.gmail.gulino.marco.easyshopper.domain.CartProduct;
import com.gmail.gulino.marco.easyshopper.domain.Market;
import com.gmail.gulino.marco.easyshopper.domain.Price;
import com.gmail.gulino.marco.easyshopper.domain.PriceType;

public class SetPriceDialog extends Dialog {
	private final CartProduct cartProduct;
	private final Context context;
	private ArrayAdapter<CurrencyItem> currencySpinnerAdapter;
	private Spinner currencySpinner;
	private EditText editPrice;
	private final Runnable runOnOK;
	private Map<Integer, PriceType> priceTypeMaps;
	public static String[] currencies=new String[]{"EUR", "USD"};

	public SetPriceDialog(Context context, CartProduct cartProduct, Runnable runOnOk) {
		super(context);
		this.context = context;
		this.cartProduct = cartProduct;
		this.runOnOK = runOnOk;
		priceTypeMaps=new HashMap<Integer, PriceType>();
		priceTypeMaps.put(R.id.Price_Type_Unit, PriceType.ByUnit);
		priceTypeMaps.put(R.id.Price_Type_Weight, PriceType.ByWeight);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setTitle(context.getResources().getString(R.string.Shopping_SetProductPrice));
		setContentView(R.layout.edit_price_dialog);
		((TextView) findViewById(R.id.EditPriceDialog_ProductName)).setText(cartProduct.getProduct().getName());
		Market market = cartProduct.getShopping().getMarket();
		((TextView) findViewById(R.id.EditPriceDialog_MarketName)).setText(market.getName());
		
		currencySpinner = (Spinner) findViewById(R.id.EditCurrency);
		currencySpinnerAdapter = new ArrayAdapter<CurrencyItem>(context, android.R.layout.simple_dropdown_item_1line);
		currencySpinner.setAdapter(currencySpinnerAdapter);
		Price currentPrice=cartProduct.getPrice();
		populateCurrencyCombo(currentPrice);
		
		((Button) findViewById(R.id.EditPriceDialog_Cancel)).setOnClickListener(new Cancel());
		((Button) findViewById(R.id.EditPriceDialog_Ok)).setOnClickListener(new SavePrice());
		editPrice = (EditText) findViewById(R.id.EditPrice);
		editPrice.setText(currentPrice!=null?currentPrice.getAmount().getReadableAmount(1):"");
	}
	

	private PriceType checkedType() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.Price_Type);
		return priceTypeMaps.get(radioGroup.getCheckedRadioButtonId());
	}

	private void populateCurrencyCombo(Price currentPrice) {
		CurrencyItem currentCurrency =null;
		currencySpinnerAdapter.clear();
		for (String currency : currencies) {
			CurrencyItem adapter = new CurrencyItem(Currency.getInstance(currency));
			if(currentPrice!=null && currency.equals(currentPrice.getCurrency().getCurrencyCode())) {
				currentCurrency=adapter;
			}
			currencySpinnerAdapter.add(adapter);
		}
		currencySpinner.setSelection(currencySpinnerAdapter.getPosition(currentCurrency));
	}


	public class SavePrice implements android.view.View.OnClickListener {

		public void onClick(View v) {
			Price price =cartProduct.getPrice();
			if(price==null) {
				price=new Price(-1);
			}
			price.setProduct(cartProduct.getProduct());
			price.setMarket(cartProduct.getShopping().getMarket());
			price.getAmount().setCurrency(currencySpinnerAdapter.getItem(currencySpinner.getSelectedItemPosition()).currency);
			price.getAmount().setFromReadableAmount(editPrice.getText().toString());
			price.setPriceType(checkedType() );
			new PriceDBAdapter(new EasyShopperSqliteOpenHelper(context)).saveAndAssociate(price, cartProduct);
			runOnOK.run();
			SetPriceDialog.this.dismiss();
		}

	}
	public class Cancel implements android.view.View.OnClickListener {
		public void onClick(View v) {
			SetPriceDialog.this.cancel();
		}

	}
	public class CurrencyItem {
		public final Currency currency;
		public CurrencyItem(Currency instance) {
			this.currency = instance;
		}
		@Override
		public String toString() {
			return currency.getSymbol();
		}
	}
}

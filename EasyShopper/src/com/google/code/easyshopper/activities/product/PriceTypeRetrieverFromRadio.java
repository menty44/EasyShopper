package com.google.code.easyshopper.activities.product;

import com.google.code.easyshopper.R;
import com.google.code.easyshopper.activities.IRadioGroup;

public class PriceTypeRetrieverFromRadio implements PriceTypeRetriever {
	private final IRadioGroup productPriceType;

	PriceTypeRetrieverFromRadio(IRadioGroup productPriceType) {
		this.productPriceType = productPriceType;
	}

	public boolean priceIsInBarcode() {
		return productPriceType.getCheckedRadioButtonId() == R.id.PriceTypeWeight;
	}

	public int priceBarcodeChars() {
		return priceIsInBarcode() ? 6 : 0;
	}

}

package com.google.code.easyshopper.domain;

import java.util.Currency;

import com.google.code.easyshopper.Logger;

public class Amount {
	private Currency currency;
	private Long amount;

	public Amount() {
		this(null, null);
	}

	public Amount(Long amount, Currency currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public long getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Amount setCurrency(Currency currency) {
		this.currency = currency;
		return this;
	}

	public String getReadableAmount(long quantity) {
		if(amount==null) return "";
		
		long currentAmount = amount * quantity;
		long scaleFactor = scaleFactor();
		long intPart = (long) (currentAmount / scaleFactor);
		long fractPart = (currentAmount - (intPart * scaleFactor));
		Logger.d(this, "getReadableAmount", "amount: " + amount + ", quantity: " + quantity + ", intPart: " + intPart
				+ ", fractPart: " + fractPart);
		String fractPartAsString = String.valueOf(fractPart);
		int defaultFractionDigits = currency.getDefaultFractionDigits();
		Logger.d(this, "getReadableAmount", "fractPartAsString: '" + fractPartAsString + "(" + fractPartAsString.length()  + ")', defaultFractionDigits: "
				+ defaultFractionDigits);
		while(fractPartAsString.length() < defaultFractionDigits) {
			fractPartAsString="0".concat(fractPartAsString);
			Logger.d(this, "getReadableAmount", "adding a 0: fractPartAsString: '" + fractPartAsString  + "(" + fractPartAsString.length()  + ")', defaultFractionDigits: "
					+ defaultFractionDigits);
		}
		return "" + intPart + getSeparator() + fractPartAsString.substring(0, defaultFractionDigits);
	}

	public Amount setFromReadableAmount(String amount) {
		if(amount.equals("")) {
			this.amount=null;
			return this;
		}
		if (amount.indexOf(getSeparator()) < 0) {
			this.amount = Long.parseLong(amount) * scaleFactor();
			return this;
		}
		int separatorIndex = amount.indexOf(getSeparator());
		long intPart = Long.parseLong(amount.substring(0, separatorIndex)) * scaleFactor();
		String originalDecimalPart = amount.substring(separatorIndex + 1, amount.length());
		long decimalPart = Long.parseLong(originalDecimalPart.concat("00000").substring(0,
				currency.getDefaultFractionDigits()));
		this.amount = intPart + decimalPart;
		return this;
	}

	private long scaleFactor() {
		return (long) Math.pow(10, currency.getDefaultFractionDigits());
	}

	public char getSeparator() {
		return '.';
		// DecimalFormat decimalFormat = (DecimalFormat)
		// DecimalFormat.getInstance(Locale.getDefault());
		// return decimalFormat.getDecimalFormatSymbols().getDecimalSeparator();
	}

	@Override
	public String toString() {
		return this.getClass().getName() + " { amount=" + amount + ", currency=" + currency + " }";
	}

	public String getReadableAmountLabel(long quantity) {
		return getReadableAmount(quantity) + " " + currency.getSymbol();
	}
	
	@Override
	public boolean equals(Object o) {
		if( o==null || ! (o instanceof Amount) ) return false;
		Amount other = ((Amount)o);
		return other.amount == amount && other.currency.equals(currency);
	}
}

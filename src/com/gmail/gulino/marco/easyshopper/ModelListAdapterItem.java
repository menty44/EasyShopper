package com.gmail.gulino.marco.easyshopper;

public abstract class ModelListAdapterItem {
	public abstract void executeOnClick();
	public boolean executeOnLongClick() {
		return false;
	}
	protected abstract String label();
	
	@Override
	public String toString() {
		return label();
	}
}

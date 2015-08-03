package com.appnativa.rare.ui.text;

public class HTMLCharSequence implements CharSequence{
	protected String text;
	protected Object attributedText;
	
	public HTMLCharSequence(String text, Object attributedText) {
		super();
		this.text = text;
		this.attributedText = attributedText;
	}

	@Override
	public char charAt(int index) {
		// TODO Auto-generated method stub
		return text.charAt(index);
	}

	@Override
	public int length() {
		return text.length();
	}

	public Object getAttributedText() {
		return attributedText;
	}
	@Override
	public CharSequence subSequence(int beginIndex, int endIndex) {
		return text.substring(beginIndex, endIndex);
	}
	@Override
  public String toString() {
		return text;
	}
	

}

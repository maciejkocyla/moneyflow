package org.mf.parser.plugins;

import org.mf.model.Transaction;
import org.mf.parser.ParserPlugin;

public class CashParser extends ParserPlugin {

	@Override
	public String evaluate(String text, Transaction transaction) {
		String[] splits = text.split(" ");
		for(String s : splits){
			Double value = null;
			try{
				s.replace(",", ".");
				value = Double.parseDouble(s);
			}catch(NumberFormatException e){
				
			}
			if(value!=null){
				transaction.setAmount(value);
				return text.replace(s+" ", "");
			}			
		}
		return text;

	}

	@Override
	public int getPriority() {
		return 0;
	}

}

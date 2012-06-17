package org.mf.parser.plugins;

import org.mf.model.Transaction;
import org.mf.parser.ParserPlugin;;

public class DateParser extends ParserPlugin{

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String evaluate(String text, Transaction transaction) {
		// TODO Auto-generated method stub
		return text;
	}

}

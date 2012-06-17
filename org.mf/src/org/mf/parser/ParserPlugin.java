package org.mf.parser;

import org.mf.model.Transaction;

public abstract class ParserPlugin implements Comparable<ParserPlugin>{
	
	public abstract int getPriority();
	public abstract String evaluate(String text, Transaction transaction);
	
	
	public int compareTo(ParserPlugin pp){
		return pp.getPriority()-this.getPriority();
	}
}

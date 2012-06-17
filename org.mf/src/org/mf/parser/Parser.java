package org.mf.parser;

import org.mf.model.Transaction;

public abstract class Parser {
	
	public Transaction parse(String value){
		Transaction tr = create(value);
		try{
			validate(tr);
		}catch(RuntimeException e){
			e.printStackTrace();
			throw e;
		}
		return tr;
	}
	
	protected abstract void validate(Transaction transaction) throws RuntimeException;
	
	protected abstract Transaction create(String text);
	
	
}

package org.mf.parser;

import java.util.LinkedList;
import java.util.List;

import org.mf.model.Account;
import org.mf.model.IAccountsGetter;
import org.mf.model.Transaction;
import org.mf.parser.plugins.AccountParser;
import org.mf.parser.plugins.CashParser;

public class ParserImpl extends Parser{

	private List<ParserPlugin> plugins;
	
	public ParserImpl(IAccountsGetter accGetter){
		
		plugins = new LinkedList<ParserPlugin>();
		
		plugins.add(new AccountParser(accGetter));
		plugins.add(new CashParser());
		System.out.println(plugins);
	}

	@Override
	protected void validate(Transaction transaction) {
		if(transaction.getFrom()==null){
			throw new RuntimeException("From category of transaction can't be null.");
		} else if(transaction.getAmount()==null){
			throw new RuntimeException("Amount of transaction can't be null.");
		} else if(transaction.getTo()==null){
			throw new RuntimeException("To category of transaction can't be null.");
		} else if(!(transaction.getFrom() instanceof Account) && !(transaction.getTo() instanceof Account)){
			throw new RuntimeException("Can't create transaction without account");
		} else if(transaction.getFrom().equals(transaction.getTo())){
			throw new RuntimeException("Can't create transaction to the same point");
		}
		
	}

	@Override
	protected Transaction create(String value) {
		Transaction tr = new Transaction();
		for(ParserPlugin pp : plugins){
			System.out.print(value + " -> ");
			value = pp.evaluate(value,tr);
			System.out.println(value);
		}
		return tr;
	}

	

}

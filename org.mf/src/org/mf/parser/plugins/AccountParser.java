package org.mf.parser.plugins;

import java.util.Set;

import org.mf.model.Account;
import org.mf.model.Category;
import org.mf.model.IAccountsGetter;
import org.mf.model.Transaction;
import org.mf.model.sqlite.AccountsDataSource;
import org.mf.parser.ParserPlugin;

public class AccountParser extends ParserPlugin{

	private IAccountsGetter accountGetter;
	public AccountParser(IAccountsGetter accountGetter){
		this.accountGetter = accountGetter;
	}
	
	public String evaluate(String text,Transaction tr){
		String[] splits = text.split(" ");
		for(int i=0;i<splits.length;++i){			
			if(splits[i].equalsIgnoreCase("from")){
				if(splits.length>=i+1){
					Account acc = getAccount(splits[++i]);
					if(acc!=null){
						tr.setFrom(acc);
						
					}else{
						tr.setFrom(new Category(splits[i]));
					}
					text = text.replace(splits[i-1]+" "+splits[i],"");
					continue;
				}
			}
			
			if(splits[i].equalsIgnoreCase("to")){
				if(splits.length>=i+1){
					Account acc = getAccount(splits[++i]);
					if(acc!=null){
						tr.setTo(acc);
						text = text.replace(splits[i-1]+" "+splits[i],"");
					}else{
						tr.setTo(new Category(splits[i]));
					}
					text = text.replace(splits[i-1]+" "+splits[i],"");
				}
			}			
		}
		return text;
	}

	private Account getAccount(String string) {
		
		for(Account acc : accountGetter.getAllAccounts()){
			if(acc.getName().equalsIgnoreCase(string))
				return acc;
		}
		return null;
	}

	@Override
	public int getPriority() {
		return 0;
	}
}

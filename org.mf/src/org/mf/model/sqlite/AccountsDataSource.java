package org.mf.model.sqlite;

import java.util.ArrayList;
import java.util.List;

import org.mf.model.Account;
import org.mf.model.IAccountsGetter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class AccountsDataSource implements IAccountsGetter {
	// Database fields
		private SQLiteDatabase database;
		private MySQLiteHelper dbHelper;
		private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
				MySQLiteHelper.COLUMN_ACCOUNT,MySQLiteHelper.ACCOUNT_NAME };

		public AccountsDataSource(Context context) {
			dbHelper = new MySQLiteHelper(context);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		public Account createAccount(Float account,String name) {
			ContentValues values = new ContentValues();
			values.put(MySQLiteHelper.COLUMN_ACCOUNT, account);
			values.put(MySQLiteHelper.ACCOUNT_NAME, name);
			long insertId = database.insert(MySQLiteHelper.TABLE_ACCOUNTS, null,
					values);
			Cursor cursor = database.query(MySQLiteHelper.TABLE_ACCOUNTS,
					allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
			cursor.moveToFirst();
			Account newAccount = cursorToAccount(cursor);
			cursor.close();
			return newAccount;
		}

		public void deleteAccount(Account account) {
			long id = account.getId();
			System.out.println("Comment deleted with id: " + id);
			database.delete(MySQLiteHelper.TABLE_ACCOUNTS, MySQLiteHelper.COLUMN_ID
					+ " = " + id, null);
		}

		public List<Account> getAllAccounts() {
			List<Account> accounts = new ArrayList<Account>();

			Cursor cursor = database.query(MySQLiteHelper.TABLE_ACCOUNTS,
					allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Account account = cursorToAccount(cursor);
				accounts.add(account);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return accounts;
		}

		private Account cursorToAccount(Cursor cursor) {
			Account account = new Account(0,"name");
			account.setId(cursor.getLong(0));
			account.setAmount(cursor.getDouble(1));
			account.setName(cursor.getString(2));
			return account;
		}

}

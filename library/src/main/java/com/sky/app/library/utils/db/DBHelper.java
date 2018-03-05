package com.sky.app.library.utils.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.sky.app.library.utils.db.impl.DBTableImpl;

/**
 * 创建数据库
 * @author wjx
 *
 */
public class DBHelper extends SQLiteOpenHelper {

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		try {
			// 保存登录的用户信息
			db.execSQL("create table if not exists " + DBTableImpl.TABLENAME
					+ "(_id integer primary key autoincrement, "
					+ DBTableImpl.USERNAME + " String, "
					+ DBTableImpl.PASSWORD + " String) ");
		}catch(Exception e){
			e.printStackTrace();
		}
		updateTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		updateTable(db);
	}
	
	/**
	 * 更新表
	 * @param db
	 */
	private void updateTable(SQLiteDatabase db){
		try{
			//添加时间字段
			/*db.execSQL("alter table " + DBTableImpl.TABLENAME + " add " +
					DBTableImpl.TIME + " integer ");*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
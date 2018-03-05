package com.sky.app.library.utils.db.impl;

import android.content.Context;

import com.sky.app.library.utils.db.DBHelper;

/**
 * 数据库操作（sqlLite）
 * @author wjx
 *
 */
public class DBTableImpl {

	public static final String TABLENAME = "userlogin";	//用户登录表
	public static final String USERNAME = "username";	//用户名
	public static final String PASSWORD = "password";	//密码
	private DBHelper dbHelper = null;
	private static DBTableImpl userlogininfodao;

	private DBTableImpl(Context context) {
		dbHelper = new DBHelper(context, "userlogin.db", null, 1);
	}

	public static DBTableImpl getInstance(Context context) {
		if (null == userlogininfodao) {
			synchronized (DBTableImpl.class){
				if (null == userlogininfodao){
					userlogininfodao = new DBTableImpl(context);
				}
			}
		}
		return userlogininfodao;
	}

	/**
	 * 查询用户登录信息
	 * @return
	 */
	/*public List<UserInfo> getUserLoginInfo() {
		List<UserInfo> userLogininfos = null;
		SQLiteDatabase r_db = dbHelper.getReadableDatabase();
		if (r_db.isOpen()) {
			Cursor cursor = r_db.rawQuery("select * from " + TABLENAME + " order by " + TIME + " desc ", null);
			userLogininfos = new ArrayList<UserInfo>();
			UserInfo user = null;
			String username;
			String password;
			String lineid;
			String gamename;
			while(cursor.moveToNext()){
				user = new UserInfo();
				username = cursor.getString(cursor.getColumnIndex(USERNAME));
				password = cursor.getString(cursor.getColumnIndex(PASSWORD));
				lineid = cursor.getString(cursor.getColumnIndex(LINEID));
				gamename = cursor.getString(cursor.getColumnIndex(GMAENAME));
				if (null == username || "null".equalsIgnoreCase(username)){
					username = "";
				}
				if (null == password || "null".equalsIgnoreCase(password)){
					password = "";
				}
				if (null == lineid || "null".equalsIgnoreCase(lineid)){
					lineid = "";
				}
				if (null == gamename || "null".equalsIgnoreCase(gamename)){
					gamename = "";
				}
				user.setUsername(username);
				user.setPassword(password);
				user.setLoginImeiId(lineid);
				user.setGamename(gamename);
				userLogininfos.add(user);
				user = null;
			}
			cursor.close();
		}
		r_db.close();
		r_db = null;
		return userLogininfos;
	}*/

	/**
	 * 保存用户信息
	 * @param username
	 * @param pwd
	 */
	/*public void saveUserLoginInfo(String username, String pwd, Object lineid,
			Object gamename) {
		if (null == username) username = "";
		if (null == pwd) pwd = "";
		if (null == lineid) lineid = "";
		if (null == gamename) gamename = "";
		SQLiteDatabase w_db = dbHelper.getWritableDatabase();
		if (w_db.isOpen()) {
			w_db.execSQL(" insert into " + TABLENAME + "(" + USERNAME + ","
					+ PASSWORD + "," + TIME + "," + LINEID + "," + GMAENAME 
					+ ") values(?, ?, ?, ?, ?)",
					new Object[] {username, pwd, (int)(System.currentTimeMillis()/1000),
					lineid, gamename});
		}
		w_db.close();
		w_db = null;
	}*/
	
	/**
	 * 批量保存数据
	 * @param sqls
	 * @param dataList
	 */
	/*public void saveDataBatch(List<Map<String, Object>> dataList) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			if (db.isOpen()){
				for (Map<String, Object> rowMap: dataList) {
					db.execSQL(" insert into " + TABLENAME + "(" + USERNAME + ","
							+ PASSWORD + "," + TIME + "," + LINEID + "," + GMAENAME 
							+ ") values(?, ?, ?, ?, ?)", 
							new Object[]{rowMap.get("username"), "", 
							rowMap.get("time"), rowMap.get("id"), 
							rowMap.get("gamename")});
				}
			}
			// 设置事务标志为成功，当结束事务时就会提交事务
			db.setTransactionSuccessful();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 结束事务
			db.endTransaction();
			db.close();
			db = null;
		}
	}*/

	/**
	 * 通过用户名查找用户信息
	 * @param username
	 * @return true
	 */
	/*public boolean findUserLoginInfoByName(String username) {
		boolean flag = false;
		SQLiteDatabase r_db = dbHelper.getReadableDatabase();
		if (r_db.isOpen()) {
			Cursor cursor = r_db.rawQuery(" select * from " + TABLENAME
					+ " where " + USERNAME + " = ? ", new String[] { username });
			if (cursor.moveToNext()) {
				flag = true;
			}
			cursor.close();
			cursor = null;
		}
		r_db.close();
		r_db = null;
		return flag;
	}*/

	/**
	 * 删除对应的用户信息
	 * @param username
	 */
	/*public void deleteUserLoginByName(String username) {
		SQLiteDatabase w_db = dbHelper.getWritableDatabase();
		if (w_db.isOpen()) {
			w_db.execSQL("delete from " + TABLENAME + " where " + USERNAME
					+ " = ? ", new Object[] { username });
		}
		w_db.close();
		w_db = null;
	}*/
	
	/**
	 * 根据用户信息更新用户信息
	 * @param username
	 */
	/*public void updateUserLoginByName(String username, String password, Object lineid,
			Object gamename){
		if (null == username) username = "";
		if (null == password) password = "";
		if (null == lineid) lineid = "";
		if (null == gamename) gamename = "";
		SQLiteDatabase w_db = dbHelper.getWritableDatabase();
		if (w_db.isOpen()){
			w_db.execSQL(" update " + TABLENAME + " set " + DBTableImpl.PASSWORD + " = ?, " +
					DBTableImpl.TIME + " = ?, " +
					DBTableImpl.GMAENAME + " = ?, " +
					DBTableImpl.LINEID + " = ? " +
					" where " + DBTableImpl.USERNAME + " = ? " ,
					new Object[]{password, (int)(System.currentTimeMillis()/1000), gamename, 
					lineid, username});
		}
		w_db.close();
		w_db = null;
	}*/
}
package com.sky.app.library.utils;

import android.content.Context;

/**
 * 根据资源的名字获取其ID值
 * @author wjx
 */
public class MResource {
	
	/**
	 * 获取资源 id
	 * @param context
	 * @param className
	 * @param name
	 * @return
	 */
	public static int getIdByName(Context context, String className, String name) {
		int id = 0;
		try{
			id = context.getResources().getIdentifier(name, 
					className, context.getPackageName());
		}catch(Exception e){
			e.printStackTrace();
		}
		return id;
	}
}
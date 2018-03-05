package com.sky.app.library.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 用来处理从网络上获得json格式数据进行转换
 * @author wjx
 *
 */
public class JsonUtil {
	
	/**
	 * json字符串转Map
	 * @param json
	 * @return
	 */
	public static Map<String,Object> jsonToMap(String json){
		Map<String, Object> map = null;
		try{
			JSONObject jsonObject = getJSONByStr(json);
			map = m(jsonObject);
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 字符串转json （转换失败返回空json对象）
	 * @param str
	 * @return
	 * @throws JSONException 
	 */
	public static JSONObject getJSONByStr(String str) throws JSONException{
		return new JSONObject(str);
	}
	
	private static Map<String,Object> m(JSONObject jsonObj) throws JSONException{
		Map<String, Object> valueMap = new HashMap<String, Object>();
		Iterator<String> keyIter = jsonObj.keys();
		while (keyIter.hasNext()) {
			String key = keyIter.next();
			Object value = jsonObj.get(key);
			if(value instanceof JSONObject){
				valueMap.put(key, m((JSONObject)value));
			}else if(value instanceof JSONArray){
				valueMap.put(key, l((JSONArray)value));
			}else{
				valueMap.put(key, value);
			}
		}
		return valueMap;
	}
	
	/**
	 * 有些数据格式会丢失
	 * @param arrjson
	 * @return
	 * @throws JSONException 
	 */
	private static List<Object> l(JSONArray arrjson) throws JSONException{
		List<Object> valueList = new ArrayList<Object>();
		for (int i = 0; i < arrjson.length(); i++) {
			Object value = arrjson.get(i);
			if(value instanceof JSONObject){
				valueList.add(m((JSONObject)value));
			}else if(value instanceof JSONArray){
				valueList.add(l((JSONArray)value));
			}else{
				valueList.add(value);
			}
		}
		return valueList;
	}
	
	/**
	 * Map<> 转成 Json
	 * @return
	 */
	public static JSONObject getJsonFromMap(Map<String, Object> map) {
		JSONObject jsonObject = new JSONObject();

		try {
			Iterator<String> keyIter = map.keySet().iterator();
			String key;
			while (keyIter.hasNext()) {
				key = keyIter.next();
				jsonObject.put(key, map.get(key));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
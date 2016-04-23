package com.robin.lazy.util.json;

import com.robin.lazy.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * json工具
 * 
 * @author 江钰锋
 * @version [版本号, 2014年6月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class JSONObjectUtils {

	public static boolean isPrintException = true;

	/**
	 * 获取对象
	 * 
	 * @param <V>
	 * 
	 * @param jsonOb
	 * @param key
	 * @param clazz
	 */
	@SuppressWarnings("unchecked")
	public static <V extends Object> V getValue(JSONObject jsonOb, String key,
			Class<V> clazz) {
		V v = null;
		try {
			if (String.class.isAssignableFrom(clazz)) {
				v = (V) getString(jsonOb, key, "");
			} else if (Integer.class.isAssignableFrom(clazz)
					|| int.class.isAssignableFrom(clazz)) {
				v = (V) getInt(jsonOb, key, Integer.valueOf(0));
			} else if (Double.class.isAssignableFrom(clazz)
					|| double.class.isAssignableFrom(clazz)) {
				v = (V) getDouble(jsonOb, key, Double.valueOf(0));
			} else if (Long.class.isAssignableFrom(clazz)
					|| long.class.isAssignableFrom(clazz)) {
				v = (V) getLong(jsonOb, key, Long.valueOf(0));
			} else if (Boolean.class.isAssignableFrom(clazz)) {
				v = (V) getBoolean(jsonOb, key, Boolean.valueOf(false));
			} else if (!Collection.class.isAssignableFrom(clazz)
					&& Object.class.isAssignableFrom(clazz)) {
				v = (V) getJSONObject(jsonOb, key, null);
			} else if (Collection.class.isAssignableFrom(clazz)) {
				v = (V) getJSONArray(jsonOb, key, null);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return v;
	}

	/**
	 * 是否为基本的数据类型
	 * 
	 * @param field
	 * @return
	 */
	public static boolean isBaseDateType(Field field) {
		Class<?> clazz = field.getType();
		return clazz.equals(String.class) || clazz.equals(Integer.class)
				|| clazz.equals(Byte.class) || clazz.equals(Long.class)
				|| clazz.equals(Double.class) || clazz.equals(Float.class)
				|| clazz.equals(Character.class) || clazz.equals(Short.class)
				|| clazz.equals(Boolean.class) || clazz.equals(Date.class)
				|| clazz.equals(Date.class)
				|| clazz.equals(java.sql.Date.class) || clazz.isPrimitive();
	}

	/**
	 * get Long from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getLong(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getLong(String)}</li>
	 *         </ul>
	 */
	public static Long getLong(JSONObject jsonObject, String key,
			Long defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getLong(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Long from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#getLong(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static Long getLong(String jsonData, String key, Long defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getLong(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONObjectUtils#getLong(JSONObject, String, Long)
	 */
	public static long getLong(JSONObject jsonObject, String key,
			long defaultValue) {
		return getLong(jsonObject, key, (Long) defaultValue);
	}

	/**
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONObjectUtils#getLong(String, String, Long)
	 */
	public static long getLong(String jsonData, String key, long defaultValue) {
		return getLong(jsonData, key, (Long) defaultValue);
	}

	/**
	 * get Int from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getInt(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getInt(String)}</li>
	 *         </ul>
	 */
	public static Integer getInt(JSONObject jsonObject, String key,
			Integer defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getInt(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Int from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#getInt(JSONObject, String, JSONObject)}</li>
	 *         </ul>
	 */
	public static Integer getInt(String jsonData, String key,
			Integer defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getInt(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONObjectUtils#getInt(JSONObject, String, Integer)
	 */
	public static int getInt(JSONObject jsonObject, String key, int defaultValue) {
		return getInt(jsonObject, key, (Integer) defaultValue);
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONObjectUtils#getInt(String, String, Integer)
	 */
	public static int getInt(String jsonData, String key, int defaultValue) {
		return getInt(jsonData, key, (Integer) defaultValue);
	}

	/**
	 * get Double from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getDouble(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getDouble(String)}</li>
	 *         </ul>
	 */
	public static Double getDouble(JSONObject jsonObject, String key,
			Double defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getDouble(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Double from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#getDouble(JSONObject, String, JSONObject)}
	 *         </li>
	 *         </ul>
	 */
	public static Double getDouble(String jsonData, String key,
			Double defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getDouble(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONObjectUtils#getDouble(JSONObject, String, Double)
	 */
	public static double getDouble(JSONObject jsonObject, String key,
			double defaultValue) {
		return getDouble(jsonObject, key, (Double) defaultValue);
	}

	/**
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return
	 * @see JSONObjectUtils#getDouble(String, String, Double)
	 */
	public static double getDouble(String jsonData, String key,
			double defaultValue) {
		return getDouble(jsonData, key, (Double) defaultValue);
	}

	/**
	 * get String from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getString(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getString(String)}</li>
	 *         </ul>
	 */
	public static String getString(JSONObject jsonObject, String key,
			String defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get String from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#getString(JSONObject, String, JSONObject)}
	 *         </li>
	 *         </ul>
	 */
	public static String getString(String jsonData, String key,
			String defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getString(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get String array from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONArray(String)} exception, return
	 *         defaultValue</li>
	 *         <li>if {@link JSONArray#getString(int)} exception, return
	 *         defaultValue</li>
	 *         <li>return string array</li>
	 *         </ul>
	 */
	public static String[] getStringArray(JSONObject jsonObject, String key,
			String[] defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			JSONArray statusArray = jsonObject.getJSONArray(key);
			if (statusArray != null) {
				String[] value = new String[statusArray.length()];
				for (int i = 0; i < statusArray.length(); i++) {
					value[i] = statusArray.getString(i);
				}
				return value;
			}
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
		return defaultValue;
	}

	/**
	 * get String array from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#getStringArray(JSONObject, String, JSONObject)}
	 *         </li>
	 *         </ul>
	 */
	public static String[] getStringArray(String jsonData, String key,
			String[] defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getStringArray(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONObject from jsonObject
	 * 
	 * @param jsonObject
	 *            <em><em></em></em>
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONObject(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getJSONObject(String)}</li>
	 *         </ul>
	 */
	public static JSONObject getJSONObject(JSONObject jsonObject, String key,
			JSONObject defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getJSONObject(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONObject from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#getJSONObject(JSONObject, String, JSONObject)}
	 *         </li>
	 *         </ul>
	 */
	public static JSONObject getJSONObject(String jsonData, String key,
			JSONObject defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getJSONObject(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONArray from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>if {@link JSONObject#getJSONArray(String)} exception, return
	 *         defaultValue</li>
	 *         <li>return {@link JSONObject#getJSONArray(String)}</li>
	 *         </ul>
	 */
	public static JSONArray getJSONArray(JSONObject jsonObject, String key,
			JSONArray defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get JSONArray from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#getJSONArray(JSONObject, String, JSONObject)}
	 *         </li>
	 *         </ul>
	 */
	public static JSONArray getJSONArray(String jsonData, String key,
			JSONArray defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getJSONArray(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Boolean from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>return {@link JSONObject#getBoolean(String)}</li>
	 *         </ul>
	 */
	public static Boolean getBoolean(JSONObject jsonObject, String key,
			Boolean defaultValue) {
		if (jsonObject == null || StringUtils.isEmpty(key)) {
			return defaultValue;
		}

		try {
			return jsonObject.getBoolean(key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Boolean from jsonData
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return defaultValue</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#getBoolean(JSONObject, String, Boolean)}</li>
	 *         </ul>
	 */
	public static Boolean getBoolean(String jsonData, String key,
			Boolean defaultValue) {
		if (StringUtils.isEmpty(jsonData)) {
			return defaultValue;
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getBoolean(jsonObject, key, defaultValue);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return defaultValue;
		}
	}

	/**
	 * get Boolean from jsonObject
	 * 
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>return {@link JSONObject#getBoolean(String)}</li>
	 *         </ul>
	 */
	public static boolean getBoolean(JSONObject jsonObject, String key,
			boolean defaultValue) {
		return getBoolean(jsonObject, key, (Boolean) defaultValue);
	}

	/**
	 * get Boolean from jsonObject
	 * 
	 * @param jsonData
	 * @param key
	 * @param defaultValue
	 * @return <ul>
	 *         <li>if jsonObject is null, return defaultValue</li>
	 *         <li>if key is null or empty, return defaultValue</li>
	 *         <li>return {@link JSONObject#getBoolean(String)}</li>
	 *         </ul>
	 */
	public static Boolean getBoolean(String jsonData, String key,
			boolean defaultValue) {
		return getBoolean(jsonData, key, (Boolean) defaultValue);
	}

	/**
	 * get map from jsonObject.
	 * 
	 * @param jsonObject
	 *            key-value pairs json
	 * @param key
	 * @return <ul>
	 *         <li>if jsonObject is null, return null</li>
	 *         <li>return {@link JSONObjectUtils#parseKeyAndValueToMap(String)}</li>
	 *         </ul>
	 */
	public static Map<String, String> getMap(JSONObject jsonObject, String key) {
		return JSONObjectUtils.parseKeyAndValueToMap(JSONObjectUtils.getString(
				jsonObject, key, null));
	}

	/**
	 * get map from jsonData.
	 * 
	 * @param jsonData
	 *            key-value pairs string
	 * @param key
	 * @return <ul>
	 *         <li>if jsonData is null, return null</li>
	 *         <li>if jsonData length is 0, return empty map</li>
	 *         <li>if jsonData {@link JSONObject#JSONObject(String)} exception,
	 *         return null</li>
	 *         <li>return {@link JSONObjectUtils#getMap(JSONObject, String)}</li>
	 *         </ul>
	 */
	public static Map<String, String> getMap(String jsonData, String key) {

		if (jsonData == null) {
			return null;
		}
		if (jsonData.length() == 0) {
			return new HashMap<String, String>();
		}

		try {
			JSONObject jsonObject = new JSONObject(jsonData);
			return getMap(jsonObject, key);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * parse key-value pairs to map. ignore empty key, if getValue exception,
	 * put empty value
	 * 
	 * @param sourceObj
	 *            key-value pairs json
	 * @return <ul>
	 *         <li>if sourceObj is null, return null</li>
	 *         <li>else parse entry by
	 *         {@link MapUtils#putMapNotEmptyKey(Map, String, String)} one by
	 *         one</li>
	 *         </ul>
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, String> parseKeyAndValueToMap(JSONObject sourceObj) {
		if (sourceObj == null) {
			return null;
		}

		Map<String, String> keyAndValueMap = new HashMap<String, String>();
		for (Iterator iter = sourceObj.keys(); iter.hasNext();) {
			String key = (String) iter.next();
			if (keyAndValueMap != null && !StringUtils.isEmpty(key)) {
				keyAndValueMap.put(key, getString(sourceObj, key, ""));
			}
		}
		return keyAndValueMap;
	}

	/**
	 * parse key-value pairs to map. ignore empty key, if getValue exception,
	 * put empty value
	 * 
	 * @param source
	 *            key-value pairs json
	 * @return <ul>
	 *         <li>if source is null or source's length is 0, return empty map</li>
	 *         <li>if source {@link JSONObject#JSONObject(String)} exception,
	 *         return null</li>
	 *         <li>return
	 *         {@link JSONObjectUtils#parseKeyAndValueToMap(JSONObject)}</li>
	 *         </ul>
	 */
	public static Map<String, String> parseKeyAndValueToMap(String source) {
		if (StringUtils.isEmpty(source)) {
			return null;
		}

		try {
			JSONObject jsonObject = new JSONObject(source);
			return parseKeyAndValueToMap(jsonObject);
		} catch (JSONException e) {
			if (isPrintException) {
				e.printStackTrace();
			}
			return null;
		}
	}
}

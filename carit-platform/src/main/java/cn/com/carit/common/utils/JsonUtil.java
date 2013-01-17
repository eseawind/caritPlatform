package cn.com.carit.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import cn.com.carit.platform.request.market.CheckAppUpdated;

public class JsonUtil {

	public static final ObjectMapper MAPPER = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K,V> jsonToMap(String json) throws Exception{
		return MAPPER.readValue(json, Map.class);
	}
	
	public static <K, V> String mapToStr(Map<K, V> map) throws Exception{
		return MAPPER.writeValueAsString(map);
	}
	
	public static Object [] jsonToStrArray(String json) throws Exception {
		if (json==null || json.isEmpty()) {
			json="[]";
		}
		return MAPPER.readValue(json, Object[].class);
	}
	
	public static String arrayToStr(Object [] array) throws Exception{
		if (array!=null && array.length>0) {
			return MAPPER.writeValueAsString(array);
		}
		return "[]";
	}
	
	public static <T> Object jsonToObject(String json, Class<T> clazz) throws Exception{
		return MAPPER.readValue(json, clazz);
	}
	
	public static <T> List<T> jsonToList(String json) throws Exception {
		return MAPPER.readValue(json, new TypeReference<List<T>>() {
			
		});
	}
	
	public static <T> String listToJson(List<T> list) throws Exception{
		if (list!=null && list.size()>0) {
			return MAPPER.writeValueAsString(list);
		}
		return "[]";
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws Exception {
		String json="{\"8\":\"23\",\"9\":\"24\",\"4\":\"152047890\",\"5\":\"19\",\"15\":\"36\",\"6\":\"20\",\"16\":\"37\",\"7\":\"5653\",\"13\":\"34\",\"14\":\"35\",\"1\":\"5\",\"11\":\"32\",\"2\":\"6\",\"12\":\"33\",\"3\":\"1800\",\"10\":\"25\"}";
		Map<String, String> map=jsonToMap(json);
		for (Entry<String,String> e: map.entrySet()) {
			System.out.println(e.getKey()+":"+e.getValue());
		}
		System.out.println(mapToStr(map));
		String json2="[\"010201\",\"010302\"]";
		Object [] array=jsonToStrArray(json2);
		for(Object str:array){
			System.out.println(str);
		}
		System.out.println(arrayToStr(array));
		
		List<CheckAppUpdated> list = new ArrayList<CheckAppUpdated>();
		list.add(new CheckAppUpdated("besttone.restaurant", "3.5.0.1"));
		list.add(new CheckAppUpdated("com.tencent.mm", "3.8"));
		
		System.out.println(listToJson(list));
	}

}

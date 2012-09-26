package cn.com.carit.common.utils;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import cn.com.carit.platform.request.LocationRequest;

public class JsonUtil {

	public static final ObjectMapper MAPPER = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	public static Map<String,String> jsonToMap(String json) throws Exception{
		return MAPPER.readValue(json, Map.class);
	}
	
	@SuppressWarnings("rawtypes")
	public static String mapToStr(Map map) throws Exception{
		return MAPPER.writeValueAsString(map);
	}
	
	public static String[] jsonToStrArray(String json) throws Exception {
		if (json==null || json.isEmpty()) {
			json="[]";
		}
		return MAPPER.readValue(json, String[].class);
	}
	
	public static String arrayToStr(String [] array) throws Exception{
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
		String [] array=jsonToStrArray(json2);
		for(String str:array){
			System.out.println(str);
		}
		System.out.println(arrayToStr(array));
		long now=Calendar.getInstance().getTimeInMillis();
		List<LocationRequest> l=MAPPER.readValue("[{\"lat\":22.543099,\"lng\":114.057868,\"time\":"+now+"}, {\"lat\":21.543099,\"lng\":114.057868,\"time\":"+now+"}]", new TypeReference<List<LocationRequest>>() {
			
		});
		for (LocationRequest r : l) {
			System.out.println(r);
		}
	}

}

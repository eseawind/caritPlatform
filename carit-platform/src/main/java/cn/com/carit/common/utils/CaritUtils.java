package cn.com.carit.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.com.carit.common.RssCatalog;
import cn.com.carit.platform.response.RssCatalogResponse;

public class CaritUtils {
	
	public static int [] listToIntArray(List<Integer> list) {
		int [] intArray = new int [list.size()];
		int i = 0;
		for (int j : list) {
			intArray[i]=j;
			i++;
		}
		return intArray;
	}
	
	/**
	 * 将字段单词拆分，以"_"区分单词。如 updateTime，拆分后为update_time
	 * @param field
	 * @return
	 */
	public static String splitFieldWords(String field){
		StringBuilder str=new StringBuilder();
		char [] array=field.toCharArray();
		for (int i = 0; i < array.length; i++) {
			if (Character.isUpperCase(array[i])) {
				str.append("_").append(Character.toLowerCase(array[i]));
			} else {
				str.append(array[i]);
			}
		}
		return str.toString();
	}
	
	/**
	 * 将日期转为字符串
	 * @param date
	 * @param formatter
	 * @return
	 */
	public static String dateToStr(Date date, String formatter){
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.format(date);
	}
	
	public static Date strToDate(String dateStr, String formatter) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(formatter);
		return sdf.parse(dateStr);
	}
	
	public static Date gmtStrToDate(String dateStr) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
		return sdf.parse(dateStr);
	}
	
	public static List<RssCatalogResponse> rssCatalogsToResponse(List<RssCatalog> list){
		List<RssCatalogResponse> result=new ArrayList<RssCatalogResponse>();
		if (list!=null&&list.size()>0) {
			for (RssCatalog rssCatalog : list) {
				RssCatalogResponse temp=new RssCatalogResponse();
				temp.setId(rssCatalog.getId());
				temp.setTitle(rssCatalog.getTitle());
				result.add(temp);
			}
		}
		return result;
	}
	
	
	public static void main(String[] args) throws Exception {
		System.out.println(gmtStrToDate("Thu, 29 Nov 2012 08:53:18 GMT"));
	}
}

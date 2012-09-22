package cn.com.carit.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoader;

public class AttachmentUtil {
	private static final Logger logger = LoggerFactory.getLogger(AttachmentUtil.class);
	private Properties p;
	
	private static class Holder{
		private static final AttachmentUtil INSTANCE = new AttachmentUtil();
	}
	
	private AttachmentUtil(){
		try {
			p = new Properties();
			logger.info("init AttachmentUtil INSTANCE start...");
			p.load(ContextLoader.getCurrentWebApplicationContext().getClassLoader().getResourceAsStream("attachment.properties"));
			if (logger.isDebugEnabled()) {
				logger.debug("attachment.photos="+p.getProperty("attachment.photos"));
				logger.debug("attachment.host="+p.getProperty("attachment.host"));
			}
			logger.info("init AttachmentUtil INSTANCE end...");
		} catch (IOException e) {
			logger.error("There is no attachment.properties in classpath root...", e);
		}
	}
	
	public static AttachmentUtil getInstance(){
		return Holder.INSTANCE;
	}
	

	public void mkDir(String path){
		File file=new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
	}
	
	public File newFile(String parentPath, String fileName){
		mkDir(parentPath);
		return new File(parentPath+File.separator+fileName);
	}
	
	public String getValue(String key) {
		return p.getProperty(key);
	}
	
	public File getImageFile(String fileName){
		return newFile(getValue("attachment.images"), fileName);
	}
	
	public File getPhotoFile(String fileName){
		return newFile(getValue("attachment.photos"), fileName);
	}
	
	public String getPhotoPath(String fileName) {
		int index = fileName.lastIndexOf(File.separator);
		if (index != -1) {
			fileName = fileName.substring(index);
		}
		return  getValue("attachment.photos")
				+ (File.separator + fileName);
	}
	public boolean deleteFile(String fileName){
		logger.info("delete file["+fileName+"]...");
		File file = new File(fileName);
		return file.delete();
	}
	
	public boolean deletePhoto(String fileName){
		if (!StringUtils.hasText(fileName)) {
			logger.warn("empty fileName...");
			return false;
		}
		return deleteFile(getPhotoPath(fileName));
	}
	
	public String getHost(){
		return getValue("attachment.host");
	}
}

package cn.com.carit.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class AttachmentUtil {
	private static final Logger logger = LoggerFactory.getLogger(AttachmentUtil.class);
	private Properties p;
	
	private String configPath;
	
	public AttachmentUtil(){
		try {
			p = new Properties();
			logger.info("init AttachmentUtil INSTANCE start...");
			URL url=new URL(configPath);
			p.load(url.openStream());
			logger.info("init AttachmentUtil INSTANCE end...");
		} catch (MalformedURLException e) {
			logger.error("read file["+configPath+"] error...", e);
		} catch (IOException e) {
			logger.error("init attachment.properties error...", e);
		}
	}
	
	public String getConfigPath() {
		return configPath;
	}

	public void setConfigPath(String configPath) {
		this.configPath = configPath;
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
	
	public Object getValue(String key) {
		return p.get(key);
	}
	
	public File getImageFile(String fileName){
		return newFile((String)getValue("attachment.images"), fileName);
	}
	
	public File getPhotoFile(String fileName){
		return newFile((String)getValue("attachment.photos"), fileName);
	}
	
	public String getPhotoPath(String fileName) {
		int index = fileName.lastIndexOf(File.separator);
		if (index != -1) {
			fileName = fileName.substring(index);
		}
		return (String) getValue("attachment.photos")
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
	
	
}

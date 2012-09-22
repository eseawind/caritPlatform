package cn.com.carit.converter;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.CaritUtils;

import com.rop.request.RopConverter;

public class DateConverter implements RopConverter<String, Date>{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Date convert(String source) {
		try {
			return CaritUtils.strToDate(source, Constants.DATE_FORMATTER);
		} catch (Exception e) {
			logger.error(Constants.DATE_FORMATTER+" format String conver to java.util.Date error...", e);
			return null;
		}
	}

	@Override
	public String unconvert(Date target) {
		return CaritUtils.dateToStr(target, Constants.DATE_FORMATTER);
	}

	@Override
	public Class<String> getSourceClass() {
		 return String.class;
	}

	@Override
	public Class<Date> getTargetClass() {
		return Date.class;
	}

}

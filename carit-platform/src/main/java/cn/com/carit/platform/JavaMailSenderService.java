package cn.com.carit.platform;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.common.Constants;
@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
@Service
public class JavaMailSenderService {
	private static Logger logger = LoggerFactory.getLogger(JavaMailSenderService.class);
	
	@Resource
	private JavaMailSender mailSender;
	@Resource
    private SimpleMailMessage simpleMailMessage;
	
	/**
	 * 发送邮件
	 * @param to 
	 * @param subject 主题
	 * @param content 内容
	 * @throws Exception 
	 */
	public void sendHtmlMail(String to, String subject, String content) {
		try {
			if (logger.isDebugEnabled()) {
				logger.info("sending mail to " + to);
				logger.debug("subject:"+subject);
				logger.debug("content:"+content);
			}
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,
					true, Constants.CHARACTER_ENCODING_UTF8);
			messageHelper.setFrom(simpleMailMessage.getFrom()); // 设置发件人Email
			messageHelper.setSubject(subject); // 设置邮件主题
			messageHelper.setTo(to); // 设定收件人Email
			messageHelper.setText(content, true);
			mailSender.send(mimeMessage); // 发送HTML邮件
			if (logger.isDebugEnabled()) {
				logger.info("mail have sent to " + to);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}

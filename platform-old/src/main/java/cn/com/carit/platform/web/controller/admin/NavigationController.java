package cn.com.carit.platform.web.controller.admin;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.carit.common.Constants;
import cn.com.carit.common.utils.AttachmentUtil;
import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.common.utils.MD5Util;
import cn.com.carit.platform.bean.Navigation;
import cn.com.carit.platform.service.NavigationService;
import cn.com.carit.platform.web.CacheManager;

@Controller
@RequestMapping(value="admin/navigation")
public class NavigationController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private NavigationService<Navigation> service;
	
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("navigation", new Navigation());
		return "admin/navigation";
	}
	
	/**
	 * 新增/保存<br>
	 * admin/navigation/save
 	 * @param navigation
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute(value="navigation") Navigation navigation
			, BindingResult result, HttpServletRequest request) throws Exception{
		if (result.hasErrors()) {
			log.warn(result.getAllErrors().toString());
			return -1;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		MultipartFile multipartFile = multipartRequest.getFile("file");
		if(navigation.getId()==0&&(multipartFile==null||multipartFile.getOriginalFilename().length()<=0)){
			log.debug("file must be not empty ...");
			return -1;
		}
    	if (multipartFile!=null && multipartFile.getOriginalFilename().length()>0) { // 上传文件
    		String prefix=MD5Util.md5Hex(System.nanoTime()+"");
    		String suffix = multipartFile.getOriginalFilename().substring(
    				multipartFile.getOriginalFilename().lastIndexOf(".")-1);
    		String fileName = (prefix + "." + suffix).toLowerCase();// 构建文件名称
    		// 图片文件
    		navigation.setLogo(Constants.BASE_PATH_IMAGE+fileName);
    		multipartFile.transferTo(AttachmentUtil.getImageFile(fileName));
		}
    	service.saveOrUpdate(navigation);
    	CacheManager.getInstance().refreshNavigations();
		return 1; 
	}
	
	/**
	 * 查看<br>
	 * admin/navigation/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public Navigation view(@RequestParam int id){
		if (id<=0) {
			log.warn("The param id must be bigger than 0...");
			return null;
		}
		return service.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/navigation/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam(required=false) int id
			, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return service.batchDelete(ids);
		} else if (id>0) {
			return service.delete(id);
		} else {
			throw new IllegalArgumentException("both id and ids are empty...");
		}
	}
	
	/**
	 * 查询
	 * admin/navigation/query
	 * @return {@link JsonPage<Navigation>}
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<Navigation> query(@ModelAttribute(value="navigation") Navigation navigation, BindingResult result,DataGridModel dgm){
		return service.queryByExemple(navigation, dgm);
	}
}

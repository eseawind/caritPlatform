package cn.com.carit.platform.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.carit.common.Constants;
import cn.com.carit.platform.bean.Navigation;
import cn.com.carit.platform.bean.PortalAccount;
import cn.com.carit.platform.service.NavigationService;
import cn.com.carit.platform.web.CacheManager;
@Controller
public class IndexController {
	
	@Resource
	private NavigationService<Navigation> navigationService;
	
	/**
	 * 首页
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String index(HttpServletRequest req, Model model){
		PortalAccount portalAccount=(PortalAccount) req.getSession().getAttribute(Constants.PORTAL_ACCOUNT);
		if (portalAccount!=null) {
			model.addAttribute("favoriteNavigation", navigationService.queryByAccount(portalAccount.getId()));
		}
		model.addAttribute("allCatalogList", CacheManager.getInstance().getAllCatalogList());
		model.addAttribute("allNavigation", CacheManager.getInstance().getAllNavigationList());
		return "index";
	}
	
}
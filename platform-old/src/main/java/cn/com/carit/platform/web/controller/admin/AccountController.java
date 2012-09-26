package cn.com.carit.platform.web.controller.admin;

import javax.annotation.Resource;

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

import cn.com.carit.common.utils.DataGridModel;
import cn.com.carit.common.utils.JsonPage;
import cn.com.carit.platform.bean.Account;
import cn.com.carit.platform.service.AccountService;
import cn.com.carit.platform.web.CacheManager;

@Controller
@RequestMapping(value="admin/account")
public class AccountController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@Resource
	private AccountService<Account> service;
	
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute("account", new Account());
		return "admin/account";
	}
	
	/**
	 * 新增/保存<br>
	 * admin/account/save
 	 * @param account
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute(value="account") Account account
			, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			log.warn(result.getAllErrors().toString());
			return -1;
		}
		int rows=service.saveOrUpdate(account);
		if (rows>0) {
			CacheManager.getInstance().refreshAccounts();
		}
		return rows;
	}
	
	/**
	 * 查看<br>
	 * admin/account/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public Account view(@RequestParam int id){
		if (id<=0) {
			log.warn("The param id must be bigger than 0...");
			return null;
		}
		return service.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/account/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam(required=false) int id
			, @RequestParam(required=false) String ids){
		int rows=0;
		if (StringUtils.hasText(ids)) {
			rows = service.batchDelete(ids);
		} else if (id>0) {
			rows = service.delete(id);
		} else {
			throw new IllegalArgumentException("both id and ids are empty...");
		}
		if (rows>0) { // 刷新缓存
			CacheManager.getInstance().refreshAccounts();
		}
		return rows;
	}
	
	/**
	 * 查询
	 * admin/account/query
	 * @return {@link JsonPage<Account>}
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<Account> query(@ModelAttribute(value="account") Account account, BindingResult result,DataGridModel dgm){
		return service.queryByExemple(account, dgm);
	}
	
	/**
	 * 封号
	 * admin/account/lock?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="lock", method=RequestMethod.GET)
	@ResponseBody
	public int lock(@RequestParam(required=false) int id, @RequestParam(required=false)String ids){
		int rows=0;
		if (StringUtils.hasText(ids)) {
			rows=service.batchLockAccount(ids);
		} else {
			rows = service.lockAccount(id);
		}
		if (rows>0) { // 刷新缓存
			CacheManager.getInstance().refreshAccounts();
		}
		return rows;
	}
	/**
	 * 解封
	 * admin/account/unlock?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="unlock", method=RequestMethod.GET)
	@ResponseBody
	public int unlock(@RequestParam(required=false) int id, @RequestParam(required=false)String ids){
		int rows = 0;
		if (StringUtils.hasText(ids)) {
			rows = service.batchUnLockAccount(ids);
		} else {
			rows = service.unLockAccount(id);
		}
		if (rows>0) { // 刷新缓存
			CacheManager.getInstance().refreshAccounts();
		}
		return rows;
	}
}

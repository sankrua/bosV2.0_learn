package cn.itcast.bos.web.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Standard;
import cn.itcast.bos.service.base.StandardService;
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
@SuppressWarnings("all")
public class StandardAction extends ActionSupport implements ModelDriven<Standard> {
	
	private Standard standard = new Standard();
	@Override
	public Standard getModel() {
		return standard;
	}
	
	//注入service对象
	@Autowired
	private StandardService standardService;
	
	//添加操作
	@Action(value="standard_save",results={@Result(name="success",type="redirect",
			location="./pages/base/standard.html")})
	public String save(){
		System.out.println("添加收派标准...");
		standardService.save(standard);
		return SUCCESS;
	}
	
	//属性驱动
	private int page;
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}


	public void setRows(int rows) {
		this.rows = rows;
	}


	//分页列表查询
	@Action(value="standard_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		//调用业务层查询数据结果
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Standard> page = standardService.findPageData(pageable);
		
		//返回客户端数据，需要total和rows
		Map<String, Object> map = new HashMap<>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		
		//将map转换成json数据返回,使用struts2-json-plugin操作
		ActionContext.getContext().getValueStack().push(map);
		System.out.println(map);
		return SUCCESS;
	}
	
	//查询所有取派标准


	@Action(value="standard_findAll",results={@Result(name="success",type="json")})
	public String findAll(){
		List<Standard> standards = standardService.findAll();
		//利用插件压栈
		ActionContext.getContext().getValueStack().push(standards);
		System.out.println(standards);
		return SUCCESS;
	}
}

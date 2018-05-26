package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
@SuppressWarnings("all")
public class CourierAction extends ActionSupport implements ModelDriven<Courier>{
	private Courier courier = new Courier();
	//注入service
	@Autowired
	private CourierService courierService;
	@Override
	public Courier getModel() {
		return courier;
	}

	//添加快递员方法
	@Action(value="courier_save",results={@Result(name="success",location="./pages/base/courier.html",type="redirect")})
	public String save(){
		System.out.println("添加快递员...");
		courierService.save(courier);
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
	@Action(value="Courier_PageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		//封装Pageable对象
		Pageable pageable = new PageRequest(page-1, rows);
		
		//根据查询条件构造Specification 条件查询对象(类似Hibernate的QBC查询)
		Specification<Courier> spec = new Specification<Courier>() {

			@Override
			/*
			 * (Javadoc)构造条件查询方法，如果方法返回null，代表无条件查询
			 * @Root 参数获取条件表达式 name=?, age=?
			 * @CriteriaQuery 参数，构造简单查询条件返回    提供where方法
			 * @CriteriaBuilder 参数 构造Predicate对象，条件对象，构造复杂查询效果 
			 */
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list = new ArrayList<>();
				//当前查询Root根对象 Courier
				//单表查询(查询当前对象 对应数据表)
				if (StringUtils.isNotBlank(courier.getCourierNum())) {
					//进行快递员工号查询
					//courierNum=?
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
					list.add(p1);
				}
				if (StringUtils.isNotBlank(courier.getCompany())) {
					//进行公司查询，模糊查询
					//company like %?%
					Predicate p2 = cb.equal(root.get("company").as(String.class),
							"%"+courier.getCompany()+"%");
					list.add(p2);
				}
				if (StringUtils.isNotBlank(courier.getType())) {
					//进行快递员类型查询,等值查询
					//type=?
					Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
					list.add(p3);
				}
				//多表查询(查询当前对象 关联对象   对应数据表)
				//使用Courier(Root),关联Standard
				Join<Object, Object> standardRoot = root.join("standard",
						JoinType.INNER);
				if (courier.getStandard()!=null && StringUtils.isNotBlank(courier.getStandard().getName())) {
					//进行收派标准 名称 模糊查询
					//standard.name like %?%
					Predicate p4 = cb.like(standardRoot.get("name").as(String.class), 
							"%"+courier.getStandard().getName()+"%");
					list.add(p4);
				}
				return cb.and(list.toArray(new Predicate[0]));
			}
		};
		//调用业务层，返回page
		Page<Courier> pageData = courierService.findPageData(spec, pageable);
		//将对象转换成datagrid格式
		Map<String, Object> map = new HashMap<>();
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		
		//将结果压入值栈底部
		ActionContext.getContext().getValueStack().push(map);
		System.out.println(map);
		return SUCCESS;
	}
}

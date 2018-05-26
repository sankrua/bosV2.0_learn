package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;
/**
 * 快递员操作接口
 * @author JustinLubo
 *
 */
public interface CourierService {
	//保存快递员
	public void save(Courier courier);

	//分页查询
	public Page<Courier> findPageData(Specification<Courier> spec,Pageable pageable);

}

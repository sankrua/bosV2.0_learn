package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Standard;

public interface StandardRepository extends JpaRepository<Standard, Integer>{
	//根据收派标准名称查询
	public List<Standard> findByName(String name);
	
	//根据收派标准模糊查询
	public List<Standard> findByNameLike(String name);
	
	//第二种查询方式
	@Query(value="from Standard where name = ?",nativeQuery=false)
	//nativeQuery为false 配置JPQL 为true 配置SQL
	public List<Standard> QueryName(String name);
	
	//第三种查询方式,配在类上
	@Query
	public List<Standard> QueryName2(String name);
	
	//修改操作
	@Query(value="update Standard set minLength = ?2 where id = ?1")
	@Modifying
	public void updateMinLength(Integer id,Integer minLength);
}

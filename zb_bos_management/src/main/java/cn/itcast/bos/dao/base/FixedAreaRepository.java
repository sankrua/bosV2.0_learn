package cn.itcast.bos.dao.base;

import cn.itcast.bos.domain.base.FixedArea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FixedAreaRepository extends JpaRepository<FixedArea,String>,JpaSpecificationExecutor<FixedArea>{
}

package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.FixedArea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface FixedService {
    void save(FixedArea model);

    //分页查询
    Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable);
}

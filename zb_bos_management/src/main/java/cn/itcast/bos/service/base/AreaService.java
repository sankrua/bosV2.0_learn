package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface AreaService {
    //批量保存区域实现
    void saveBatch(List<Area> areas);

    //条件查询
    Page<Area> findPageData(Specification<Area> spec, Pageable pageable);
}

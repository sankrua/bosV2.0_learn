package cn.itcast.bos.service.base;

import cn.itcast.bos.domain.base.Area;

import java.util.List;

public interface AreaService {
    //批量保存区域实现
    void saveBatch(List<Area> areas);
}

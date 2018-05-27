package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class AreaServiceImpl  implements AreaService {
    //注入DAO
    @Autowired
    private AreaRepository areaRepository;
    @Override
    public void saveBatch(List<Area> areas) {
        areaRepository.save(areas);
    }
}

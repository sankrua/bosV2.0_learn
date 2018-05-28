package cn.itcast.bos.service.base.impl;

import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FixedServiceImpl implements FixedService {
    //注入DAO对象
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Override
    public void save(FixedArea fixedArea) {
        fixedAreaRepository.save(fixedArea);
    }

    @Override
    public Page<FixedArea> findPageData(Specification<FixedArea> specification, Pageable pageable) {
        return fixedAreaRepository.findAll(specification,pageable);
    }
}

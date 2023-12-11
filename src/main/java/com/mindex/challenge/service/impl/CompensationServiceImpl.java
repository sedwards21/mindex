package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
    @Autowired
    private CompensationRepository compRepo;


    @Override
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation object [{}]", compensation);
        return compRepo.save(compensation);
    }

    @Override
    public Compensation read(String employeeId) {
        LOG.debug("Retrieving compensation object for employee with id  [{}]", employeeId);
        Compensation cmp = compRepo.findByEmployeeId(employeeId);
        if (cmp == null) {
            throw new RuntimeException("Compensation for this id not found: " + employeeId);
        }
        return cmp;
    }

    @Override
    public Compensation update(Compensation compensation) {
        LOG.debug("Updating compensation object [{}]", compensation);
        return compRepo.save(compensation);
    }
}

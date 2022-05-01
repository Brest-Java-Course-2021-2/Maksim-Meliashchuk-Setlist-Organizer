package com.epam.brest.dao.jpa.generator.id;

import com.epam.brest.dao.jpa.entity.BandEntity;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class CustomIdentityGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) throws HibernateException {
        if ((obj instanceof BandEntity)) {
            BandEntity entity = (BandEntity) obj;
            if ((entity.getBandId() == null) || (entity.getBandId().equals(0))) {
                return super.generate(session, obj);
            }
            return entity.getBandId();
        }
        return super.generate(session, obj);
    }
}

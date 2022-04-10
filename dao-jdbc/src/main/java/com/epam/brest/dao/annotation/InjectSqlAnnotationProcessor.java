package com.epam.brest.dao.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class InjectSqlAnnotationProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        this.scanInjectSqlAnnotation(bean);
        return bean;
    }

    protected void scanInjectSqlAnnotation(Object bean) {
        this.configureSqlInjection(bean);
    }

    private void configureSqlInjection(Object bean) {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.FieldCallback fieldCallback =
                new InjectSqlCallback(bean);
        ReflectionUtils.doWithFields(managedBeanClass, fieldCallback);
    }


}

package com.example.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

public class SpringContextUtil implements BeanFactoryAware {


	private static final SpringContextUtil SPRINGBEANLOCATOR = new SpringContextUtil();

    public static <T> T getBean(final Class<T> clazz) {
        return SpringContextUtil.SPRINGBEANLOCATOR.getBeanFactory().getBean(clazz);
    }

    /**
    * 根据提供的bean名称得到相应的服务类
    * @param beanName bean名称
    */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(final String beanName) {
        return (T) SpringContextUtil.SPRINGBEANLOCATOR.getBeanFactory().getBean(beanName);
    }

    /**
    * 根据提供的bean名称得到对应于指定类型的服务类
    * @param servName bean名称
    * @param clazz 返回的bean类型,若类型不匹配,将抛出异常
    */
    public static <T> T getBean(final String beanName, final Class<T> clazz) {
        return SpringContextUtil.SPRINGBEANLOCATOR.getBeanFactory().getBean(beanName, clazz);
    }

    public static SpringContextUtil getInstance() {
        return SpringContextUtil.SPRINGBEANLOCATOR;
    }

    private BeanFactory beanFactory;

    private SpringContextUtil() {}

    @Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory=beanFactory;
	}
    public BeanFactory getBeanFactory() {
        return this.beanFactory;
    }



}

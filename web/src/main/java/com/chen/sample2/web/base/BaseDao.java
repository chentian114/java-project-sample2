package com.chen.sample2.web.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author ChenTian
 */
@NoRepositoryBean
public interface BaseDao<T> extends JpaRepository<T, Serializable>, JpaSpecificationExecutor<T> {

}

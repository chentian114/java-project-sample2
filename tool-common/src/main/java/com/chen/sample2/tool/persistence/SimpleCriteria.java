package com.chen.sample2.tool.persistence;


import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询条件容器
 *
 * @author ChenTian
 */
public class SimpleCriteria<T> implements Specification<T> {
    private List<SimpleCriterion> criterionList = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (!criterionList.isEmpty()) {
            List<Predicate> predicates = new ArrayList<>();
            for (SimpleCriterion c : criterionList) {
                predicates.add(c.toPredicate(root, query, builder));
            }
            // 将所有条件用 and 联合起来
            if (predicates.size() > 0) {
                return builder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        }
        return builder.conjunction();
    }

    public SimpleCriteria(){

    }


    /**
     * 增加简单条件表达式
     */
    public void add(SimpleCriterion criterion) {
        if (criterion != null) {
            criterionList.add(criterion);
        }
    }


    public static class Builder<T> {

        private SimpleCriteria<T> simpleCriteria;

        public Builder() {
            this.simpleCriteria = new SimpleCriteria<>();
        }

        /**
         * 增加简单条件表达式
         */
        public Builder<T> add(SimpleCriterion criterion) {
            if (criterion != null) {
                simpleCriteria.add(criterion);
            }
            return this;
        }

        public SimpleCriteria<T> builder(){
            return simpleCriteria;
        }
    }

}

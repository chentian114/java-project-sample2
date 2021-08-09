package com.chen.sample2.tool.persistence;


import org.springframework.util.ObjectUtils;

import java.util.Collection;

/**
 * 条件构造器
 * 用于创建条件表达式
 *
 * @author ChenTian
 */
public class SimpleRestrictions {

    /**
     * 等于
     */
    public static SimpleExpression eq(String fieldName, Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.EQ);
    }

    /**
     * 集合包含某个元素
     */
    public static SimpleExpression hasMember(String fieldName, Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.IS_MEMBER);
    }

    /**
     * 不等于
     */
    public static SimpleExpression ne(String fieldName, Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.NE);
    }

    /**
     * 全模糊匹配，例如 %value%
     */
    public static SimpleExpression like(String fieldName, String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.LIKE);
    }

    /**
     * 左模糊匹配，例如 %value
     */
    public static SimpleExpression likeLeft(String fieldName, String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.LIKE_LEFT);
    }

    /**
     * 右模糊匹配，例如 value%
     */
    public static SimpleExpression likeRight(String fieldName, String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.LIKE_RIGHT);
    }

    /**
     * 大于
     */
    public static SimpleExpression gt(String fieldName, Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.GT);
    }

    /**
     * 小于
     */
    public static SimpleExpression lt(String fieldName, Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.LT);
    }

    /**
     * 小于等于
     */
    public static SimpleExpression lte(String fieldName, Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.LTE);
    }

    /**
     * 大于等于
     */
    public static SimpleExpression gte(String fieldName, Object value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        return new SimpleExpression(fieldName, value, SimpleCriterion.Operator.GTE);
    }

    /**
     * 并且
     */
    public static SimpleLogicalExpression and(SimpleCriterion... criterions) {
        return new SimpleLogicalExpression(criterions, SimpleCriterion.Operator.AND);
    }

    /**
     * 或者
     */
    public static SimpleLogicalExpression or(SimpleCriterion... criterions) {
        return new SimpleLogicalExpression(criterions, SimpleCriterion.Operator.OR);
    }

    /**
     * 包含于
     */
    @SuppressWarnings("rawtypes")
    public static SimpleLogicalExpression in(String fieldName, Collection value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        SimpleExpression[] ses = new SimpleExpression[value.size()];
        int i = 0;
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, SimpleCriterion.Operator.EQ);
            i++;
        }
        return new SimpleLogicalExpression(ses, SimpleCriterion.Operator.OR);
    }


    /**
     * 集合包含某几个元素，譬如可以查询User类中Set<String> set包含"ABC","bcd"的User集合，
     * 或者查询User中Set<Address>的Address的name为"北京"的所有User集合
     * 集合可以为基本类型或者JavaBean，可以是one to many或者是@ElementCollection
     *
     * @param fieldName 列名
     * @param value     集合
     * @return expresssion
     */
    public static SimpleLogicalExpression hasMembers(String fieldName, Object... value) {
        SimpleExpression[] ses = new SimpleExpression[value.length];
        int i = 0;
        //集合中对象是基本类型，如Set<Long>，List<String>
        SimpleCriterion.Operator operator = SimpleCriterion.Operator.IS_MEMBER;
        //集合中对象是JavaBean
        if (fieldName.contains(".")) {
            operator = SimpleCriterion.Operator.EQ;
        }
        for (Object obj : value) {
            ses[i] = new SimpleExpression(fieldName, obj, operator);
            i++;
        }
        return new SimpleLogicalExpression(ses, SimpleCriterion.Operator.OR);
    }
}

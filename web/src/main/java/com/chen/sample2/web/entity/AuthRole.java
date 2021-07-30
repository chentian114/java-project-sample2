package com.chen.sample2.web.entity;

import java.util.Date;
import javax.persistence.*;
import java.io.Serializable;

/**
 *  角色表
 *
 * @author Chentian
 */ 
@Entity
@Table(name = "t_auth_role")
public class AuthRole implements Serializable {

	/**
	 * 主键
	 */ 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	/**
	 * 角色名称
	 */ 
	@Column(name = "name")
	private String name;

	/**
	 * 角色描述
	 */ 
	@Column(name = "memo")
	private String memo;

	/**
	 * 创建时间
	 */ 
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 修改时间
	 */ 
	@Column(name = "modify_time")
	private Date modifyTime;

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id=id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getMemo(){
		return memo;
	}

	public void setMemo(String memo){
		this.memo=memo;
	}

	public Date getCreateTime(){
		return createTime;
	}

	public void setCreateTime(Date createTime){
		this.createTime=createTime;
	}

	public Date getModifyTime(){
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime){
		this.modifyTime=modifyTime;
	}

}


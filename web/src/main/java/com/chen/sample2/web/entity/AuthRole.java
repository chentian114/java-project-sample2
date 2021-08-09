package com.chen.sample2.web.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 *  角色表
 *
 * @author Chentian
 */ 
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
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

}


package com.dotple.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "`group`")
@Getter
@Setter
public class Group extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "group_id", columnDefinition = "int unsigned")
	private Long id;

	@Column(name = "group_nm")
	private String name;

	@Column(name = "group_pw")
	private Integer password;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager")
	private User manager;

	private Integer maxUser;
}

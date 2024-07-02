package com.dotple.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sharing extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "sharing_id", columnDefinition = "int unsigned")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "todo_id")
	private Todo todo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eval_id")
	private Eval eval;
}

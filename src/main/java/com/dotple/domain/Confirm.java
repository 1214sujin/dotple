package com.dotple.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Confirm extends BasePass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "confirm_id", columnDefinition = "int unsigned")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "eval_day_id")
	private EvalDay evalDay;
}

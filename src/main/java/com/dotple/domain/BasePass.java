package com.dotple.domain;

import jakarta.persistence.*;
import lombok.Getter;

@MappedSuperclass
@Getter
public abstract class BasePass extends BaseEntity {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Lob
	@Column(columnDefinition = "text")
	private String reason;

	private Boolean isOk;
}

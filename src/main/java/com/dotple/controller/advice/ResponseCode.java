package com.dotple.controller.advice;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

	C2000(200, 2000, "OK"),
	C4000(400, 4000, "잘못된 요청 형식입니다."),
	C4001(400, 4001, "규칙에 맞지 않는 값이 포함되어 있습니다."),
	C4010(401, 4010, "인증 정보가 만료되었습니다."),
	C4011(401, 4011, "잘못된 인증 정보입니다."),
	C4040(404, 4040, "존재하지 않는 자원입니다."),
	C4090(409, 4090, "중복된 자원입니다."),
	C4091(409, 4091, "현재 자원의 상태와 충돌하는 요청입니다."),
	C5000(500, 5000, "서버 내에 오류가 발생했습니다."),
	C5001(500, 5001, "DB 오류. 관리자에게 문의해주세요.");

	private final int status;
	private final int code;
	private final String message;
}

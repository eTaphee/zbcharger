package com.zerobase.zbcharger.domain.payment.api.naver.dto;

import lombok.Builder;

/**
 * 정기/반복 결제 정보 조회 요청
 *
 * @param recurrentId 정기/반복 결제 등록 번호
 * @param startTime   등록 시작 일시(YYYYMMDDHH24MMSS)
 * @param endTime     등록 종료 일시(YYYYMMDDHH24MMSS)
 * @param state       VALID: 유효, EXPIRED: 만료, ALL: 전체
 * @param pageNumber  조회하고자 하는 페이지번호, default: 1
 * @param rowsPerPage 페이지 당 row 건수(1~100), default: 20
 */
@Builder
public record SearchRegisteredRecurrentRequest(String recurrentId,
                                               String startTime,
                                               String endTime,
                                               String state,
                                               int pageNumber,
                                               int rowsPerPage) {

}

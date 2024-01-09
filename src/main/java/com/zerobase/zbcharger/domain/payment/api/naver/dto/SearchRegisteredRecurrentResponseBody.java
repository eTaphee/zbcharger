package com.zerobase.zbcharger.domain.payment.api.naver.dto;

/**
 * 정기/반복 결제 정보 조회 응답 바디
 *
 * @param list               정기/반복 결제 정보 목록
 * @param totalCount         전체 건수
 * @param totalResponseCount 응답 건수
 * @param totalPageCount     전체 페이지 수
 * @param currentPageNumber  현재 페이지 번호
 */
public record SearchRegisteredRecurrentResponseBody(Recurrent[] list, int totalCount,
                                                    int totalResponseCount, int totalPageCount,
                                                    int currentPageNumber) {

}

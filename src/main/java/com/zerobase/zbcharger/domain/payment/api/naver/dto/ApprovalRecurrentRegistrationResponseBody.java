package com.zerobase.zbcharger.domain.payment.api.naver.dto;

/**
 * 정기/반복 결제 완료 응답 바디
 *
 * @param reserveId      등록 예약 번호
 * @param tempReceiptId  임시 접수 번호
 * @param recurrentId    정기/반복결제 등록 번호
 * @param actionType     등록 예약 시 가맹점이 전달한 actionType
 * @param preRecurrentId actionType이 "CHANGE"인 경우 변경되기 전 정기/반복결제 등록 번호
 */
public record ApprovalRecurrentRegistrationResponseBody(String reserveId, String tempReceiptId,
                                                        String recurrentId, String actionType,
                                                        String preRecurrentId) {

}

package com.zerobase.zbcharger.domain.charger.type;

import com.zerobase.zbcharger.domain.common.type.EnumCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StationKindDetailCode implements EnumCode<String> {
    A001("A001", "관공서"),
    A002("A002", "주민센터"),
    A003("A003", "공공기관"),
    A004("A004", "지자체시설"),
    B001("B001", "공영주차장"),
    B002("B002", "공원주차장"),
    B003("B003", "환승주차장"),
    B004("B004", "일반주차장"),
    C001("C001", "고속도로 휴게소"),
    C002("C002", "지방도로 휴게소"),
    C003("C003", "쉼터"),
    D001("D001", "공원"),
    D002("D002", "전시관"),
    D003("D003", "민속마을"),
    D004("D004", "생태공원"),
    D005("D005", "홍보관"),
    D006("D006", "관광안내소"),
    D007("D007", "관광지"),
    D008("D008", "박물관"),
    D009("D009", "유적지"),
    E001("E001", "마트(쇼핑몰)"),
    E002("E002", "백화점"),
    E003("E003", "숙박시설"),
    E004("E004", "골프장(CC)"),
    E005("E005", "카페"),
    E006("E006", "음식점"),
    E007("E007", "주유소"),
    E008("E008", "영화관"),
    F001("F001", "서비스센터"),
    F002("F002", "정비소"),
    G001("G001", "군부대"),
    G002("G002", "야영장"),
    G003("G003", "공중전화부스"),
    G004("G004", "기타"),
    G005("G005", "오피스텔"),
    G006("G006", "단독주택"),
    H001("H001", "아파트"),
    H002("H002", "빌라"),
    H003("H003", "사업장(사옥)"),
    H004("H004", "기숙사"),
    H005("H005", "연립주택"),
    I001("I001", "병원"),
    I002("I002", "종교시설"),
    I003("I003", "보건소"),
    I004("I004", "경찰서"),
    I005("I005", "도서관"),
    I006("I006", "복지관"),
    I007("I007", "수련원"),
    I008("I008", "금융기관"),
    J001("J001", "학교"),
    J002("J002", "교육원"),
    J003("J003", "학원"),
    J004("J004", "공연장"),
    J005("J005", "관람장"),
    J006("J006", "동식물원"),
    J007("J007", "경기장");

    private final String value;
    private final String description;
}

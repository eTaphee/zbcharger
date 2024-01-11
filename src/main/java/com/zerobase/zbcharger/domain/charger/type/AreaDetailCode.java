package com.zerobase.zbcharger.domain.charger.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AreaDetailCode {
    Z11110("11110", "종로구"),
    Z11140("11140", "중구"),
    Z11170("11170", "용산구"),
    Z11200("11200", "성동구"),
    Z11215("11215", "광진구"),
    Z11230("11230", "동대문구"),
    Z11260("11260", "중랑구"),
    Z11290("11290", "성북구"),
    Z11305("11305", "강북구"),
    Z11320("11320", "도봉구"),
    Z11350("11350", "노원구"),
    Z11380("11380", "은평구"),
    Z11410("11410", "서대문구"),
    Z11440("11440", "마포구"),
    Z11470("11470", "양천구"),
    Z11500("11500", "강서구"),
    Z11530("11530", "구로구"),
    Z11545("11545", "금천구"),
    Z11560("11560", "영등포구"),
    Z11590("11590", "동작구"),
    Z11620("11620", "관악구"),
    Z11650("11650", "서초구"),
    Z11680("11680", "강남구"),
    Z11710("11710", "송파구"),
    Z11740("11740", "강동구"),
    Z26110("26110", "중구"),
    Z26140("26140", "서구"),
    Z26170("26170", "동구"),
    Z26200("26200", "영도구"),
    Z26230("26230", "부산진구"),
    Z26260("26260", "동래구"),
    Z26290("26290", "남구"),
    Z26320("26320", "북구"),
    Z26350("26350", "해운대구"),
    Z26380("26380", "사하구"),
    Z26410("26410", "금정구"),
    Z26440("26440", "강서구"),
    Z26470("26470", "연제구"),
    Z26500("26500", "수영구"),
    Z26530("26530", "사상구"),
    Z26710("26710", "기장군"),
    Z27110("27110", "중구"),
    Z27140("27140", "동구"),
    Z27170("27170", "서구"),
    Z27200("27200", "남구"),
    Z27230("27230", "북구"),
    Z27260("27260", "수성구"),
    Z27290("27290", "달서구"),
    Z27710("27710", "달성군"),
    Z28110("28110", "중구"),
    Z28140("28140", "동구"),
    Z28177("28177", "미추홀구"),
    Z28185("28185", "연수구"),
    Z28187("28187", "남동구"),
    Z28188("28188", "부평구"),
    Z28189("28189", "계양구"),
    Z28190("28190", "서구"),
    Z28191("28191", "강화군"),
    Z28192("28192", "옹진군"),
    Z29110("29110", "동구"),
    Z29140("29140", "서구"),
    Z29155("29155", "남구"),
    Z29170("29170", "북구"),
    Z29200("29200", "광산구"),
    Z30110("30110", "동구"),
    Z30140("30140", "중구"),
    Z30170("30170", "서구"),
    Z30200("30200", "유성구"),
    Z30230("30230", "대덕구"),
    Z31110("31110", "중구"),
    Z31140("31140", "남구"),
    Z31170("31170", "동구"),
    Z31200("31200", "북구"),
    Z31710("31710", "울주군"),
    Z36110("36110", "세종특별자치시"),
    Z41110("41110", "수원시"),
    Z41130("41130", "성남시"),
    Z41150("41150", "의정부시"),
    Z41170("41170", "안양시"),
    Z41190("41190", "부천시"),
    Z41210("41210", "광명시"),
    Z41220("41220", "평택시"),
    Z41250("41250", "동두천시"),
    Z41270("41270", "안산시"),
    Z41280("41280", "고양시"),
    Z41290("41290", "과천시"),
    Z41310("41310", "구리시"),
    Z41360("41360", "남양주시"),
    Z41370("41370", "오산시"),
    Z41390("41390", "시흥시"),
    Z41410("41410", "군포시"),
    Z41420("41420", "의왕시"),
    Z41450("41450", "하남시"),
    Z41460("41460", "용인시"),
    Z41480("41480", "파주시"),
    Z41500("41500", "이천시"),
    Z41550("41550", "안성시"),
    Z41570("41570", "김포시"),
    Z41590("41590", "화성시"),
    Z41610("41610", "광주시"),
    Z41630("41630", "양주시"),
    Z41650("41650", "포천시"),
    Z41670("41670", "여주시"),
    Z41800("41800", "연천군"),
    Z41820("41820", "가평군"),
    Z41830("41830", "양평군"),
    Z42110("42110", "춘천시"),
    Z42130("42130", "원주시"),
    Z42150("42150", "강릉시"),
    Z42170("42170", "동해시"),
    Z42190("42190", "태백시"),
    Z42210("42210", "속초시"),
    Z42230("42230", "삼척시"),
    Z42720("42720", "홍천군"),
    Z42730("42730", "횡성군"),
    Z42750("42750", "영월군"),
    Z42760("42760", "평창군"),
    Z42770("42770", "정선군"),
    Z42780("42780", "철원군"),
    Z42790("42790", "화천군"),
    Z42800("42800", "양구군"),
    Z42810("42810", "인제군"),
    Z42820("42820", "고성군"),
    Z42830("42830", "양양군"),
    Z43110("43110", "청주시"),
    Z43130("43130", "충주시"),
    Z43150("43150", "제천시"),
    Z43720("43720", "보은군"),
    Z43730("43730", "옥천군"),
    Z43740("43740", "영동군"),
    Z43745("43745", "증평군"),
    Z43750("43750", "진천군"),
    Z43760("43760", "괴산군"),
    Z43770("43770", "음성군"),
    Z43800("43800", "단양군"),
    Z44130("44130", "천안시"),
    Z44150("44150", "공주시"),
    Z44180("44180", "보령시"),
    Z44200("44200", "아산시"),
    Z44210("44210", "서산시"),
    Z44230("44230", "논산시"),
    Z44250("44250", "계룡시"),
    Z44270("44270", "당진시"),
    Z44710("44710", "금산군"),
    Z44760("44760", "부여군"),
    Z44770("44770", "서천군"),
    Z44790("44790", "청양군"),
    Z44800("44800", "홍성군"),
    Z44810("44810", "예산군"),
    Z44825("44825", "태안군"),
    Z45110("45110", "전주시"),
    Z45130("45130", "군산시"),
    Z45140("45140", "익산시"),
    Z45180("45180", "정읍시"),
    Z45190("45190", "남원시"),
    Z45210("45210", "김제시"),
    Z45710("45710", "완주군"),
    Z45720("45720", "진안군"),
    Z45730("45730", "무주군"),
    Z45740("45740", "장수군"),
    Z45750("45750", "임실군"),
    Z45770("45770", "순창군"),
    Z45790("45790", "고창군"),
    Z45800("45800", "부안군"),
    Z46110("46110", "목포시"),
    Z46130("46130", "여수시"),
    Z46150("46150", "순천시"),
    Z46170("46170", "나주시"),
    Z46230("46230", "광양시"),
    Z46710("46710", "담양군"),
    Z46720("46720", "곡성군"),
    Z46730("46730", "구례군"),
    Z46770("46770", "고흥군"),
    Z46780("46780", "보성군"),
    Z46790("46790", "화순군"),
    Z46800("46800", "장흥군"),
    Z46810("46810", "강진군"),
    Z46820("46820", "해남군"),
    Z46830("46830", "영암군"),
    Z46840("46840", "무안군"),
    Z46860("46860", "함평군"),
    Z46870("46870", "영광군"),
    Z46880("46880", "장성군"),
    Z46890("46890", "완도군"),
    Z46900("46900", "진도군"),
    Z46910("46910", "신안군"),
    Z47110("47110", "포항시"),
    Z47130("47130", "경주시"),
    Z47150("47150", "김천시"),
    Z47170("47170", "안동시"),
    Z47190("47190", "구미시"),
    Z47210("47210", "영주시"),
    Z47230("47230", "영천시"),
    Z47250("47250", "상주시"),
    Z47280("47280", "문경시"),
    Z47290("47290", "경산시"),
    Z47720("47720", "군위군"),
    Z47730("47730", "의성군"),
    Z47750("47750", "청송군"),
    Z47760("47760", "영양군"),
    Z47770("47770", "영덕군"),
    Z47820("47820", "청도군"),
    Z47830("47830", "고령군"),
    Z47840("47840", "성주군"),
    Z47850("47850", "칠곡군"),
    Z47900("47900", "예천군"),
    Z47920("47920", "봉화군"),
    Z47930("47930", "울진군"),
    Z47940("47940", "울릉군"),
    Z48121("48121", "창원시"),
    Z48123("48123", "진주시"),
    Z48125("48125", "통영시"),
    Z48127("48127", "사천시"),
    Z48129("48129", "김해시"),
    Z48170("48170", "밀양시"),
    Z48190("48190", "거제시"),
    Z48220("48220", "양산시"),
    Z48720("48720", "의령군"),
    Z48730("48730", "함안군"),
    Z48740("48740", "창녕군"),
    Z48820("48820", "고성군"),
    Z48840("48840", "남해군"),
    Z48850("48850", "하동군"),
    Z48860("48860", "산청군"),
    Z48870("48870", "함양군"),
    Z48880("48880", "거창군"),
    Z48890("48890", "합천군"),
    Z50110("50110", "제주시"),
    Z50130("50130", "서귀포시"),
    Z51110("51110", "춘천시"),
    Z51130("51130", "원주시"),
    Z51150("51150", "강릉시"),
    Z51170("51170", "동해시"),
    Z51190("51190", "태백시"),
    Z51210("51210", "속초시"),
    Z51230("51230", "삼척시"),
    Z51710("51710", "홍천군"),
    Z51720("51720", "횡성군"),
    Z51730("51730", "영월군"),
    Z51740("51740", "평창군"),
    Z51750("51750", "정선군"),
    Z51760("51760", "철원군"),
    Z51770("51770", "화천군"),
    Z51780("51780", "양구군"),
    Z51790("51790", "인제군"),
    Z51800("51800", "고성군"),
    Z51810("51810", "양양군");

    private final String value;
    private final String description;

    public static AreaDetailCode from(String code) {
        try {
            return AreaDetailCode.valueOf("Z" + code);
        } catch (Exception e) {
            return null;
        }
    }
}
package com.lion.demo.util;

import org.junit.jupiter.api.Test; // Junit 5
import static org.assertj.core.api.Assertions.assertThat;

public class CalcUtilTest {

    private final CalcUtil calcUtil = new CalcUtil();

    @Test
    void testAdd() {
        // given
        int x =3, y =4;

        // when: 테스트 실행
        int result = calcUtil.add(x,y);

        // then: 테스트 결과 확인
        assertThat(result).isEqualTo(9);
    }

    @Test
    void testMul() {
        int x=3, y=4;
        int result = calcUtil.mul(x,y);
        assertThat(result).isEqualTo(12);
    }

}

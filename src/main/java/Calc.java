// 다항식 계산기 구현
// 테스트케이스를 하나씩 도입해서 전부 만족하도록 구현
// 스택 자료구조 사용금지
// 재귀방식으로 구현

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Calc {


    public static int run(String expression) {

        // 문자열로 재귀하고 최종 결과는 String -> Integer -> int로 형변환
        // return Integer.parseInt(splitExp(expression));

        // splitExp 함수는 괄호 추적 기능만 할 수 있도록 변경함
        String makeP = "(" + expression + ")";
        return Integer.parseInt(splitExp(makeP));
    }


    // 가장 안쪽 괄호부터 재귀적으로 계산
    static String splitExp(String expression) {
        int end = expression.indexOf(')');

        if (end == -1) {
            //  괄호가 없는 식 또한 연산을 해야하기에 최종 연산 진행
            // return realCalculator(expression);

            // 초기에 무조건 앞 뒤에 괄호가 붙기에 괄호가 없는 경우, 최종 연산에 대해서도 일관적으로 재귀 가능하도록 변경
            return expression;
        }
        int start = expression.lastIndexOf('(', end);

        // 가장 안쪽에 있는 괄호를 계산 후 다시 문자열로 만들어서 치환
        String merged = expression.substring(0, start) + realCalculator(expression.substring(start + 1, end)) + expression.substring(end + 1, expression.length());
        return splitExp(merged);
    }

    // 실제 계산 함수
    // * -> - -> + 를 순서로 계산 진행
    static String realCalculator(String expression) {
        // + 를 기준으로 분리하는 것을 가장 먼저하고 마지막에 연산을 진행
        String[] plusParts = expression.split(" \\+ ");
        int sum = 0;

        for (String plusPart : plusParts) {
            // 그 안에서 - 를 기준으로 split
            String[] minusParts = plusPart.split(" - "); // 1 - 1과 1 - -1과는 띄어쓰기로 인해 구분되며 전자만 split

            // 곱셈 연산을 우선적으로 계산
            int termValue = mulCalculator(minusParts[0]);   // 곱셈 연산이 없더라도 우선 곱셈 (1 * 자기자신)

            // 빼기 연산이 존재했던 원소라면 스플릿 후의 크기가 2보다 크거나 같기 때문에 빼기 연산을 진행
            for (int j = 1; j < minusParts.length; j++) {
                // - 를 기준으로 나눈 문자열에는 곱하기 연산이 존재할 수 있음
                // 곱셈 연산을 우선적으로 마친 후 빼기 연산 진행
                termValue -= mulCalculator(minusParts[j]);
            }

            // 결과적으로 + 제외 모든 연산이 끝난 항들을 sum에 누적
            sum += termValue;
        }

        return String.valueOf(sum);
    }

    // 곱셈 함수
    static int mulCalculator(String piece) {
        // * 기준으로 분리 후 곱셈 연산 진행
        String[] mulParts = piece.split(" \\* ");

        // * 이 없는 문자열도 처리 가능하도록 초기값을 1로 시작
        int product = 1;


        for (String p : mulParts) {
            product *= Integer.parseInt(p.trim());
        }

        return product;
    }
}


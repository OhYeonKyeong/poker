package paker.game;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void test() {
        ArrayList<String> player = new ArrayList<>(Arrays.asList("H5", "D5", "D7", "C3", "H7", "S3", "C7"));
        // return 은 각자 deck 을 체크한 총점
        int score = 0;

        // 1. Check Deck 생성
        // x는 S, D, C, H 순의 모양 / y는 1~13의 숫자
        // 예를 들어, x의 0의 count가 5 이상이면 플러시
        // y의 숫자 4개가 같으면 포카드
        String[][] deck = {
                {"S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10", "S11", "S12", "S13", "S0"},
                {"D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13", "D0"},
                {"C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "C0"},
                {"H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H0"}
        };

        ArrayList<String> check = new ArrayList<>();
        String card = "";
        String point = "";

        // 각 카드의 좌표 확인
        for(int k = 0; k < player.size(); k++) {
            card = player.get(k);

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 14; j++) {
                    if (deck[i][j].equals(card)) {
                        point = i+" "+j;
                        check.add(point);
                    }
                }
            }
        }
        System.out.println("check : " + check);

        // 각 카드의 좌표 추출
        String[] coordinate = new String[2];
        int[] x = new int[7];
        int[] y = new int[7];
        for(int i = 0; i < player.size(); i++){
            String str = check.get(i);
            coordinate = str.split(" ");
            System.out.println("coordinate : " + Arrays.toString(coordinate));

            x[i] = Integer.parseInt(coordinate[0]);
            y[i] = Integer.parseInt(coordinate[1]);
        }

        int[] xSum = new int[4];
        // 모양 x의 합 구하기
        for(int i = 0; i < 7; i++){
            xSum[x[i]]++;
        }
        System.out.println("xSum : " + Arrays.toString(xSum));

        // 숫자 y의 합 구하기
        int[] ySum = new int[14];
        for(int i = 0; i < 7; i++){
            ySum[y[i]]++;
        }
        System.out.println("ySum : " + Arrays.toString(ySum));
        int isStraight = 1;
        int startNum = ySum[0];
        for(int i = 0; i < 13 ; i++){
            if((ySum[i] > 0) && (ySum[i+1] > 0)){
                isStraight++;
            } else {
                isStraight = 1;
                startNum = ySum[i];
            }
        }
        if(isStraight > 4){
            for ( int xx : xSum){
                if(xx > 4){
                    // 1. 스트레이트 플러시
                    System.out.println(900+startNum+1);
                } else {
                    // 5. 스트레이트
                    System.out.println( 500+startNum+1);
                }
            }

        }
    }

}
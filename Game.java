package paker.game;

import paker.card.Card;

import java.util.*;
import java.util.stream.Collectors;

public class Game {

    public static void main(String[] args){

        // 1. Deck 생성
        ArrayList<String > deck = new ArrayList<>(Arrays.asList(
            "S1", "S2", "S3", "S4", "S5", "S6", "S7", "S8", "S9", "S10", "S11", "S12", "S13",
            "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13",
            "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13",
            "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13"));

        System.out.println("Deck List : "+deck);
        System.out.println("Deck Size : "+deck.size());

        // 2. 순서 섞기
        Collections.shuffle(deck);

        System.out.println(" Deck List after shuffle : "+deck);

        // 3. 카드 배분
        ArrayList<String> hand1 = new ArrayList<>();
        ArrayList<String> hand2 = new ArrayList<>();
        ArrayList<String> hand3 = new ArrayList<>();
        ArrayList<String> hand4 = new ArrayList<>();

        for( int i = 28; i > 0; i-=4 ){
            hand1.add(deck.get(i));
            deck.remove(i);

            hand2.add(deck.get(i-1));
            deck.remove(i-1);

            hand3.add(deck.get(i-2));
            deck.remove(i-2);

            hand4.add(deck.get(i-3));
            deck.remove(i-3);
        }

        System.out.println("hand1 " + hand1);
//        System.out.println("hand2 " + hand2);
//        System.out.println("hand3 " + hand3);
//        System.out.println("hand4 " + hand4);

        // checkDeck을 통해 return 된 숫자가 가장 큰 사람이 이기도록 하면 됨
        System.out.println("hand1's checked cards list: " + checkDeck(hand1));
//        checkDeck(hand1);
//        checkDeck(hand2);
//        checkDeck(hand3);
//        checkDeck(hand4);
    }

    public static int checkDeck(ArrayList<String> hand){
        // return 은 각자 deck 을 체크한 총점
        int result =  0;

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
        for(int k = 0; k < hand.size(); k++) {
            card = hand.get(k);

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
        for(int i = 0; i < hand.size(); i++){
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

        // 모양만큼 for문을 돌린다
        for ( int xx : xSum){
            if(xx > 4){
                // 4. 플러시
            }
        }

        // 1. 스트레이트 플러시
        // 2. 포카드
        // 3. 풀하우스
        // 4. 플러시
        // 5. 스트레이트
        // 6. 트리플
        // 7. 투페어
        // 8. 원페어
        // 9. 노페어

        return 100+ getMaxNum(y);
    }


    public static int getMaxNum(int[] arr){
        int maxNum = arr[0];

        for (int i : arr) {
            if (i > maxNum)
                maxNum = i;
        }
        return maxNum;
    }

}

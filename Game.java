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

        // 2. 순서 섞기
        Collections.shuffle(deck);

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

        System.out.println("Player1's Cards " + hand1);
        System.out.println("Player2's Cards " + hand2);
        System.out.println("Player3's Cards " + hand3);
        System.out.println("Player4's Cards " + hand4);

        // checkDeck을 통해 return 된 숫자가 가장 큰 사람이 이기도록 하면 됨
        int score1 = checkDeck(hand1);
        int score2 = checkDeck(hand2);
        int score3 = checkDeck(hand3);
        int score4 = checkDeck(hand4);

        System.out.println("Player1 "+getScoreName(score1)+"(card: "+Integer.toString(score1).substring(1,3)+")");
        System.out.println("Player2 "+getScoreName(score2)+"(card: "+Integer.toString(score2).substring(1,3)+")");
        System.out.println("Player3 "+getScoreName(score3)+"(card: "+Integer.toString(score3).substring(1,3)+")");
        System.out.println("Player4 "+getScoreName(score4)+"(card: "+Integer.toString(score4).substring(1,3)+")");

        System.out.println(getWinner(score1, score2, score3, score4));

    }

    private static String getScoreName(int score) {
        if(score > 900) return "Straight Flush";
        if(score > 800) return "Four of a kind";
        if(score > 700) return "Full House";
        if(score > 600) return "Flush";
        if(score > 500) return "Straight";
        if(score > 400) return "Three of a kind";
        if(score > 300) return "Two Pair";
        if(score > 200) return "One Pair";
        if(score > 100) return "High Card";

        return "Error";
    }

    private static String getWinner(int score1, int score2, int score3, int score4) {
        String winner = "";

        int maximum = score1;
        winner = "player1 ";

        if (score2 >= maximum) {
            maximum = score2;
            winner = "player2 ";
        }
        if (score3 >= maximum) {
            maximum = score3;
            winner = "player3 ";
        }
        if (score4 >= maximum) {
            maximum = score4;
            winner = "player4 ";
        }

        return "Winner: "+winner+", Score: "+getScoreName(maximum)+"("+Integer.toString(maximum).substring(1,3)+")";
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

        // 각 카드의 좌표 추출
        String[] coordinate = new String[2];
        int[] x = new int[7];
        int[] y = new int[7];
        for(int i = 0; i < hand.size(); i++){
            String str = check.get(i);
            coordinate = str.split(" ");

            x[i] = Integer.parseInt(coordinate[0]);
            y[i] = Integer.parseInt(coordinate[1]);
        }

        int[] xSum = new int[4];
        // 모양 x의 합 구하기
        for(int i = 0; i < 7; i++){
            xSum[x[i]]++;
        }

        // 숫자 y의 합 구하기
        int[] ySum = new int[14];
        for(int i = 0; i < 7; i++){
            ySum[y[i]]++;
        }

        // 스트레이트와 스트레이트 플러시 구하기
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
                    return 900+startNum+1;
                } else {
                // 5. 스트레이트
                    return 500+startNum+1;
                }
            }

        }

        // 2. 포카드
        for(int i = 0 ; i < 14; i++){
            if(ySum[i] == 4){
                return 800+i+1;
            }
        }

        // 3. 풀하우스
        int tripleNum = 0;
        boolean fhTriple = false;
        boolean fhPair = false;
        for(int i = 0 ; i < 14; i++){
            if(ySum[i] == 3){
                fhTriple = true;
                tripleNum = i;
            }

            if(ySum[i] == 2){
                fhPair = true;
            }
        }

        if(fhTriple && fhPair){
            return 700+tripleNum+1;
        }

        // 4. 플러시
        for (int i = 0 ; i < 4; i++){
            if(xSum[i] > 4){
                return 600+i+1;
            }
        }

        // 6. 트리플
        int maxNum = 0;
        int triple = 0;
        for(int i = 0 ; i < 14; i++){
            if(ySum[i] == 3){
                triple++;
                maxNum = i;
            }
        }
        if(triple > 0){
            return 400+maxNum+1;
        }

        // 7. 투페어
        int pair = 0;
        maxNum = 0;
        for(int i = 0 ; i < 14; i++){
            if(ySum[i] == 2){
                pair++;
                maxNum = i;
            }
        }

        if(pair > 1){
            return 300+maxNum+1;
        }
        // 8. 원페어
        if(pair == 1){
            return 200+maxNum+1;
        }

        // 9. 노페어
        Arrays.sort(y);
        return 100+ getMaxNum(y);
    }


    public static int getMaxNum(int[] arr){
        int maxNum = arr[0];

        for (int i : arr) {
            if (i > maxNum)
                maxNum = i;
        }
        return maxNum+1;
    }

}

package com.example.securitytest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Test {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        long[] distance = new long[N-1];
        long[] city = new long[N];
        for(int i=0;i<distance.length;i++){
            distance[i] = Integer.parseInt(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0;i < city.length;i++){
            city[i] = Integer.parseInt(st.nextToken());
        }
        long temp  = city[0];
        long sum  = 0;
        for(int i =0;i<city.length -1;i++){

            if(city[i] < temp){
                temp = city[i]; //최소값인지 체크하고 최소값이면 값을 변경한다. 그게 아니라면 기존값을 통해 거리만큼 주유
            }
            sum += (temp * distance[i]);
        }

        System.out.println(sum);



    }
}

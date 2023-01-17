package com.example.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class test {

    public static void main(String[] args) throws IOException {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());
            int[] arr = new int[N];

            for (int i = 0; i < N; i++) {
                arr[i] = Integer.parseInt(br.readLine());
            }
            int totalCoin = 0;


            for(int i = N -1 ;i>0;i--){
               if(arr[i] <= W){
                   totalCoin += (W/arr[i]);
                   W = W%arr[i];
               }
            }


              System.out.println(totalCoin);

    }
}


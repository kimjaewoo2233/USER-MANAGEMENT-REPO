package com.example.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Test2 {

    public static void main(String[] args) throws IOException {
            BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
            int N = Integer.parseInt(br.readLine());
            int[][] list = new int[N][2];
            StringTokenizer st;


            for(int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                list[i][0] = Integer.parseInt(st.nextToken());	// 시작시간
                list[i][1] = Integer.parseInt(st.nextToken());	// 종료시간
            }

            Arrays.sort(list, new Comparator<int[]>() {
                @Override
                public int compare(int[] o1, int[] o2) {
                    return o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0];
                }
            });


            int prev_end_time = 0;
            int result=0;

            for (int i = 1; i < list.length; i++) {

                if(prev_end_time <= list[i][0]){
                        prev_end_time = list[i][1];
                        result++;
                }
            }

         System.out.println(result);
    }
}

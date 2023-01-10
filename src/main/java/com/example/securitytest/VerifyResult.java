package com.example.securitytest;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyResult {

        private boolean success;
        private String username;

}

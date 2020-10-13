package com.protoend.config;


import com.jcraft.jsch.JSch;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FTPClientConfig {

    @Bean
    public JSch getFTPClient(){
        return new JSch();
    }
}
package com.protoend.config;


import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FTPClientConfig {

    @Bean
    public FTPClient getFTPClient(){
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(60000);
        ftpClient.setRemoteVerificationEnabled(true);
        return ftpClient;
    }
}
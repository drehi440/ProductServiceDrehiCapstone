package com.rehi.productservicedrehicapstone.services.ai;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class UserAiService {

    private static final Logger log = LoggerFactory.getLogger(UserAiService.class);

    private final ChatClient chatClient;

    public UserAiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    /**
     * Uses Spring AI (OpenAI) to generate a short, friendly user bio based on interests.
     * This method is designed as a helper to enhance user profiles with AI-generated copy.
     */
    public String generateUserBio(String interests) {
        String prompt = """
                You are an assistant helping to write fun, concise user bios for an e-commerce profile.
                Write a single-sentence bio (max 220 characters), friendly and upbeat, based on these interests:
                """ + interests;

        log.info("Generating AI user bio for interests='{}'", interests);

        return chatClient
                .prompt()
                .user(prompt)
                .call()
                .content();
    }
}



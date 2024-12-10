package com.lion.demo.chatting;

import java.util.List;

public interface ChatMessageService {
    List<ChatMessage> getListByUser(String uid, String friendUid);
    
    ChatMessage getLastChatMessage(String uid, String friendUid);
    
    int getNewCount(String friendUid, String uid); // 내가 안읽은 메시지
    
    ChatMessage insertChatMessage(ChatMessage chatMessage);
}

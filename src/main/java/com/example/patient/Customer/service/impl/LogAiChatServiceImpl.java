package com.example.patient.Customer.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.example.patient.Customer.entity.LogAiChat;
import com.example.patient.Customer.mapper.LogAiChatMapper;
import com.example.patient.Customer.service.LogAiChatService;
import org.springframework.stereotype.Service;

/**
 * 智能答疑对话记录 服务层实现。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Service
public class LogAiChatServiceImpl extends ServiceImpl<LogAiChatMapper, LogAiChat>  implements LogAiChatService{

}

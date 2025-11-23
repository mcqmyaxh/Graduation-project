package com.example.patient.Customer.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 智能答疑对话记录 实体类。
 *
 * @author MECHREV
 * @since 2025-11-29
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("log_ai_chat")
public class LogAiChat implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long logId;

    /**
     * 提问患者
     */
    private Integer patientId;

    /**
     * 患者病症描述/提问
     */
    private String questionText;

    /**
     * 智能体建议
     */
    private String aiResponseText;

    private LocalDateTime createdAt;

}

package com.example.maincommon.dto.participation;

import com.example.maincommon.dto.Status;
import lombok.Data;

@Data
public class ParticipationRequestDto {
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private Status status;
}

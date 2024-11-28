package com.example.maincommon.dto.event;

import com.example.maincommon.dto.Status;
import lombok.Data;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    private List<Integer> requestIds;
    private Status status;
}

package com.indocyber.winterhold.dtos.book;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class checkCodeBooResponse {
    private final Boolean isExist;
}

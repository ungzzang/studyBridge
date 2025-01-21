package com.green.studybridge.acaClass.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AcaClassWeekDay {
    @JsonIgnore
    private long dayId;
    @Schema(title = "요일", example = "월", requiredMode = Schema.RequiredMode.REQUIRED)
    private String day;
}

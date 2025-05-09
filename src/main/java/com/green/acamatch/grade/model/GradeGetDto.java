package com.green.acamatch.grade.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class GradeGetDto {
    @JsonIgnore
    private long examId;

    @Schema(title = "과목 이름", example = "1차 시험")
    private String examName;
    @Schema(title = "시험 날짜", example = "2025-01-26")
    private LocalDate examDate;
    @Schema(title = "과목 점수", example = "90")
    private Integer score;
    @Schema(title = "패스 여부", example = "0")
    private Integer pass;
}

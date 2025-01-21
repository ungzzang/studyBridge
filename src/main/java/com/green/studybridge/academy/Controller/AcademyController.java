package com.green.studybridge.academy.Controller;

import com.green.studybridge.academy.Service.AcademyService;
import com.green.studybridge.academy.Service.TagService;
import com.green.studybridge.academy.model.*;
import com.green.studybridge.config.model.ResultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/academy")
@RequiredArgsConstructor
@Tag(name = "학원")
public class AcademyController {
    private final AcademyService academyService;
    private final TagService tagService;
    private final UserMessage userMessage;


    @PostMapping
    @Operation(summary = "학원정보등록", description = "필수: 유저 PK, 동 PK, 학원 이름, 학원 번호, 학원 상세 주소 || 옵션: 학원 설명, 강사 수, 오픈 시간, 마감 시간, 학원 사진")
    public ResultResponse<Integer> postAcademy(@RequestPart(required = false) MultipartFile pic, @RequestPart AcademyPostReq req) {
        int result1 = academyService.insAcademy(pic, req);
        int result2 = tagService.insAcaTag(req);
        return ResultResponse.<Integer>builder()
                .resultMessage((result1 == 1? "학원정보등록성공" : "학원정보등록실패"))
                .resultData(result1)
                .build();
    }

    @PutMapping
    @Operation(summary = "학원정보수정")
    public ResultResponse<Integer> putAcademy(@RequestPart(required = false) MultipartFile pic, @RequestPart AcademyUpdateReq req) {
        int result = academyService.updAcademy(pic, req);
        return ResultResponse.<Integer>builder()
                .resultMessage("학원 정보 수정 완료")
                .resultData(result)
                .build();
    }

    @DeleteMapping
    @Operation(summary = "학원정보삭제")
    public ResultResponse<Integer> deleteAcademy(@ModelAttribute AcademyDeleteReq req) {
        int result = academyService.delAcademy(req);
        return ResultResponse.<Integer>builder()
                .resultMessage("학원 정보 삭제 완료")
                .resultData(result)
                .build();
    }

// -------------------------------------------------------------

    @GetMapping("academyList")
    @Operation(summary = "학원 리스트 검색")
    public ResultResponse<List<getAcademyRes>> getAcademyList(@ParameterObject @ModelAttribute getAcademyReq p){
        List<getAcademyRes> res = academyService.getAcademyRes(p);
        return ResultResponse.<List<getAcademyRes>>builder()
                .resultMessage("학원리스트 검색 성공")
                .resultData(res)
                .build();
    }

    @GetMapping("academyDetail/{acaId}")
    @Operation(summary = "학원 정보 자세히 보기")
    public ResultResponse<GetAcademyDetail> getAcademyDetail(@PathVariable Long acaId){
        GetAcademyDetail res = academyService.getAcademyDetail(acaId);
        log.info("result: {}", res);
        return ResultResponse.<GetAcademyDetail>builder()
                .resultMessage("학원 상세보기 성공")
                .resultData(res)
                .build();
    }

   /* @GetMapping("tagList")
    @Operation(summary = "등록된 태그 불러오기")
    public ResultResponse<List<GetTagList>> getTagList(){
        List<GetTagList> list = academyService.getTagList();
        return ResultResponse.<List<GetTagList>>builder()
                .resultMessage("태그 불러오기")
                .resultData(list)
                .build();
    }*/ //태그 불러오기 수정필요해서 주석처리했다.

}

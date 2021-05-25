package com.projects.app.controllers;

import com.projects.app.common.exception.model.BackendError;
import com.projects.app.common.response.ResponseTool;
import com.projects.app.common.response.model.APIPagingResponse;
import com.projects.app.common.response.model.APIResponse;
import com.projects.app.models.Report;
import com.projects.app.models.request.ReportDTO;
import com.projects.app.services.ReportService;
import com.projects.app.utils.Constant;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/report")
@Tag(name = "Report")
public class ReportController {


    @Autowired
    ReportService reportService;


    @ApiOperation(value = "get generated report")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIPagingResponse> getReport(
            @RequestParam(required = false) Date start,
            @RequestParam(required = false) Date end,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_SIZE) Integer pageSize,
            @RequestParam(required = false, defaultValue = Constant.DEFAULT_PAGE_NUMBER) Integer pageNumber
    ) throws BackendError {
        try {
            Page<Report> reports = reportService.getAllReport(start, end
                    , pageNumber, pageSize);
            return ResponseTool.GET_OK(new ArrayList<Object>(reports.getContent()), (int) reports.getTotalElements());
        } catch (Exception e) {
            e.printStackTrace();
            throw new BackendError(HttpStatus.BAD_REQUEST, "Something went wrong");
        }
    }

    @ApiOperation(value = "Pre generate report")
    @GetMapping("/generate")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> preGenerateReport(
            @RequestParam(required = false, defaultValue = "1") Long staffID,
            @RequestParam Date dateFrom,
            @RequestParam Date dateTo
    ) throws BackendError {
        ReportDTO reportDTO = reportService.getInformationForReport(dateFrom, dateTo, staffID);
        return ResponseTool.GET_OK(reportDTO);
    }

    @ApiOperation(value = "Generate report")
    @PostMapping("/generate")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<APIResponse> generateReport(
            @Valid @RequestBody ReportDTO reportDTO
    ) throws BackendError {
        Report report = reportService.parseReportDTOToReport(reportDTO);
        report.setReportDate(new Date());
        Report newReport = reportService.add(report);
        return ResponseTool.POST_OK(newReport);
    }

    @ApiOperation(value = "Get report by ID")
    @GetMapping("/{reportID}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<APIResponse> getReport(@PathVariable(name = "reportID") long reportID) throws BackendError {
        Report report = reportService.getReportById(reportID);
        if (report == null) {
            String message = "Report không tồn tại hoặc đã bị xóa khỏi cơ sở dữ liệu";
            throw new BackendError(HttpStatus.BAD_REQUEST, message);
        }
        return ResponseTool.GET_OK(report);

    }
}


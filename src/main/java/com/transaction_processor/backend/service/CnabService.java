package com.transaction_processor.backend.service;

import com.transaction_processor.backend.controller.CnabController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
public class CnabService {

    private final Path fileStorageLocation;
    private final Job job;
    private final JobLauncher jobLauncher;

    public CnabService(@Value("${file.upload-dir}") String fileUploadDir,
                       @Qualifier("jobLauncherAsync") JobLauncher jobLauncher,
                       Job job) {
        this.fileStorageLocation = Paths.get(fileUploadDir);
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    public void uploadCnab(MultipartFile file) throws Exception {
        var fileName = StringUtils.cleanPath(file.getOriginalFilename());
        var targetLocation = fileStorageLocation.resolve(fileName);
        log.info("Temp directory being used: {}", System.getProperty("java.io.tmpdir"));
        file.transferTo(targetLocation);

        var jobParameters = new JobParametersBuilder()
                .addJobParameter("cnab",
                        file.getOriginalFilename(), String.class,
                        true)
                .addJobParameter("cnabFile",
                        "file:" + targetLocation.toString(),
                        String.class)
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }
}

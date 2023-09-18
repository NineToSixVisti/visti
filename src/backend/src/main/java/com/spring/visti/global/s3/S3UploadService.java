package com.spring.visti.global.s3;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@Service
@RequiredArgsConstructor
public class S3UploadService{
    private final AmazonS3 amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // MultipartFile 전달 받아 File 로 전환. S3에 업로드
    public String S3Upload(MultipartFile multipartFile, String postCategory) throws IOException {
        Optional<File> uploadFile = convert(multipartFile);
        if (!uploadFile.isPresent()) {
            return "사진없음";
        }

        return pathUpload(uploadFile.get(), postCategory);
    }

    // S3에 파일 저장
    private String pathUpload(File uploadFile, String postCategory) {
        //같은 이름으로 들어오면 저장이 안되므로 UUID 생성
        String originName = UUID.randomUUID() + "-" + uploadFile.getName();
        String fileName = postCategory + "/" + originName;
        String uploadImagePath = putS3(uploadFile, fileName);
        // 로컬 생성된 File 삭제 (File 로 전환하며 생성된 파일)
        removeNewFile(uploadFile);

        return uploadImagePath;
    }

    // 저 장.
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                // bucket 명, 고유 키 (file 이름), File 을 넣어서 요청 객체 만들기
                new PutObjectRequest(bucket, fileName, uploadFile)
//                        .withCannedAcl(CannedAccessControlList.PublicRead) acl 사용 비권장하므로 IAM만 사용

        );

        // bucket 이름과, 고유 키를 통해 Url 을 불러오기
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 생성된 파일 삭제
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일 삭제 성공");
        } else {
            log.info("파일 삭제 실패");
        }
    }

    // MultipartFile -> File 로 전환
    private Optional<File> convert(MultipartFile file) throws IOException {
        File converFile = new File(file.getOriginalFilename());
        // 객체 -> 실제 파일화
        try {
            if (converFile.createNewFile()) {
                // 내부 내용 작성
                try (FileOutputStream fos = new FileOutputStream(converFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(converFile);
            }
        } catch (IOException e) {
            log.error("error", e);
        }
        return Optional.empty();
    }

    //S3이미지 삭제

    public void deleteS3File(String fileName) throws IOException{
        try{
            amazonS3Client.deleteObject(bucket, fileName);
        }catch (SdkClientException e){
            throw new IOException("Error Delete S3 file", e);
        }
    }


}


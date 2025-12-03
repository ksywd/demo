package com.example.demo.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileController {

    // application.properties 에서 업로드 경로 주입
    @Value("${spring.servlet.multipart.location}")
    private String uploadFolder;

    // 업로드 폴더 준비 (없으면 생성)
    private Path getUploadPath() throws IOException {
        Path uploadPath = Paths.get(uploadFolder).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        return uploadPath;
    }

    // 같은 이름의 파일이 있으면 _1, _2 식으로 뒤에 번호를 붙여서 이름 생성
    private Path createUniqueFile(Path uploadPath, String baseName, String ext) throws IOException {
        String fileName = baseName + ext;
        Path filePath = uploadPath.resolve(fileName);

        int count = 1;
        while (Files.exists(filePath)) {
            fileName = baseName + "_" + count + ext;
            filePath = uploadPath.resolve(fileName);
            count++;
        }
        return filePath;
    }

    // 1) 메일 내용을 텍스트 파일로 저장
    @PostMapping("/upload-email")
    public String uploadEmail(@RequestParam("email") String email,
                              @RequestParam("subject") String subject,
                              @RequestParam("message") String message,
                              RedirectAttributes redirectAttributes) {

        try {
            Path uploadPath = getUploadPath();

            // 이메일을 기반으로 파일 이름 생성(특수문자 제거)
            String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
            Path filePath = createUniqueFile(uploadPath, sanitizedEmail, ".txt");

            System.out.println("Email file path: " + filePath);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
                writer.write("메일 제목: " + subject);
                writer.newLine();
                writer.write("요청 메시지:");
                writer.newLine();
                writer.write(message);
            }

            redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");
            return "upload_end";

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "업로드 중 오류가 발생했습니다.");
            return "article_error";
        }
    }

    // 2) 실제 파일 업로드 (예: 프로필 이미지)
    @PostMapping("/upload-file")
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "업로드할 파일을 선택하세요.");
            return "article_error";
        }

        try {
            Path uploadPath = getUploadPath();

            String originalName = file.getOriginalFilename();
            if (originalName == null || originalName.isBlank()) {
                originalName = "uploaded";
            }

            // 파일명과 확장자 분리
            String baseName = originalName;
            String ext = "";
            int dotIndex = originalName.lastIndexOf('.');
            if (dotIndex != -1) {
                baseName = originalName.substring(0, dotIndex);
                ext = originalName.substring(dotIndex);   // .png, .jpg 등
            }

            // 파일명에서 특수문자 제거
            baseName = baseName.replaceAll("[^a-zA-Z0-9]", "_");

            Path filePath = createUniqueFile(uploadPath, baseName, ext);
            System.out.println("Upload file path: " + filePath);

            // 실제 파일 저장
            file.transferTo(filePath.toFile());

            redirectAttributes.addFlashAttribute(
                    "message", "파일이 성공적으로 업로드되었습니다: " + filePath.getFileName());
            return "upload_end";

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message", "파일 업로드 중 오류가 발생했습니다.");
            return "article_error";
        }
    }
}

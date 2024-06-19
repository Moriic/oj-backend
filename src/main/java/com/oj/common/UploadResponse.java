package com.oj.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadResponse {

    private int errno;
    private UploadData data;
    private String message;

    // 上传成功的静态工厂方法
    public static UploadResponse success(String url) {
        return new UploadResponse(0, new UploadData(url), null);
    }

    // 上传失败的静态工厂方法
    public static UploadResponse failure(String message) {
        return new UploadResponse(1, null, message);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadData {
        private String url;
    }
}


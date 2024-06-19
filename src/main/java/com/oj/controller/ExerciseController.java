package com.oj.controller;

import com.oj.common.BaseResponse;
import com.oj.common.ResultUtils;
import com.oj.model.entity.Exercise;
import com.oj.service.ExerciseService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Resource
    private ExerciseService exerciseService;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.url}")
    private String uploadUrl;

    //添加实验
    @PostMapping("/add")
    public BaseResponse<Boolean> addExercise(@RequestBody Exercise exercise) {
        // 设置 state 默认值为 0
        exercise.setState(0);
        // 设置当前时间为 timestamp
        exercise.setTimestamp(LocalDateTime.now());
        exerciseService.save(exercise);
        return ResultUtils.success(true);
    }
    //删除实验
    @DeleteMapping("/{id}")
    public BaseResponse<Boolean> deleteExercise(@PathVariable Long id) {
        exerciseService.removeById(id);
        return ResultUtils.success(true);
    }
    //修改实验内容
    @PutMapping
    public  BaseResponse<Boolean> updateExercise(@RequestBody Exercise exercise) {
        exerciseService.updateById(exercise);
        return ResultUtils.success(true);
    }
    //查看实验
    @GetMapping("/list")
    public BaseResponse<List<Exercise>> getExerciseList() {
        return ResultUtils.success(exerciseService.list());
    }
    //撤回实验
    @PutMapping("/updateState/{id}")
    public BaseResponse<Boolean> updateState(@PathVariable Long id) {
        Exercise exercise = exerciseService.getById(id);
        if (exercise != null && exercise.getState() == 1) {
            exercise.setState(0);
            exerciseService.updateById(exercise);
            return ResultUtils.success(true);
        }
        return ResultUtils.success(false);
    }

    //发布实验
    @PutMapping("/publish/{id}")
    public BaseResponse<Boolean> publishExercise(@PathVariable Long id) {
        Exercise exercise = exerciseService.getById(id);
        if (exercise != null && exercise.getState() == 0) {
            exercise.setState(1);
            exerciseService.updateById(exercise);
            return ResultUtils.success(true);
        }
        return ResultUtils.success(false);
    }

    //上传图片
    @PostMapping("/uploadImage")
    public BaseResponse<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println("ss");
        // 获取上传图片的全名
        String filename = file.getOriginalFilename();
        // 截取图片后缀名
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        // 使用UUID拼接文件后缀名 防止文件名重复 导致被覆盖
        String newFilename = UUID.randomUUID().toString().replace("-", "") + fileExtension;
        // 创建文件对象
        File destFile = new File(System.getProperty("user.dir") + "/" + uploadPath + newFilename);
        // 判断文件的父文件夹是否存在 如果不存在 则创建
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        // 保存文件
        file.transferTo(destFile);
        // 返回文件访问路径
        String fileUrl = uploadUrl + newFilename;
        return ResultUtils.success(fileUrl);
    }
}

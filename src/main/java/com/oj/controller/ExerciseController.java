package com.oj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.oj.common.BaseResponse;
import com.oj.common.ResultUtils;
import com.oj.model.dto.ExerciseSubmitDTO;
import com.oj.model.entity.Exercise;
import com.oj.model.entity.ExerciseFinish;
import com.oj.model.vo.SearchVO;
import com.oj.service.ExerciseService;
import com.oj.utils.BaseContext;
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

    @PostMapping("/uploadVideo")
    public BaseResponse<String> uploadVideo(@RequestParam("file") MultipartFile file) throws IOException {
        // 获取上传视频的全名
        String filename = file.getOriginalFilename();
        if (filename == null || filename.isEmpty()) {
            return ResultUtils.error(400, "文件名无效");
        }
        // 截取视频后缀名
        String fileExtension = filename.substring(filename.lastIndexOf("."));
        // 你可以在这里添加对文件类型的检查
        if (!fileExtension.matches("\\.(mp4|avi|mkv|mov|flv|wmv)$")) {
            return ResultUtils.error(400, "无效的视频格式");
        }
        // 使用UUID拼接文件后缀名 防止文件名重复 导致被覆盖
        String newFilename = UUID.randomUUID().toString().replace("-", "") + fileExtension;
        // 创建文件对象
        File destFile = new File(System.getProperty("user.dir") + "/" + uploadPath + newFilename);

        // 判断文件的父文件夹是否存在 如果不存在 则创建
        if (!destFile.getParentFile().exists()) {
            if (!destFile.getParentFile().mkdirs()) {
                return ResultUtils.error(500, "创建目录失败");
            }
        }
        // 保存文件
        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            return ResultUtils.error(500, "文件上传失败: " + e.getMessage());
        }
        // 返回文件访问路径
        String fileUrl = uploadUrl + newFilename;
        return ResultUtils.success(fileUrl);
    }

    //查看发布的实验
    @GetMapping("/list_published")
    public BaseResponse<List<Exercise>> getPublishedExerciseList() {
        QueryWrapper<Exercise> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);
        return ResultUtils.success(exerciseService.list(queryWrapper));
    }

    //查看已提交的实验
    @GetMapping("/list_submitted")
    public BaseResponse<List<Exercise>> getSubmittedExerciseList() {
        return ResultUtils.success(exerciseService.getSubmittedExerciseList());
    }

    //查看未提交的实验
    @GetMapping("/list_no_submitted")
    public BaseResponse<List<Exercise>> getNoSubmittedExerciseList() {
        return ResultUtils.success(exerciseService.getNoSubmittedExerciseList());
    }

    //查看某学生已提交的实验
    @GetMapping("/list_submitted_by_id")
    public BaseResponse<List<Exercise>> getSubmittedExerciseListByStuId(Long stuId) {
        return ResultUtils.success(exerciseService.getSubmittedExerciseList(stuId));
    }
    //查看某学生未提交的实验
    @GetMapping("/list_not_submitted_by_id")
    public BaseResponse<List<Exercise>> getNoSubmittedExerciseListByStuId(Long stuId) {
        return ResultUtils.success(exerciseService.getNoSubmittedExerciseList(stuId));
    }


    //站内搜索
    @GetMapping("/search")
    public BaseResponse<List<SearchVO>> search(String searchStr) {
        return ResultUtils.success(exerciseService.search(searchStr));
    }
}

package com.mango.int20hweb.controller;

import com.mango.int20hweb.domain.enums.Emotion;
import com.mango.int20hweb.dto.ImageInfoDto;
import com.mango.int20hweb.dto.ResponseDto;
import com.mango.int20hweb.service.ImageManagementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ImageLoadController {

    private ImageManagementService imageManagementService;

    @RequestMapping(value = "/images/thumbnails", method = RequestMethod.GET)
    public ResponseDto<List<ImageInfoDto>> getThumbnailImages(@RequestParam(value = "emotion", required = false) Emotion emotion,
                                                              @RequestParam(value = "startIdx", required = false) Integer startIdx,
                                                              @RequestParam(value = "count") Integer count) {
        return ResponseDto.of(imageManagementService.getThumbnailImages(emotion, startIdx, count));
    }

    @RequestMapping(value = "/images/thumbnails/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getThumbnailImage(@RequestAttribute String imageId) {
        byte[] data = imageManagementService.getThumbnailImage(imageId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");
        headers.set("Content-Disposition", "attachment; filename=\"" + imageId + ".jpg\"");
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/images/originals/{imageId}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getOriginalImage(@RequestAttribute String imageId) {
        byte[] data = imageManagementService.getOriginalImage(imageId);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "image/jpeg");
        headers.set("Content-Disposition", "attachment; filename=\"" + imageId + ".jpg\"");
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}

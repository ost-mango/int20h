package com.mango.int20hweb.service;

import com.mango.int20hweb.domain.ImageTuple;
import com.mango.int20hweb.domain.enums.Emotion;
import com.mango.int20hweb.dto.ImageInfoDto;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ImageManagementService {

    private ConcurrentSkipListMap<Emotion, List<String>> imageRegistry;

    private ConcurrentSkipListMap<String, ImageTuple> imageCache;

    private ConcurrentSkipListMap<String, ImageInfoDto> imageInfoRegistry;

    public byte[] getThumbnailImage(String id) {
        return getImageBytes(imageCache.get(id).getThumbnail());
    }

    public byte[] getOriginalImage(String id) {
        return getImageBytes(imageCache.get(id).getOriginal());
    }

    public ImageInfoDto getImageInfo(String id) {
        return imageInfoRegistry.get(id);
    }

    public List<ImageInfoDto> getThumbnailImagesInfo(Emotion emotion, Integer startIdx, Integer count) {
        return emotion == null ? getRandomImages(count) : getThumbnailImagesInRange(emotion, startIdx == null ? 0 : startIdx, count);
    }

    public List<ImageInfoDto> getThumbnailImagesInRange(Emotion emotion, Integer startIdx, Integer count) {
        List<String> imageIdList = imageRegistry.get(emotion);
        if (startIdx > imageIdList.size()) {
            return Collections.emptyList();
        }
        if ((startIdx + count) > imageIdList.size()) {
            return getImagesInfo(imageIdList);
        } else {
            return getImagesInfo(imageIdList.subList(startIdx, startIdx + count));
        }
    }

    public List<ImageInfoDto> getRandomImages(Integer count) {

        List<String> imageIdList = imageRegistry.values().stream().flatMap(List::stream).collect(Collectors.toList());
        int imageCount = imageIdList.size();
        Set<String> imageIdSet = new HashSet<>();
        if (count > imageCount) {
            imageIdSet.addAll(imageIdList);
        } else {
            while(imageIdSet.size() != count) {
                imageIdSet.add(imageIdList.get(ThreadLocalRandom.current().nextInt(imageCount)));
            }
        }
        return getImagesInfo(imageIdSet);
    }

    private List<ImageInfoDto> getImagesInfo(Collection<String> idList) {
        return idList.stream().map(this::getImageInfo).collect(Collectors.toList());
    }

    private byte[] getImageBytes(File file) {
        byte[] data = new byte[0];
        try {
            data = IOUtils.toByteArray(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

}

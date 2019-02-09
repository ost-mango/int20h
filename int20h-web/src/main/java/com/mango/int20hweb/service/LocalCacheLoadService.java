package com.mango.int20hweb.service;

import com.mango.int20hweb.domain.ImageTuple;
import com.mango.int20hweb.domain.enums.Emotion;
import com.mango.int20hweb.dto.ImageInfoDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LocalCacheLoadService {

    private FlickrService flickrService;

    private FaceppService faceppService;

    public ConcurrentSkipListMap<Emotion, List<String>> imageRegistry;

    public ConcurrentSkipListMap<String, ImageInfoDto> imageInfoRegistry;

    public ConcurrentSkipListMap<String, ImageTuple> imageCache;

    private static final int THUMB_HEIGHT = 100;

    private static final int THUMB_WIDTH = 200;

    @PostConstruct
    public void loadLocalCache() { //todo somehow check flickr for new files
        List<File> files = flickrService.getImagesAsFile();
        log.info("Cache loading started. Going to load " + files.size() + " files.");

        flickrService.getImagesAsFile().forEach(this::putFileToCache); //todo move to separate thread
        log.info("Cache loading finished.");
    }


    private void putFileToCache(File file) {
        String id = UUID.randomUUID().toString();
        Emotion emotion = faceppService.matchEmotion(file);
        File thumbnail = file;
        try {
            thumbnail = createThumbnail(file, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ImageInfoDto imageInfoDto = new ImageInfoDto(id, emotion, THUMB_HEIGHT, THUMB_WIDTH);
        ImageTuple imageTuple = new ImageTuple(file, thumbnail, emotion);
        imageRegistry.get(emotion).add(id);
        imageInfoRegistry.put(id, imageInfoDto);
        imageCache.put(id, imageTuple);

    }

    public File createThumbnail(File file, String id) throws Exception {
        BufferedImage img = ImageIO.read(file);
        BufferedImage thumb = new BufferedImage(THUMB_HEIGHT, THUMB_WIDTH,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g2d = (Graphics2D) thumb.getGraphics();
        g2d.drawImage(img, 0, 0, thumb.getWidth() - 1, thumb.getHeight() - 1, 0, 0,
                img.getWidth() - 1, img.getHeight() - 1, null);
        g2d.dispose();
        File thumbFile = new File(id + "_thumb.png");
        ImageIO.write(thumb, "PNG", thumbFile);
        thumbFile.deleteOnExit();
        return thumbFile;
    }
}

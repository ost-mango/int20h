package com.mango.int20hweb.service;

import com.flickr4java.flickr.photos.Photo;
import com.mango.int20hweb.config.flickr.FlickrApiProperties;
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
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class LocalCacheLoadService {

    private FlickrService flickrService;

    private FlickrApiProperties flickrApiProperties;

    private FaceppService faceppService;

    public ConcurrentSkipListMap<Emotion, List<String>> imageRegistry;

    public ConcurrentSkipListMap<String, ImageInfoDto> imageInfoRegistry;

    public ConcurrentSkipListMap<String, ImageTuple> imageCache;

    @PostConstruct
    public void init() {
        new Thread(this::loadLocalCache).start();
    }

    public void loadLocalCache() { //todo somehow check flickr for new files
        long start = System.currentTimeMillis();
        log.info("Cache loading started.");
        Set<Photo> photos = flickrService.getPhotos();
        log.info("Going to load " + photos.size() + " files.");
        photos.forEach(this::putPhotoToCache);
        log.info("Cache loading finished in " + (System.currentTimeMillis() - start) + " (ms).");
    }


    private void putPhotoToCache(Photo photo) {
        String id = UUID.randomUUID().toString();

        ImageTuple imageTuple = new ImageTuple();
        File original = flickrService.getImage(photo);
        Emotion emotion = faceppService.matchEmotion(original);
        File thumbnail;
        int height;
        int width;
            try {
                BufferedImage image = ImageIO.read(original);
                height = image.getHeight() / 2;
                width = image.getWidth() / 2;
                thumbnail = createThumbnail(original, id, height, width);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        ImageInfoDto imageInfoDto = new ImageInfoDto(id, emotion, height, width);
        imageTuple.setOriginal(original);
        imageTuple.setThumbnail(thumbnail);
        imageTuple.setEmotion(emotion);
        original.deleteOnExit();
        thumbnail.deleteOnExit();

        imageTuple.setEmotion(emotion);

        imageRegistry.get(emotion).add(id);
        imageInfoRegistry.put(id, imageInfoDto);
        imageCache.put(id, imageTuple);

    }

    public File createThumbnail(File file, String id, int height, int width) throws Exception {
        BufferedImage img = ImageIO.read(file);
        BufferedImage thumb = new BufferedImage(width, height,
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

    public static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        int width  = imgA.getWidth();
        int height = imgA.getHeight();

        // Loop over every pixel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
}

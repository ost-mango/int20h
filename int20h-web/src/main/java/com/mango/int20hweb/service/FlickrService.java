package com.mango.int20hweb.service;

import com.flickr4java.flickr.Flickr;
import com.flickr4java.flickr.FlickrException;
import com.flickr4java.flickr.photos.Photo;
import com.flickr4java.flickr.photos.PhotoList;
import com.flickr4java.flickr.photos.SearchParameters;
import com.flickr4java.flickr.photos.Size;
import com.flickr4java.flickr.photosets.Photoset;
import com.flickr4java.flickr.tags.Cluster;
import com.mango.int20hweb.config.flickr.FlickrApiProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class FlickrService {

    private Flickr flickr;

    private FlickrApiProperties flickrApiProperties;


    public List<File> getImagesAsFile(){
       return getPhotos().stream()
               .map(photo -> getImage(photo, String.format(flickrApiProperties.getOutputPhotoDir() + "\\file%s.jpg", Math.random())))
               .collect(Collectors.toList());
    }

    private Set<Photo> getPhotos(){
        Set<Photo> photoList = new LinkedHashSet<>();
        for (List<String> list : flickrApiProperties.getPhotosetIdListByUserId().values()) {
            photoList.addAll(list.stream().flatMap(photosetId -> getPhotoListByPhotoset(photosetId).stream()).collect(Collectors.toList()));
        }
        photoList.addAll(getPhotoListByTags(flickrApiProperties.getTagList()));
        return photoList;
    }


    private File getImage(Photo photo, String pathname){
        System.out.println(1);
        File outputFile = new File(pathname);
        try {
            ImageIO.write(flickr.getPhotosInterface().getImage(photo,Size.LARGE),"jpg", outputFile);
        } catch (FlickrException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputFile;
    }


    private Set<Photo> getPhotoListByPhotoset(String photosetId) {
        Set<Photo> photoSet = new LinkedHashSet<>();
        int pageCount = 0;
        while (true) {
            try {
                List list = flickr.getPhotosetsInterface().getPhotos(photosetId, 100, pageCount);
                if (list.size() == 0) {
                    break;
                }
                photoSet.addAll(list);
                pageCount++;
            } catch (FlickrException e) {
                e.printStackTrace();
                break;
            }
        }
        return photoSet;
    }


    private Set<Photo> getPhotoListByTags(String[] tags) {
        SearchParameters searchParameters = new SearchParameters();
        searchParameters.setTags(tags);
        Set<Photo> photoList = new LinkedHashSet<>();
        int pageCount = 0;
        while (true) {
            try {
                List list = flickr.getPhotosInterface().search(searchParameters, 100, pageCount);
                if (list.size() == 0) {
                    break;
                }
                photoList.addAll(list);
                pageCount++;
            } catch (FlickrException e) {
                e.printStackTrace();
                break;
            }
        }
        return photoList;
    }

}




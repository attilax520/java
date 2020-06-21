package com.kok.sport.utils.constant;

import com.kok.auth.config.AntUrlManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class AntUrlConstants implements AntUrlManager {
    public static final Set<String> URL = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(
                    "/sport/player/movie/getMovieByCategory"
                    , "/sport/player/submovie/getWorkId"
            )));
    @Override
    public Set<String> whiteList() {
        return URL;
    }
}

package com.apirest.webflux.controller;

import com.apirest.webflux.document.Playlist;
import com.apirest.webflux.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.time.Duration;
import java.time.LocalDateTime;

//@RestController
public class PlaylistController {

    @Autowired
    PlaylistService service;

    @GetMapping(value = "/playlist")
    public Flux<Playlist> getPlaylist() {
        return service.findAll();
    }

    @GetMapping(value = "/playlist/{id}")
    public Mono<Playlist> getPlaylistId(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping(value = "/playlist")
    public Mono<Playlist> save(@RequestBody Playlist playlist) {
        return service.save(playlist);
    }

    @GetMapping(value="/playlist/webflux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tuple2<Long, Playlist>> getPlaylistByWebflux(){

        System.out.println("---Start get Playlists by WEBFLUX--- " + LocalDateTime.now());
        Flux<Long> interval = Flux.interval(Duration.ofSeconds(5));
        Flux<Playlist> playlistFlux = service.findAll();

        return Flux.zip(interval, playlistFlux);

    }
}

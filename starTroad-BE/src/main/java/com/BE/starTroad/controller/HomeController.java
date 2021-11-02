package com.BE.starTroad.controller;

import com.BE.starTroad.config.JwtTokenUtil;
import com.BE.starTroad.domain.Roadmap;
import com.BE.starTroad.domain.User;
import com.BE.starTroad.repository.SpringDataJpaRoadmapRepository;
import com.BE.starTroad.repository.SpringDataJpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private SpringDataJpaUserRepository springDataJpaUserRepository;
    @Autowired
    private SpringDataJpaRoadmapRepository springDataJpaRoadmapRepository;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/")
    public ResponseEntity<List<Roadmap>> home(@RequestHeader("Authorization") String token) {

        token = token.substring(7);
        String tokenOwner = jwtTokenUtil.getUsernameFromToken(token);

        Optional<User> user = springDataJpaUserRepository.findByEmail(tokenOwner);
        if (user.isPresent()) {
            String tags = user.get().getInterest();
            if (tags != null) {
                List<Roadmap> roadmaps = springDataJpaRoadmapRepository.findByTagLike(tags);
                if (roadmaps.isEmpty()) { //태그에 해당하는게 없으면
                    roadmaps = springDataJpaRoadmapRepository.findAll();
                    return new ResponseEntity<List<Roadmap>>(roadmaps, HttpStatus.OK);
                }
            }
            else {
                List<Roadmap> roadmaps = springDataJpaRoadmapRepository.findAll();
                return new ResponseEntity<List<Roadmap>>(roadmaps, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

}

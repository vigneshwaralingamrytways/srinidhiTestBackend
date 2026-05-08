package com.rytways.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.ReleaseTrack;
import com.rytways.repository.ReleaseTrackRepo;
import com.rytways.service.ReleaseTrackService;

@RestController
@RequestMapping("/releaseTrack")
public class ReleaseTrackController {
	
	@Autowired
	private ReleaseTrackService releaseTrackService;
	
	@Autowired
	private ReleaseTrackRepo releaseTrackRepo;


	@PostMapping("/create")
	public ResponseEntity <ReleaseTrack> createReleaseTrack (@RequestBody ReleaseTrack doc){
		
		doc=releaseTrackService.createRelease(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
	}
	
	

	
	
	@PostMapping("/loadTrack")
	public ResponseEntity<List<ReleaseTrack>> loadReleaseTrack(@RequestBody ReleaseTrack doc) {
	    List<ReleaseTrack> list = releaseTrackService.loadDetails(doc);
	    return new ResponseEntity<>(list, HttpStatus.OK);
	}


	
	@PostMapping("/loadTrackById")
	public ResponseEntity<ReleaseTrack> loadReleaseTracks(@RequestBody ReleaseTrack doc) {
	    Optional<ReleaseTrack> optionalTrack = releaseTrackService.loadDetailsById(doc);
	    return optionalTrack
	        .map(track -> new ResponseEntity<>(track, HttpStatus.OK))
	        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	 @PostMapping("/update")
	    public ResponseEntity<ReleaseTrack> updateReleaseTrack(@RequestBody ReleaseTrack doc) {
	        try {
	            ReleaseTrack updatedTrack = releaseTrackService.updateTrack(doc);
	            return ResponseEntity.ok(updatedTrack);
	        } catch (RuntimeException e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }

	 
	 
	 @PostMapping("/deleteById")
	    public ResponseEntity<String> deleteReleaseTrackById(@RequestBody ReleaseTrack doc) {
	     

	        boolean deleted = releaseTrackService.deleteById(doc.getReleaseTrackId());

	        if (deleted) {
	            return ResponseEntity.ok("Deleted successfully");
	        } else {
	            return ResponseEntity.status(HttpStatus.CONFLICT)
	                    .body("Cannot delete: Related items exist or record not found");
	        }
	    }

	 
}

package com.rytways.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rytways.model.ReleaseTrackItemComments;
import com.rytways.model.ReleaseTrackItems;
import com.rytways.repository.ReleaseTrackItemCommentsRepo;
import com.rytways.repository.ReleaseTrackItemsRepo;
import com.rytways.service.ReleaseTrackItemsService;

@RestController
@RequestMapping("/releaseTrackItem")
public class ReleaseTrackItemsController {
	
	@Autowired
	private ReleaseTrackItemsService RrleaseTrackItemsService;
	
	@Autowired
	private ReleaseTrackItemsRepo releaseTrackItemsRepo;

	@Autowired
	private ReleaseTrackItemCommentsRepo releaseTrackItemCommentsRepo;
	
	
	@PostMapping("/create")
	public ResponseEntity <ReleaseTrackItemComments> createFunction (@RequestBody ReleaseTrackItemComments doc){
		
		doc=RrleaseTrackItemsService.createComments(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
		
	}
	
	@PostMapping("/createTrackItem")
	public ResponseEntity <ReleaseTrackItems> createItem (@RequestBody ReleaseTrackItems doc){
		
		doc=RrleaseTrackItemsService.createCommentItem(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
		
	}
	
	@PostMapping("/updateTrackItem")
	public ResponseEntity <ReleaseTrackItems> createItems (@RequestBody ReleaseTrackItems doc){
		
		doc=RrleaseTrackItemsService.updateCommentItem(doc);
		
		return new ResponseEntity <>(doc,HttpStatus.OK);
		
	}
		
		@PostMapping("/loadTrackItem")
		public ResponseEntity<List<ReleaseTrackItems>> loadReleaseTrackItem(@RequestBody ReleaseTrackItems doc) {
		    List<ReleaseTrackItems> list = RrleaseTrackItemsService.loadRackItemDetails(doc);
		    return new ResponseEntity<>(list, HttpStatus.OK);
		}

	
	
		
		@PostMapping("/loadTrackItemComments")
		public ResponseEntity<List<ReleaseTrackItemComments>> loadReleaseTrackItemComments(@RequestBody ReleaseTrackItemComments doc) {
		    List<ReleaseTrackItemComments> list = RrleaseTrackItemsService.loadRackItemDetailCom(doc);
		    return new ResponseEntity<>(list, HttpStatus.OK);
		}

	
		@PostMapping("/deleteTrackItem")
		public ResponseEntity<String> deleteTrackItem(@RequestBody ReleaseTrackItems doc) {
		    try {
		        RrleaseTrackItemsService.deleteTrackItemById(doc);
		        return new ResponseEntity<>("Track Item deleted successfully", HttpStatus.OK);
		    } catch (Exception e) {
		        return new ResponseEntity<>("Track Item not found", HttpStatus.NOT_FOUND);
		    }
		}
	
}

package com.rytways.service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.model.ActivityMaster;
import com.rytways.model.ReleaseTrackItemComments;
import com.rytways.model.ReleaseTrackItems;
import com.rytways.repository.ActivityMasterRepo;
import com.rytways.repository.ReleaseTrackItemCommentsRepo;
import com.rytways.repository.ReleaseTrackItemsRepo;

@Component
@Service
@Transactional
public class ReleaseTrackItemsService {
	
	@Autowired
	private ReleaseTrackItemsRepo releaseTrackItemsRepo;
	@Autowired
	private ReleaseTrackItemCommentsRepo releaseTrackItemCommentsRepo;
	

	@Autowired
	private ActivityMasterRepo activityMasterRepo;
	
	
	public ReleaseTrackItemComments createComments(ReleaseTrackItemComments doc) {
		// TODO Auto-generated method stub
		return releaseTrackItemCommentsRepo.save(doc);
	}
//	public List<ReleaseTrackItems> loadRackItemDetails(ReleaseTrackItems doc) {
//		
//		List<ReleaseTrackItems> list =  releaseTrackItemsRepo.findByReleaseTrackId(doc.getReleaseTrackId());
//
//		return list;
//	}
	
	
//	public List<ReleaseTrackItems> loadRackItemDetails(ReleaseTrackItems doc) {
//	    return releaseTrackItemsRepo.findByReleaseTrackIdOrderByDisplaySeq(doc.getReleaseTrackId());
//	}
	
	public List<ReleaseTrackItems> loadRackItemDetails(ReleaseTrackItems doc) {
	    List<ReleaseTrackItems> items = releaseTrackItemsRepo.findByReleaseTrackId(doc.getReleaseTrackId());

	    return items.stream()
	        .sorted(Comparator.comparing(item -> item.getDisplaySeqNo() != null ? item.getDisplaySeqNo() : 0))
	        .collect(Collectors.toList());
	}



	public ReleaseTrackItems createCommentItem(ReleaseTrackItems doc) {
	    // Save the initial item
	    ReleaseTrackItems item = releaseTrackItemsRepo.save(doc);

	    // Fetch the related ActivityMaster (ensure null check)
	    Optional<ActivityMaster> optionalAct = activityMasterRepo.findById(doc.getActivityId());
	    if (optionalAct.isPresent()) {
	        item.setActivity(optionalAct.get());

	        // Save again to persist the updated activity
	        item = releaseTrackItemsRepo.save(item);
	    } else {
	        // Handle the case where ActivityMaster is not found
	        throw new RuntimeException("Activity not found with id: " + doc.getActivityId());
	    }

	    return item;
	}
	public List<ReleaseTrackItemComments> loadRackItemDetailCom(ReleaseTrackItemComments doc) {
		
		List<ReleaseTrackItemComments> list =  releaseTrackItemCommentsRepo.findByTrackItemId(doc.getTrackItemId());

		return list;
	}
	public ReleaseTrackItems updateCommentItem(ReleaseTrackItems doc) {
		
//		doc.setActivity(null);
//		return releaseTrackItemsRepo.save(doc);
		
		doc.setActivity(null);
		if (doc.getStatusName() == null || doc.getStatusName().isEmpty())
			doc.setStatusName("Created");

		Optional<ActivityMaster> activityOpt = activityMasterRepo.findById(doc.getActivityId());
		doc = releaseTrackItemsRepo.save(doc);
		doc.setActivity(activityOpt.get());
		return doc;
	}


	public String deleteTrackItemById(ReleaseTrackItems doc) {
	    Long id = doc.getTrackItemId(); // Or the correct ID getter

	    // Check if comments exist for the track item
	    List<ReleaseTrackItemComments> comments = releaseTrackItemCommentsRepo.findByTrackItemId(id);
	    
	    if (comments != null && !comments.isEmpty()) {
	        return "Cannot delete: Comments exist for this track item";
	    }

	    // If no comments, proceed to delete
	    releaseTrackItemsRepo.deleteById(id);
	    return "Track item deleted successfully";
	}

	

}

package com.rytways.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rytways.model.IssueSolutionComments;
import com.rytways.model.ReleaseTrack;
import com.rytways.repository.ReleaseTrackItemsRepo;
import com.rytways.repository.ReleaseTrackRepo;


@Component
@Service
@Transactional
public class ReleaseTrackService {
	
	@Autowired
	private ReleaseTrackRepo releaseTrackRepo;

	@Autowired
	private ReleaseTrackItemsRepo releaseTrackItemsRepo;
	

	public List<ReleaseTrack> loadDetails(ReleaseTrack doc) {
	    return releaseTrackRepo.findAll();
	}



	public ReleaseTrack createRelease(ReleaseTrack doc) {
		// TODO Auto-generated method stub
		return releaseTrackRepo.save(doc);
	}



	public Optional<ReleaseTrack> loadDetailsById(ReleaseTrack doc) {
	    return releaseTrackRepo.findById(doc.getReleaseTrackId());
	}



	public ReleaseTrack updateTrack(ReleaseTrack doc) {
	    Optional<ReleaseTrack> optionalData = releaseTrackRepo.findById(doc.getReleaseTrackId());

	    if (optionalData.isPresent()) {
	        ReleaseTrack data = optionalData.get();
	        data.setRelObjective(doc.getRelObjective()); // Assuming the field name is relObjective
	        data.setReleaseNote(doc.getReleaseNote());
	        return releaseTrackRepo.save(data);
	    } else {
	        throw new RuntimeException("ReleaseTrack not found with ID: " + doc.getReleaseTrackId());
	    }
	}



	 public boolean deleteById(Long trackId) {
        if (!releaseTrackRepo.existsById(trackId)) {
            return false;
        }

        boolean hasItems = releaseTrackItemsRepo.existsByReleaseTrackId(trackId);
        if (hasItems) {
            return false;
        }

        releaseTrackRepo.deleteById(trackId);
        return true;
    }


}

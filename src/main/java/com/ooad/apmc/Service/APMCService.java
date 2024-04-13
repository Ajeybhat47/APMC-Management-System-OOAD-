package com.ooad.apmc.Service;

import com.ooad.apmc.DTOModels.AuctionDTO;
import com.ooad.apmc.Models.APMC;
import com.ooad.apmc.Models.Auction;
import com.ooad.apmc.Repository.APMCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class APMCService {

    @Autowired
    private APMCRepository apmcRepository;

    @Autowired
    private AuctionService auctionService;

    public List<AuctionDTO> getAuctions() {
        // Retrieve the APMC entity from the repository
        Long id = 1L;
        APMC apmc = apmcRepository.findById(id).orElse(null);
        
        List<Auction> auct = apmc.getAuctions();
         // Retrieve and return auction DTOs for the associated auctions
        return auct.stream().map(AuctionDTO::mapEntityToDto).collect(Collectors.toList());
    }
}

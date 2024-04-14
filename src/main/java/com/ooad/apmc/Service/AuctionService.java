package com.ooad.apmc.Service;

import java.util.stream.Collectors;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ooad.apmc.DTOModels.AuctionDTO;
import com.ooad.apmc.DTOModels.BidDTO;
import com.ooad.apmc.DTOModels.UserDTO;
import com.ooad.apmc.Models.Auction;
import com.ooad.apmc.Models.Bid;
import com.ooad.apmc.Models.Item;

import com.ooad.apmc.Repository.AuctionRepository;
// import java.util.Optional;
import java.util.NoSuchElementException;

@Transactional
@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ItemService itemService;

    @Transactional
    public String createAuction(Auction auction, Long itemId) {
        try {
            Item item = itemService.getItemById(itemId);
            if (item == null) {
                throw new IllegalArgumentException("Item with ID " + itemId + " not found");
            }
            if(item.isSold())
            {
                throw new IllegalArgumentException("Item with ID " + itemId + " is already sold");
            }

            auction.setItem(item);
            auction.setStatus("active");
            auctionRepository.save(auction);
            return "Auction created successfully";
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return "Failed to create auction: " + e.getMessage();
        }
    }
    public Long getSellerIdFromAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
            .orElseThrow(() -> new IllegalArgumentException("Auction with ID " + auctionId + " not found"));
        Item item = auction.getItem();
        if (item == null) {
            throw new IllegalArgumentException("Item for Auction with ID " + auctionId + " not found");
        }
        return item.getSeller().getUserId();
    }

    public Auction saveAuction(Auction auction) {
        // Save the auction
        Auction savedAuction = auctionRepository.save(auction);

        // Return the saved auction
        return savedAuction;
    }
    public AuctionDTO getAuctionById(Long auctionId) {
        try {
            Auction auction = auctionRepository.getReferenceById(auctionId);
            checkStatus(auction);
            return AuctionDTO.mapEntityToDto(auction);
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return null;
        }

    }

    public List<AuctionDTO> getAllAuctions() {
        try {
            List<Auction> auctions = auctionRepository.findAll();


            // check status of each auction

            for(Auction auction : auctions)
            {
                checkStatus(auction);
            }


           auctions = auctionRepository.findAll();


            return auctions.stream().map(AuctionDTO::mapEntityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return null;
        }
    }

    public List<AuctionDTO> getAllAuctionsByStatus(String status) {
        try {
            List<Auction> auctions = auctionRepository.findAllByStatus(status);
            // check status
            for(Auction auction : auctions)
            {
                checkStatus(auction);
            }
            return auctions.stream().map(AuctionDTO::mapEntityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return null;
        }
    }

    public List<BidDTO> getAllBids(Long auctionId) {
        try {
            Auction auction = auctionRepository.findById(auctionId).orElse(null);
            if (auction == null) {
                throw new IllegalArgumentException("Auction not found with ID: " + auctionId);
            }
            return auction.getBids().stream().map(BidDTO::mapEntityToDto).collect(Collectors.toList());
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    public String closeAuction(Long auctionId) {
        try {
            Auction auction = auctionRepository.getReferenceById(auctionId);
            auction.setStatus("closed");
            setWinner(auctionId);
            if(auction.getWinner() != null)
            {
                auction.getItem().setSoldPrice(auction.getWinningBid().getBidAmount());
                auction.getItem().setSold(true);
                auction.getWinningBid().setBidStatus("accepted");
                // set all other bisd in the auction to rejected
                for(Bid bid : auction.getBids())
                {
                    if(bid != auction.getWinningBid())
                    {
                        bid.setBidStatus("rejected");
                    }
                }
            }

            auctionRepository.save(auction);
            return "Auction closed successfully";

        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return "Failed to close auction: " + e.getMessage();
        }
    }

    private UserDTO setWinner(Long auctionId) {
        try {
            Auction auction = auctionRepository.findById(auctionId).orElse(null);
            if (auction == null) {
                throw new IllegalArgumentException("Auction not found with ID: " + auctionId);
            }

            if(!auction.getStatus().equals("closed")){
                throw new IllegalArgumentException("Auction is not closed yet");
            }

            if (auction.getWinner() != null) {
                return UserDTO.mapEntityToDto(auction.getWinner());
            } else {
                findWinner(auction);
                if (auction.getWinner() != null) {
                    return UserDTO.mapEntityToDto(auction.getWinner());
                } else {
                    return null;
                }
            }
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
            return null;
        }
    }

    public UserDTO getWinner(Long auctionId)
    {
        Auction auction = auctionRepository.getReferenceById(auctionId);

        if(!auction.getStatus().equalsIgnoreCase("closed"))
        {
            return null;
        }

        return new UserDTO(auction.getWinner());
    }

    private void findWinner(Auction auction) {
        List<Bid> bids = auction.getBids();
        try {

            if (bids.isEmpty()) {
                System.out.println("No bids found in auction");

            }
            double maxamt = 0;
            Bid winningBid = null;

            for (Bid bid : bids) {
                if (bid.getBidAmount() > maxamt) {
                    maxamt = bid.getBidAmount();
                    winningBid = bid;
                }
            }
            if(winningBid != null){
                auction.setWinner(winningBid.getBidder());
                auction.setWinningBid(winningBid);
            }


        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace();
        }
    }

    public void checkStatus(Auction auction )
    {
        // get current time check closing time and modify status
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(auction.getClosingTime()))
        {
            auction.setStatus("closed");
            closeAuction(auction.getAuctionId());
        }
    }
    public Auction getAuctionByItemId(Long itemId) {
        return auctionRepository.findByItem_ItemId(itemId)
            .orElseThrow(() -> new NoSuchElementException("No auction found for item ID: " + itemId));
    }

}



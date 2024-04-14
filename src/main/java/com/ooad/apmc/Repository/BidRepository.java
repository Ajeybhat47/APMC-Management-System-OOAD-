package com.ooad.apmc.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ooad.apmc.Models.Bid;
import com.ooad.apmc.Models.*;
public interface BidRepository extends JpaRepository<Bid, Long> {

    @Query("SELECT b FROM Bid b WHERE b.auction.auctionId = :auctionId")
    List<Bid> findByAuctionId(Long auctionId);

    @Query("SELECT b FROM Bid b WHERE b.bidder.userId = :userId AND b.auction.auctionId = :auctionId")
    Bid findByBidderId(Long userId,Long auctionId);
    List<Bid> findByItemOrderByBidAmountDesc(Item item);

    List<Bid> findByAuctionOrderByBidAmountDesc(Auction auction);

}

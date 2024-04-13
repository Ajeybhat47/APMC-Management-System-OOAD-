package com.ooad.apmc.Models;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class APMC {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apmc_id")
    private Long auctionId;

    @OneToMany(mappedBy = "apmc")
    private List<Auction> auctions;

    // constructor getter setter

    public APMC() {
    }

    public APMC(List<Auction> auctions) {
        this.auctions = auctions;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }


    public List<Auction> getAuctions() {
        return auctions;
    }

    public void setAuctions(List<Auction> auctions) {
        this.auctions = auctions;
    }

}

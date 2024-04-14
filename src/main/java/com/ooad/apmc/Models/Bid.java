package com.ooad.apmc.Models;


import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "bid")
public class Bid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bid_id")
    private Long bidId;

    @Column(name = "bid_amount")
    private Double bidAmount;

    @Column(name = "bid_time")
    private LocalDateTime bidTime;


    @Column(name = "bid_status")
    private String bidStatus;

    @ManyToOne
    @JoinColumn(name = "bidder_id")
    private Trader bidder;

    @ManyToOne(fetch = FetchType.LAZY) // FetchType.LAZY for lazy loading
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    // Constructors
    public Bid() {
    }

    public Bid(Trader bidder, Double bidAmount, LocalDateTime bidTime, Auction auction) {
        this.bidder = bidder;
        this.bidAmount = bidAmount;
        this.bidTime = bidTime;
        this.auction = auction;
    }

    // Getters and setters
    public Long getBidId() {
        return bidId;
    }

    public void setBidId(Long bidId) {
        this.bidId = bidId;
    }

    public Trader getBidder() {
        return bidder;
    }

    public void setBidder(Trader bidder) {
        this.bidder = bidder;
    }

    public Double getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(Double bidAmount) {
        this.bidAmount = bidAmount;
    }

    public LocalDateTime getBidTime() {
        return bidTime;
    }

    public void setBidTime(LocalDateTime bidTime) {
        this.bidTime = bidTime;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getBidStatus() {
        return bidStatus;
    }

    public void setBidStatus(String bidStatus) {
        this.bidStatus = bidStatus;
    }

    // ToString method
    @Override
    public String toString() {
        return "Bid{" +
                "bidId=" + bidId +
                ", bidder=" + bidder +
                ", bidAmount=" + bidAmount +
                ", bidTime=" + bidTime +
                ", auction=" + auction +
                '}';
    }
}

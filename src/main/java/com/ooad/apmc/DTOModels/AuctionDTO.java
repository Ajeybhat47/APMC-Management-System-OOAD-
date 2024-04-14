package com.ooad.apmc.DTOModels;
import com.ooad.apmc.Models.User;
import com.ooad.apmc.Models.Auction;

public class AuctionDTO {

    private Long auctionId;
    private String status;
    private Double initialPrice;
    private Long itemId;
    private String itemName;
    private String itemDescription;
    private Long winnerId;

    public AuctionDTO() {
    }

    public AuctionDTO(Long auctionId, String status, Double initialPrice, Long itemId, Long winnerId, String itemName, String itemDescription) {

        this.auctionId = auctionId;
        this.status = status;
        this.initialPrice = initialPrice;
        this.itemId = itemId;
        this.winnerId = winnerId;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
    }

    public Long getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Long auctionId) {
        this.auctionId = auctionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    private Long sellerId;

    public Long getSellerId() {
        return sellerId;
    }

    private User seller;

    public User getSeller() {
        return seller;
    }

    public static AuctionDTO mapEntityToDto(Auction auction) {
        AuctionDTO auctionDTO;
        if(auction.getWinner() != null) {
            auctionDTO = new AuctionDTO(auction.getAuctionId(), auction.getStatus(), auction.getBasePrice(), auction.getItem().getItemId() , auction.getWinner().getUserId(), auction.getItem().getItemName(), auction.getItem().getDescription());
        } else {
            auctionDTO = new AuctionDTO(auction.getAuctionId(), auction.getStatus(), auction.getBasePrice(), auction.getItem().getItemId() , null, auction.getItem().getItemName(), auction.getItem().getDescription());
        }
        auctionDTO.seller = auction.getItem().getSeller();
        return auctionDTO;
    }
}
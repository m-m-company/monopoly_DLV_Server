package com.monopoly_DLV_Server.DLV_Server.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Trade {
    int initiator;
    int recipient;
    int money;
    ArrayList<Integer> propertyOffered;
    ArrayList<Integer> propertyRequested;
    int communityChestJailCard;
    int chanceJailCard;
}

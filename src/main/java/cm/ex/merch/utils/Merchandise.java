package cm.ex.merch.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Merchandise {
    private String name;
    private String gameTitle;
    private String description;
    private String brand;
    private double price;
    private int quantity;
    private String status;
    private String category;
}


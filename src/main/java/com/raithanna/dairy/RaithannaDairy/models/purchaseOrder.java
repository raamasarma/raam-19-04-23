package com.raithanna.dairy.RaithannaDairy.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class purchaseOrder {
    @Id
    @GeneratedValue
    private Integer id;

    private LocalDate invDate;
    private Double quantity;
    private Double fatP;
    private Double snfP;
    private Double tsRate;
    private String code;
    private Double ltrRate;
    private String milkType;
    private String supplier;
    private String invNo;
    private LocalDate recDate;
    private Integer slNo;
    private Double amt;
    private String bankName;
    private String paymentStatus;
   // private String VehicleNo;
    //private String format;

}

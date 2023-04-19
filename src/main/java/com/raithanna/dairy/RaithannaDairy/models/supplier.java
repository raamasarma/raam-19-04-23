package com.raithanna.dairy.RaithannaDairy.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Data
public class supplier {
    @Id
@GeneratedValue
    private Integer id;
    private String supplierName;
   private String supplierCode;

}

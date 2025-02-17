package com.marouanedbibih.hotel_management.payment;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import com.marouanedbibih.hotel_management.invoice.Invoice;
import com.marouanedbibih.hotel_management.utils.BasicEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "payments")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Payment extends BasicEntity {
    private BigDecimal amountPayed;
    private LocalDate paymentDate;

    private PaymentMethod paymentMethod;

    // Invoice
    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

}

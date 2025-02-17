package com.marouanedbibih.riyadmanager.payment;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import com.marouanedbibih.riyadmanager.invoice.Invoice;
import com.marouanedbibih.riyadmanager.utils.BasicEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "payments")
@Data
@SuperBuilder
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

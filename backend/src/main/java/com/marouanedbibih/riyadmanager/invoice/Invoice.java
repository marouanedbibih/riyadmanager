package com.marouanedbibih.riyadmanager.invoice;

import java.math.BigDecimal;

import com.marouanedbibih.riyadmanager.payment.Payment;
import com.marouanedbibih.riyadmanager.reservation.Reservation;
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
@Table(name = "invoices")
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Invoice extends BasicEntity{
    // Amounts
    private BigDecimal totalAmount;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;

    // Status
    private InvoiceStatus status;

    // Reservation
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    // Payment
    @OneToOne(mappedBy = "invoice")
    private Payment payment;

}

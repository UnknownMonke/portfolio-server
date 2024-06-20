package portfolioserver.core.equity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import portfolioserver.common.enums.Currency;
import portfolioserver.common.enums.Product;
import portfolioserver.core.geography.GeographyExposure;
import portfolioserver.core.sector.SectorExposure;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_EQUITY")
public class Equity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated.
    private Long id;

    @Column(name = "BROKER_ID")
    private String brokerId;

    @Column(name = "NAME", length = 48)
    private String name;

    @Column(name = "TICKER", length = 4, unique = true)
    private String ticker;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING) // Will convert enum values to their String equivalent for mapping.
    private Product type;

    @Column(name = "ACTIVE")
    private boolean active;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE", columnDefinition = "DECIMAL(8,2)")
    private BigDecimal price;

    @Column(name = "SOURCE", length = 48)
    private String source;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "EQUITY_ID")
    private List<GeographyExposure> geographyExposures; // Can be empty.

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "EQUITY_ID")
    private List<SectorExposure> sectorExposures; // Can be empty.


    public Equity(
        String brokerId,
        String name,
        String ticker,
        Product type,
        boolean active,
        Currency currency,
        Integer quantity,
        BigDecimal price,
        String source
    ) {
        this.brokerId = brokerId;
        this.name = name;
        this.ticker = ticker;
        this.type = type;
        this.active = active;
        this.currency = currency;
        this.quantity = quantity;
        this.price = price;
        this.source = source;
    }
}

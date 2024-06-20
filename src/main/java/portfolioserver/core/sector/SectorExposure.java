package portfolioserver.core.sector;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_SEC_EXPOSURE")
public class SectorExposure {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated.
    private Long id;

    @Column(name = "EXPOSURE", precision = 5, scale = 4) // Analog to columnDefinition = "DECIMAL(5,4)".
    private BigDecimal exposure;
}

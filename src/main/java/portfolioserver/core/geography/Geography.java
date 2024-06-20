package portfolioserver.core.geography;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_GEOGRAPHY_NODE")
public class Geography {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated.
    private Long id;

    @Column(name = "NAME", length = 48, unique = true) // Analog to columnDefinition = "VARCHAR(48)".
    private String name;


    // One record of this entity can be associated with many exposures.
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "NODE_ID")
    private List<GeographyExposure> exposures; // Can be empty.


    public Geography(String name) {
        this.name = name;
    }
}

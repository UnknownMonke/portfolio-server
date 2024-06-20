package portfolioserver.core.sector;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_SECTOR_NODE")
public class Sector {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated.
    private Long id;

    @Column(name = "NAME", length = 48, unique = true)
    private String name;

    @Column(name = "LEVEL")
    private Integer level;

    @Column(name = "PARENT_ID")
    private Integer parentId;


    // One record of this entity can be associated with many exposures.
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "NODE_ID")
    private List<SectorExposure> exposures; // Can be empty.


    public Sector(String name, Integer level, Integer parentId) {
        this.name = name;
        this.level = level;
        this.parentId = parentId;
    }
}
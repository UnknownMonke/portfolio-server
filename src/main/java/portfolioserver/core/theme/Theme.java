package portfolioserver.core.theme;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <i>Notes :</i> The theme entity cannot access users referencing this theme.
 * The relationship is <b>unidirectional</b>.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_THEME")
public class Theme {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated.
    private Long id;

    @Column(name = "NAME", length = 48, unique = true)
    private String name;


    public Theme(String name) {
        this.name = name;
    }
}

package portfolioserver.core.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import portfolioserver.core.theme.Theme;

/**
 * <i>Notes :</i> Users can be added with a null theme. The nullable param is set to true by default.
 * <br>
 * <br>
 * There is no default theme mechanism.
 * Also, the whole user is passed to update in the request.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "T_USER") // Table name should be prefixed.
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generated.
    private Long id;

    @Column(name = "USERNAME", length = 48)
    private String username;

    @Column(name = "PASSWORD", length = 48)
    private String password;

    @Column(name = "EMAIL", length = 48, unique = true)
    private String email;


    // Multiple records of this entity can be associated with a single theme.
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL) // Keeps the parent entity but sets orphan to null.
    @JoinColumn(name = "THEME_ID") // Column to execute the join on.
    private Theme theme;


    public User(String username, String password, String email, Theme theme) {
        this.username = username;
        this.password = password;
        this.email= email;
        this.theme = theme;
    }
}

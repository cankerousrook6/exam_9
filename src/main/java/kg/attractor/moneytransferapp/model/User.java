package kg.attractor.moneytransferapp.model;

import jakarta.persistence.*;
import lombok.*;
import kg.attractor.moneytransferapp.model.enums.UserRole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
            nullable = false,
            unique = true,
            length = 50
    )
    private String username;

    @Column(
            nullable = false
    )
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(
            nullable = false,
            length = 20
    )
    private UserRole role;
}
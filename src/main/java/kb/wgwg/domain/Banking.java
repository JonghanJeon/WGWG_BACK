package kb.wgwg.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "BANKING_ENTITY_SEQUENCE_GENERATOR",
        sequenceName = "BANKING_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Banking {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BANKING_ENTITY_SEQUENCE_GENERATOR")
    private Long bankingId;


    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "user_seq")
    private User owner;

    @ColumnDefault("0")
    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private String type;

    @Column(name = "BANKING_DATE")
    private LocalDateTime bankingDate;

    //@JoinColumn(name = "challenge_id")
    //private Long challengeId;

    @Column(nullable = false)
    private String category;


}

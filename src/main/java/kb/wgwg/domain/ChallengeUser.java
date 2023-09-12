package kb.wgwg.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@SequenceGenerator(
        name = "CHALLENGE_USER_ENTITY_SEQUENCE_GENERATOR",
        sequenceName = "CHALLENGE_USER_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class ChallengeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CHALLENGE_USER_ENTITY_SEQUENCE_GENERATOR")
    @Column(name = "challenge_user_id")
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User participant;

    @Column(name = "is_success")
    private boolean isSuccess = false;

    public void addParticipant(Challenge theChallenge) {
        this.challenge = theChallenge;
        theChallenge.getParticipants().add(this);
    }

    @Builder
    public ChallengeUser() {
    }
}

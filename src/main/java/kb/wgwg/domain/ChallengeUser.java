package kb.wgwg.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false)
    private String account;

    // true -> 1, false -> 0
    // N_Challenge 의 경우 최초 생성 혹은 최초 참여 시점에 기본 값이 true 여야 함
    // Coffee_Challenge 의 경우 최초 생성 혹은 최초 참여 시점에 기본 값이 false 여야 함
    @Column(name = "is_success")
    private boolean isSuccess;

    @Column(nullable = false)
    private String challengeType;

    public void addParticipant(Challenge theChallenge) {
        this.challenge = theChallenge;
        theChallenge.getParticipants().add(this);
    }

    @Builder
    public ChallengeUser(String account, boolean isSuccess, String challengeType) {
        this.account = account;
        this.isSuccess = isSuccess;
        this.challengeType = challengeType;
    }
}

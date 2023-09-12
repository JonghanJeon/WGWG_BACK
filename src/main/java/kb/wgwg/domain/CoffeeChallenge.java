package kb.wgwg.domain;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(
        name = "challengeId",
        column = @Column(name = "COFFEE_CHALLENGE_ID")
)
@DiscriminatorValue(value = "COFFEE")
public class CoffeeChallenge extends Challenge {

    private int totalAsset;

    @Builder
    public CoffeeChallenge(String title, String description, String status,
                           LocalDateTime startDate, LocalDateTime endDate, int totalAsset)
    {
        super(title, description, status, startDate, endDate);
        this.totalAsset = totalAsset;
    }
}

package kb.wgwg.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Getter
@ToString
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(
        name = "ARTICLE_ENTITY_SEQUENT_GENERATOR",
        sequenceName = "ARTICLE_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
public class Article {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ARTICLE_ENTITY_SEQUENT_GENERATOR")
    private Long articleSeq;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String category;

    @CreationTimestamp
    @Column(name = "INSERT_DATE")
    private LocalDateTime insertDate;

    @UpdateTimestamp
    @Column(name = "UPDATE_DATE")
    private LocalDateTime updateDate;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "writer_seq") // 외래키 컬럼 이름 writer_seq
    private User writer;

//    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
//    List<Comment> comments = new ArrayList<>();
}

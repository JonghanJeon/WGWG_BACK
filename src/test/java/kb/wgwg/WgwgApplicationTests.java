package kb.wgwg;

import kb.wgwg.domain.Article;
import kb.wgwg.domain.User;
import kb.wgwg.repository.ArticleRepository;
import kb.wgwg.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Commit
@Transactional
class WgwgApplicationTests {
	@Autowired
	private UserRepository userRep;

	@Autowired
	private ArticleRepository articleRep;

	@Test
	void contextLoads() {

	}

	@Test
	void userInsert() {
		userRep.save(new User("장희정", "a@naver.com", "jang", "1234"));
		userRep.save(new User("이가현", "b@naver.com", "hyun", "1234"));
		userRep.save(new User("이찬범", "c@naver.com", "chan", "1234"));
	}

	@Test
	void articleInsert() {
		User theUser = userRep.findById(1L).orElseThrow();

		Article theArticle = articleRep.save(new Article("제목1", "내용1", "카테고리1", theUser));
		System.out.println(theArticle);
	}
}
